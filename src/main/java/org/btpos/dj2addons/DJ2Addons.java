package org.btpos.dj2addons;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
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
import org.apache.logging.log4j.Logger;
import org.btpos.dj2addons.core.DJ2AddonsCore;
import org.btpos.dj2addons.impl.modrefs.CCraftTweaker;
import org.btpos.dj2addons.impl.modrefs.IsModLoaded;
import org.btpos.dj2addons.mixin.DJ2AMixinConfig;
import org.btpos.dj2addons.proxy.CommonProxy;
import org.btpos.dj2addons.registry.ModPotions;

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
	public void init(FMLInitializationEvent event) {}
	
	/**
	 * This is the final initialization event. Register actions from other mods here
	 */
	@Mod.EventHandler
	public void postinit(FMLPostInitializationEvent event) {
		if (IsModLoaded.crafttweaker) {
			CCraftTweaker.postInit();
		}
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
		
		
		private static final ImmutableMap<ResourceLocation, ResourceLocation> remapper = ImmutableMap.of(
				new ResourceLocation("modularmachinery", "blockcasing"), new ResourceLocation("multiblocked","")
		);
		
		@SubscribeEvent
		public static void catchBlocks(RegistryEvent.MissingMappings<Block> evt) {
			//TODO datafixer
			evt.getAllMappings().forEach(blockMapping -> {
				if (remapper.containsKey(blockMapping.key)) {
					Block newBlock = blockMapping.registry.getValue(remapper.get(blockMapping.key));
					blockMapping.remap(newBlock);
				}
			});
		}
		
		public static void main(String[] args) {
			CompoundDataFixer dataFixer = FMLCommonHandler.instance().getDataFixer();
			ModFixs modFixs = dataFixer.init("modularmachinery", 7);
			
			//TODO
		}
		
		//TODO Figure out models and how to register them
//		@SubscribeEvent
//		public static void addBlocks(RegistryEvent.Register<Block> evt) {
//			ModBlocks.registerBlocks(evt.getRegistry());
//		}
//
//		@SubscribeEvent
//		public static void addItems(RegistryEvent.Register<Item> evt) {
//			ModBlocks.registerBlockItems(evt.getRegistry());
//			//ModItems.registerItems(evt.getRegistry());
//		}
	}
	
	
}
