package org.btpos.dj2addons.crafttweaker.datafixers;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.data.IData;
import crafttweaker.api.data.IllegalDataException;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.data.NBTConverter;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.FixTypes;
import net.minecraftforge.common.util.ModFixs;
import org.btpos.dj2addons.remapper.BlockFlattening;
import org.btpos.dj2addons.remapper.BlockFlattening.BlockStateGetter;
import org.btpos.dj2addons.remapper.BlockFlattening.FlatteningDefinition;
import org.btpos.dj2addons.remapper.BlockFlattening.TileEntityAction;
import org.btpos.dj2addons.remapper.BlockFlattening.TileEntityProcessor;
import org.btpos.dj2addons.util.zendoc.ZenDocArg;
import org.btpos.dj2addons.util.zendoc.ZenDocClass;
import org.btpos.dj2addons.util.zendoc.ZenDocMethod;
import org.btpos.dj2addons.util.zendoc.ZenDocNullable;
import org.jetbrains.annotations.Nullable;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

@ZenRegister
@ZenClass("dj2addons.datafixers.BlockMigrationFixer") @ZenDocClass(description = {"Edits save files to change one block to another or edit NBT data.", "Use when removing mods to ensure the old blocks/items aren't outright deleted from players' worlds."})
public class CTMigrationFixer implements ICTDataFixer {
	private BlockFlattening internal;
	private final int version;
	
	private final List<FlatteningDefinition> definitions;
	public CTMigrationFixer(int version) {
		definitions = new ArrayList<>();
		this.version = version;
	}
	
	@ZenMethod @ZenDocMethod(description="Adds a block migration to the world transformer.", args={
			@ZenDocArg(value="oldBlock", info="The block to change in the world."),
			@ZenDocArg(value="newBlock", info="The block to change the old block into."),
			@ZenDocArg(value="blockStateTransformer", info="Function to generate the new block state. (newBlock, tileEntityNBT) -> newBlockState"),
			@ZenDocArg(value="tileEntityNBTTransformer", info="Function to transform the tile entity's NBT data. Have the function return null to remove the tile entity. (oldNBT) -> newNBT OR null")
	})
	public CTMigrationFixer addMigration(IItemStack oldBlock, IBlock newBlock, BiFunction<IBlock, IData, crafttweaker.api.block.IBlockState> blockStateTransformer, @ZenDocNullable Function<IData, IData> tileEntityNBTTransformer) {
		IBlock old = oldBlock.asBlock();
		if (old == null)
			throw new IllegalDataException("[dj2addons.datafixers.MigrationFixer#addMigration] oldBlock is not a block!");
		if (newBlock == null)
			throw new IllegalDataException("[dj2addons.datafixers.MigrationFixer#addMigration] newBlock is null!");
		
		Block oldBB = CraftTweakerMC.getBlock(old);
		Block newBB = CraftTweakerMC.getBlock(newBlock);
		
		definitions.add(new FlatteningDefinition(
			oldBB.getRegistryName(),
			old.getMeta(),
			newBB,
			new CTBlockStateGetter(blockStateTransformer),
			new CTTileEntityProcessor(tileEntityNBTTransformer)
		));
		
		return this;
	}
	
	@ZenMethod
	public ICTDataFixer build() {
		internal = new BlockFlattening(definitions, version);
		return this;
	}
	
	public void registerSelf(ModFixs modFixs) {
		modFixs.registerFix(FixTypes.CHUNK, internal);
	}
	
	@Override
	public BlockFlattening getInternal() {
		return internal;
	}
	
	static class CTBlockStateGetter implements BlockStateGetter {
		BiFunction<IBlock, IData, crafttweaker.api.block.IBlockState> internal;
		CTBlockStateGetter(BiFunction<IBlock, IData, crafttweaker.api.block.IBlockState> internal) {
			this.internal = internal;
		}
		@Override
		public IBlockState getBlockState(Block block, @Nullable NBTTagCompound tileEntityNBT) {
			if (internal == null)
				return block.getDefaultState();
			IBlock ctblock = CraftTweakerMC.getBlock(block, block.getMetaFromState(block.getDefaultState()));
			return CraftTweakerMC.getBlockState(internal.apply(ctblock, CraftTweakerMC.getIData(tileEntityNBT)));
		}
	}
	
	
	static class CTTileEntityProcessor implements TileEntityProcessor {
		private final Function<IData, IData> internal;
		
		CTTileEntityProcessor(Function<IData, IData> internal) {
			this.internal = internal;
		}
		
		@Override
		public TileEntityAction processTileEntity(NBTTagCompound tileEntityNBT) {
			IData result = internal.apply(CraftTweakerMC.getIDataModifyable(tileEntityNBT));
			if (!(result instanceof Map))
				return TileEntityAction.REMOVE; //TODO
			NBTConverter.updateMap(tileEntityNBT, result);
			return TileEntityAction.KEEP;
		}
	}
}
