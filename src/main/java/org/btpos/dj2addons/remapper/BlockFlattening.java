package org.btpos.dj2addons.remapper;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.IFixableData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.GameData;
import org.apache.commons.lang3.tuple.Pair;
import org.btpos.dj2addons.DJ2Addons;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;

import static org.btpos.dj2addons.DJ2Addons.LOGGER;

/**
 * A data fixer that remaps (flattened) blocks to their new states.
 *
 * @author Choonster https://github.com/Choonster-Minecraft-Mods/TestMod3/blob/ba02bfc6a440792cd16136510e8438a46962c37f/src/main/java/choonster/testmod3/datafix/BlockFlattening.java
 */
public class BlockFlattening implements IFixableData {
	private final List<FlatteningDefinition> flatteningDefinitions;
	private final int version;
	
	public BlockFlattening(final List<FlatteningDefinition> flatteningDefinitions) {
		this(flatteningDefinitions, 10005);
	}
	
	public BlockFlattening(final List<FlatteningDefinition> flatteningDefinitions, int version) {
		this.flatteningDefinitions = flatteningDefinitions;
		this.version = version;
	}
	
	@Override
	public int getFixVersion() {
		return version;
	}
	
	private static boolean alreadyLogged = false;
	@Override
	public @NotNull NBTTagCompound fixTagCompound(final @NotNull NBTTagCompound compound) {
		
		final ForgeRegistry<Block> blockRegistry = (ForgeRegistry<Block>) ForgeRegistries.BLOCKS;
		
		// Maps old block IDs to an array of flattening definitions indexed by their old metadata
		final Map<Integer, FlatteningDefinition[]> flattingDefinitionsPerBlockID = new HashMap<>();
		
		flatteningDefinitions.stream()
		                     .map(flatteningDefinition -> {
			                     // Get the ID of the old name
			                     int oldID = blockRegistry.getID(flatteningDefinition.oldName);
			
			                     // If the ID exists in this save, return a pair of the ID and the definition; else return an empty pair
			                     return Optional.ofNullable(oldID > 0 ? Pair.of(oldID, flatteningDefinition) : null);
		                     })
		                     .forEach(optionalPair -> optionalPair.ifPresent(pair -> { // If the ID exists in this save,
			                     final Integer blockID = pair.getKey();
			                     final FlatteningDefinition flatteningDefinition = pair.getValue();
			
			                     // Add the definition to the ID's array using the old metadata as an index
			                     final FlatteningDefinition[] flatteningDefinitions = flattingDefinitionsPerBlockID.computeIfAbsent(
					                     blockID,
					                     id -> new FlatteningDefinition[16]);
			                     
								 flatteningDefinitions[flatteningDefinition.oldMetadata] = flatteningDefinition;
		                     }));
		if (!alreadyLogged) {
			DJ2Addons.LOGGER.info("Running fix with {} block IDs, with {} flattening definitions on the first ID", flattingDefinitionsPerBlockID.size(),
			                      Arrays.stream(flattingDefinitionsPerBlockID.get(flattingDefinitionsPerBlockID.keySet()
			                                                                                                   .stream()
			                                                                                                   .findFirst()
			                                                                                                   .get())).filter(Objects::nonNull).count());
			alreadyLogged = true;
		}
		// If there aren't any blocks to flatten in this save, do nothing
		if (flattingDefinitionsPerBlockID.isEmpty()) {
			return compound;
		}
		
		final ObjectIntIdentityMap<IBlockState> blockStateIDMap = GameData.getBlockStateIDMap();
		
		try {
			final NBTTagCompound level = compound.getCompoundTag("Level");
			final int chunkX = level.getInteger("xPos");
			final int chunkZ = level.getInteger("zPos");
			final NBTTagList tileEntities = level.getTagList("TileEntities", 10);
			final NBTTagList sections = level.getTagList("Sections", 10);
			
			final ChunkPos chunkPos = new ChunkPos(chunkX, chunkZ);
			
			final IntList tileEntityIndexesToRemove = new IntArrayList();
			
			// Maps TileEntity positions to pairs of TileEntity compound tags and their index in the `TileEntities` list tag
			final Map<BlockPos, Pair<Integer, NBTTagCompound>> tileEntityMap = new HashMap<>();
			
			for (int tileEntityIndex = 0; tileEntityIndex < tileEntities.tagCount(); tileEntityIndex++) {
				final NBTTagCompound tileEntityNBT = tileEntities.getCompoundTagAt(tileEntityIndex);
				if (!tileEntityNBT.isEmpty()) {
					final BlockPos pos = new BlockPos(tileEntityNBT.getInteger("x"),
					                                  tileEntityNBT.getInteger("y"),
					                                  tileEntityNBT.getInteger("z"));
					tileEntityMap.put(pos, Pair.of(tileEntityIndex, tileEntityNBT));
				}
			}
			
			for (int sectionIndex = 0; sectionIndex < sections.tagCount(); ++sectionIndex) {
				final NBTTagCompound section = sections.getCompoundTagAt(sectionIndex);
				
				final int sectionY = section.getByte("Y");
				final byte[] blockIDs = section.getByteArray("Blocks");
				final NibbleArray metadataArray = new NibbleArray(section.getByteArray("Data"));
				final NibbleArray blockIDsExtension = section.hasKey("Add", Constants.NBT.TAG_BYTE_ARRAY)
				                                      ? new NibbleArray(section.getByteArray("Add"))
				                                      : new NibbleArray();
				boolean hasExtendedBlockIDs = section.hasKey("Add", Constants.NBT.TAG_BYTE_ARRAY);
				
				for (int blockIndex = 0; blockIndex < blockIDs.length; ++blockIndex) {
					final int x = blockIndex & 15;
					final int y = blockIndex >> 8 & 15;
					final int z = blockIndex >> 4 & 15;
					final int blockIDExtension = blockIDsExtension.get(x, y, z);
					final int blockID = blockIDExtension << 8 | (blockIDs[blockIndex] & 255);
					final int metadata = metadataArray.get(x, y, z);
					
					final FlatteningDefinition[] flatteningDefinitions = flattingDefinitionsPerBlockID.get(blockID);
					if (flatteningDefinitions != null) {
						final FlatteningDefinition flatteningDefinition = flatteningDefinitions[metadata];
						
						if (flatteningDefinition != null) {
							// Calculate the world coordinates of the block
							final BlockPos blockPos = chunkPos.getBlock(x, sectionY << 4 | y, z).toImmutable();
							
							// Get the TileEntity NBT, if any
							final Pair<Integer, NBTTagCompound> tileEntityPair = tileEntityMap.get(blockPos);
							final NBTTagCompound tileEntityNBT = tileEntityPair != null
							                                     ? tileEntityPair.getValue()
							                                     : null;
							// Get the new block state from the flattening definition
							final IBlockState newBlockState = flatteningDefinition.blockStateGetter.getBlockState(
									flatteningDefinition.newBlock,
									tileEntityNBT);
							
							
							DJ2Addons.LOGGER.info("Old block state: {}:{}\nNew block state: {}:{}",
							                      flatteningDefinition.oldName,
							                      flatteningDefinition.oldMetadata,
							                      newBlockState.getBlock().getRegistryName(),
							                      newBlockState.getBlock().getMetaFromState(newBlockState));
							
							// Calculate the new block ID, block ID extension and metadata from the block state's ID
							final int blockStateID = blockStateIDMap.get(newBlockState);
							final byte newBlockID = (byte) (blockStateID >> 4 & 255);
							final byte newBlockIDExtension = (byte) (blockStateID >> 12 & 15);
							final byte newMetadata = (byte) (blockStateID & 15);
							
							// Update the block ID and metadata
							blockIDs[blockIndex] = newBlockID;
							metadataArray.set(x, y, z, newMetadata);
							
							// Update the block ID extension if present
							if (newBlockIDExtension != 0) {
								hasExtendedBlockIDs = true;
								blockIDsExtension.set(x, y, z, newBlockIDExtension);
							}
							
							// If there's a TileEntity and the flattening definition has a TileEntity processor,
							if (tileEntityNBT != null && flatteningDefinition.tileEntityProcessor != null) {
								// Run the processor
								final TileEntityAction tileEntityAction = flatteningDefinition.tileEntityProcessor.processTileEntity(tileEntityNBT);
								
								// If the processor requested the removal of the TileEntity, add the index to the removal list
								if (tileEntityAction == TileEntityAction.REMOVE) {
									tileEntityIndexesToRemove.add(tileEntityPair.getKey());
								}
							}
						}
					}
				}
				
				// Update the block ID and metadata in the section
				section.setByteArray("Blocks", blockIDs);
				section.setByteArray("Data", metadataArray.getData());
				
				// Update the block ID extensions in the section, if present
				if (hasExtendedBlockIDs) {
					section.setByteArray("Add", blockIDsExtension.getData());
				}
			}
			
			// Remove the requested TileEntities, highest indexes first
			tileEntityIndexesToRemove.sort(Comparator.reverseOrder());
			for (final int tileEntityIndex : tileEntityIndexesToRemove) {
				tileEntities.removeTag(tileEntityIndex);
			}
		} catch (final Exception e) {
			LOGGER.catching(e);
		}
		
		return compound;
	}
	
	
	public static class FlatteningDefinition {
		final ResourceLocation oldName;
		final int oldMetadata;
		
		final Block newBlock;
		
		final BlockStateGetter blockStateGetter;
		
		@Nullable final TileEntityProcessor tileEntityProcessor;
		
		public FlatteningDefinition(final ResourceLocation oldName, final int oldMetadata, final Block newBlock, final BlockStateGetter blockStateGetter,
		                            @Nullable
		                            final TileEntityProcessor tileEntityProcessor)
		{
			this.oldName = oldName;
			this.oldMetadata = oldMetadata;
			this.newBlock = newBlock;
			this.blockStateGetter = blockStateGetter;
			this.tileEntityProcessor = tileEntityProcessor;
		}
		
	}
	
	@FunctionalInterface
	public interface BlockStateGetter {
		IBlockState getBlockState(Block block, @Nullable NBTTagCompound tileEntityNBT);
	}
	
	@FunctionalInterface
	public interface TileEntityProcessor {
		TileEntityAction processTileEntity(NBTTagCompound tileEntityNBT);
	}
	
	public enum TileEntityAction {
		KEEP,
		REMOVE,
	}
}
