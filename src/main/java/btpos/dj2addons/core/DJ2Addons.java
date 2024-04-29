package btpos.dj2addons.core;

import btpos.dj2addons.Tags;
import btpos.dj2addons.api.client.SatuRegen;
import btpos.dj2addons.commands.DJ2AServerCommands;
import btpos.dj2addons.common.CoreInfo;
import btpos.dj2addons.common.modrefs.CCraftTweaker;
import btpos.dj2addons.common.modrefs.IsModLoaded;
import btpos.dj2addons.custom.proxy.CommonProxy;
import btpos.dj2addons.custom.registry.ModPotions;
import btpos.dj2addons.custom.registry.ModPotions.Registered;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

@Mod(
		modid = DJ2Addons.MOD_ID,
		name = DJ2Addons.MOD_NAME,
		version = Tags.VERSION,
		dependencies = DJ2Addons.DEPENDENCIES//,
//		certificateFingerprint = Tags.CERT_FINGERPRINT
)
public class DJ2Addons  {
	public static final String MOD_ID = "dj2addons";
	public static final String MOD_NAME = "Divine Journey 2 Addons";
	
	public static final String DEPENDENCIES =
			"after:crafttweaker;" +
			"after:aether_legacy;" +
			"before:totemic;" +
			"before:bloodmagic;" +
			"before:bewitchment;" +
			"after:extremereactors;" +
			"after:botania;" +
			"before:actuallyadditions";
	
	public static final Logger LOGGER = CoreInfo.LOGGER;
	
	/**
	 * This is the instance of your mod as created by Forge. It will never be null.
	 */
	@Mod.Instance(MOD_ID)
	public static DJ2Addons INSTANCE;
	
	@SidedProxy(
			clientSide="btpos.dj2addons.custom.proxy.ClientProxy",
			serverSide="btpos.dj2addons.custom.proxy.CommonProxy"
	)
	public static CommonProxy proxy;
	
	/**
	 * This is the first initialization event. Register tile entities here.
	 * The registry events below will have fired prior to entry to this method.
	 */
	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		LOGGER.info("DJ2Addons Loaded!");
		//noinspection DataFlowIssue
		SatuRegen.addHungerShankWaveActivator(player -> player.isPotionActive(Registered.saturegen));
		CoreInfo.verifyCoreLoaded();
		proxy.registerCommands();
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
		IsModLoaded.update();
		if (IsModLoaded.crafttweaker) {
			CCraftTweaker.postInit();
		}
	}
	
//	@GameRegistry.ObjectHolder(MOD_ID)
//	public static class Blocks {
//      public static final Block resourcename = null;
//	}
	
	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		event.registerServerCommand(new DJ2AServerCommands());
	}
	
	
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
