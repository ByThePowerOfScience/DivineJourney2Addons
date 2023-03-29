package org.btpos.dj2addons;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.FixTypes;
import net.minecraftforge.common.util.CompoundDataFixer;
import net.minecraftforge.common.util.ModFixs;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.Logger;
import org.btpos.dj2addons.core.DJ2AddonsCore;
import org.btpos.dj2addons.impl.modrefs.CCraftTweaker;
import org.btpos.dj2addons.mixin.DJ2AMixinConfig;
import org.btpos.dj2addons.proxy.CommonProxy;
import org.btpos.dj2addons.registry.ModPotions;
import org.btpos.dj2addons.remapper.BlockFlattening;
import org.btpos.dj2addons.remapper.BlockFlattening.FlatteningDefinition;
import org.btpos.dj2addons.remapper.BlockFlattening.TileEntityAction;
import org.btpos.dj2addons.remapper.BlockFlattening.TileEntityProcessor;

import java.util.List;

@Mod(modid = DJ2Addons.MOD_ID, name = DJ2Addons.MOD_NAME, version = DJ2Addons.VERSION, dependencies = DJ2Addons.DEPENDENCIES)
public class DJ2Addons {
	public static final String MOD_ID = "dj2addons";
	public static final String MOD_NAME = "Divine Journey 2 Addons";
	public static final String VERSION = "@VERSION@";
	
	public static final String DEPENDENCIES =
			"after:crafttweaker;" +
			"after:aether_legacy;" +
			"before:totemic;" +
			"before:bloodmagic;" +
			"before:bewitchment;" +
			"after:extremereactors;" +
			"after:botania";
	
	public static final Logger LOGGER = DJ2AMixinConfig.LOGGER;
	
	/**
	 * This is the instance of your mod as created by Forge. It will never be null.
	 */
	@Mod.Instance(MOD_ID)
	public static DJ2Addons INSTANCE;
	
	@SidedProxy(clientSide="org.btpos.dj2addons.proxy.ClientProxy", serverSide="org.btpos.dj2addons.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	/**
	 * This is the first initialization event. Register tile entities here.
	 * The registry events below will have fired prior to entry to this method.
	 */
	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		DJ2AddonsCore.verifyCoreLoaded();
		if (Loader.isModLoaded("crafttweaker"))
			CCraftTweaker.loadCommandHandler();
	}
	
	
	
	/**
	 * This is the second initialization event. Register custom recipes.
	 */
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		doDataFixers();
	}
	
	/**
	 * This is the final initialization event. Register actions from other mods here.
	 */
	@Mod.EventHandler
	public void postinit(FMLPostInitializationEvent event) {
		if (Loader.isModLoaded("crafttweaker")) {
			CCraftTweaker.postInit();
		}
	}
	
	public static void doDataFixers() {
		LOGGER.debug("Adding data fixers.");
		CompoundDataFixer dataFixer = FMLCommonHandler.instance().getDataFixer();
		ModFixs modFixs = dataFixer.init("dj2addons", 10007);
		modFixs.registerFix(FixTypes.CHUNK, getRemapperFixer());
	}
	
	public static BlockFlattening getRemapperFixer() {
		IForgeRegistry<Block> registry = ForgeRegistries.BLOCKS;
		TileEntityProcessor toFurnace = (nbt) -> {
			nbt.setInteger("CookTime", 0);
			nbt.setInteger("BurnTime", 0);
			nbt.setString("id", "minecraft:furnace");
			nbt.setInteger("CookTimeTotal", 200);
			NBTTagCompound items = nbt.getCompoundTag("Items");
			for (int i = 2; i < items.getSize(); i++) {
				items.removeTag(String.valueOf(i));
			}
			return TileEntityAction.KEEP;
		};
		List<FlatteningDefinition> definitions = ImmutableList.of(
				new FlatteningDefinition(
						new ResourceLocation("minecraft", "dispenser"), //debug
						0,
						Blocks.FURNACE,
						(block, tileEntityNBT) -> block.getDefaultState(),
						toFurnace
				),
				new FlatteningDefinition(
						new ResourceLocation("minecraft", "dispenser"), //debug
						1,
						Blocks.FURNACE,
						(block, tileEntityNBT) -> block.getDefaultState(),
						toFurnace
				),
				new FlatteningDefinition(
						new ResourceLocation("minecraft", "dispenser"), //debug
						2,
						Blocks.FURNACE,
						(block, tileEntityNBT) -> block.getDefaultState().withProperty(BlockFurnace.FACING, EnumFacing.NORTH),
						toFurnace
				),
				new FlatteningDefinition(
						new ResourceLocation("minecraft", "dispenser"), //debug
						3,
						Blocks.FURNACE,
						(block, tileEntityNBT) -> block.getDefaultState().withProperty(BlockFurnace.FACING, EnumFacing.SOUTH),
						toFurnace
				),
				new FlatteningDefinition(
						new ResourceLocation("minecraft", "dispenser"), //debug
						4,
						Blocks.FURNACE,
						(block, tileEntityNBT) -> block.getDefaultState().withProperty(BlockFurnace.FACING, EnumFacing.WEST),
						toFurnace
				),
				new FlatteningDefinition(
						new ResourceLocation("minecraft", "dispenser"), //debug
						5,
						Blocks.FURNACE,
						(block, tileEntityNBT) -> block.getDefaultState().withProperty(BlockFurnace.FACING, EnumFacing.EAST),
						toFurnace
				));
		
		return new BlockFlattening(definitions);
	}
	
	
//	@GameRegistry.ObjectHolder(MOD_ID)
//	public static class Blocks {
//      public static final Block resourcename = null;
//	}
	
	
	/**
	 * This is a special class that listens to registry events, to allow creation of mod blocks and items at the proper time.
	 */
	@Mod.EventBusSubscriber(modid=DJ2Addons.MOD_ID)
	public static class ObjectRegistryHandler {
		@SubscribeEvent
		public static void addPotions(RegistryEvent.Register<Potion> evt) {
			ModPotions.registerPotions(evt.getRegistry());
		}
		
		@SubscribeEvent
		public static void addPotionTypes(RegistryEvent.Register<PotionType> evt) {
			ModPotions.registerPotionTypes(evt.getRegistry());
		}
		
		
		private static final ImmutableMap<ResourceLocation, ImmutableMap<Integer, Pair<ResourceLocation, Integer>>> remapper = ImmutableMap.of(
				new ResourceLocation("modularmachinery", "blockcasing"), ImmutableMap.of(
						0, Pair.of(new ResourceLocation("multiblocked",""), 1)) //TODO
		                                                                                                      );
		
//		@SubscribeEvent
//		public static void catchBlocks(RegistryEvent.MissingMappings<Block> evt) {
//			//TODO might need datafixer ONLY
//			evt.getAllMappings().forEach(blockMapping -> {
//				if (remapper.containsKey(blockMapping.key)) {
//					ImmutableMap<Integer, Pair<ResourceLocation, Integer>> metaMap = remapper.get(blockMapping.key);
//					if (metaMap.containsKey(blockMapping.id)) {
//						Pair<ResourceLocation, Integer> newBlockMeta = metaMap.get(blockMapping.id);
//						Block newBlock = blockMapping.registry.getValue(newBlockMeta.getLeft());
//
//						blockMapping.remap(newBlock);
//					}

//
//				}
//			});
//		}
		
	
	}
	
	
}
