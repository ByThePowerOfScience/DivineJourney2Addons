package btpos.dj2addons.core;

import btpos.dj2addons.DJ2AEventListeners;
import btpos.dj2addons.Tags;
import btpos.dj2addons.api.client.SatuRegen;
import btpos.dj2addons.commands.DJ2AServerCommands;
import btpos.dj2addons.common.modrefs.CCraftTweaker;
import btpos.dj2addons.common.modrefs.IsModLoaded;
import btpos.dj2addons.custom.proxy.CommonProxy;
import btpos.dj2addons.patches.impl.aether_legacy.AetherEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The only reason this is here is because the mod class has to be in the same package as the coremod for some reason.
 */
@Mod(
		modid = DJ2Addons.MOD_ID,
		name = DJ2Addons.MOD_NAME,
		version = Tags.VERSION,
		dependencies = DJ2Addons.DEPENDENCIES
)
public class DJ2Addons  {
	public static final String MOD_ID = "dj2addons";
	public static final String MOD_NAME = "Divine Journey 2 Addons";
	
	public static final String DEPENDENCIES =
			"required-after:crafttweaker;" +
			"after:aether_legacy;" +
			"before:totemic;" +
			"before:bloodmagic;" +
			"before:bewitchment;" +
			"after:extremereactors;" +
			"after:botania;" +
			"before:actuallyadditions";
	
	public static final Logger LOGGER = LogManager.getLogger("Divine Journey 2 Addons");
	
	/**
	 * This is the instance of your mod as created by Forge. It will never be null.
	 */
	@Instance(MOD_ID)
	public static DJ2Addons INSTANCE;
	
	@SidedProxy(
			clientSide="btpos.dj2addons.custom.proxy.ClientProxy",
			serverSide="btpos.dj2addons.custom.proxy.CommonProxy"
	)
	public static CommonProxy proxy;
	
	public DJ2Addons() {
		MinecraftForge.EVENT_BUS.register(DJ2AEventListeners.class);
	}
	
	/**
	 * This is the first initialization event. Register tile entities here.
	 * The registry events below will have fired prior to entry to this method.
	 */
	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		CoreInfo.verifyCoreLoaded();
		LOGGER.info("DJ2Addons Loaded!");
		proxy.registerCommands();
		// event.getAsmData() // this is a thing you should use sometime
		AetherEventHandler.tryEnable();
	}
	
	/**
	 * This is the second initialization event. Register custom recipes.
	 */
	@EventHandler
	public void init(FMLInitializationEvent event) {
		//noinspection DataFlowIssue
		SatuRegen.addHungerShankWaveActivator(player -> player.isPotionActive(ModPotions.SATUREGEN));
	}
	
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
	
	
	
}
