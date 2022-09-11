package org.btpos.dj2addons;

import crafttweaker.mc1120.commands.CTChatCommand;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;
import org.btpos.dj2addons.bootstrapper.core.DJ2AddonsCore;
import org.btpos.dj2addons.crafttweaker.CommandHandler;
import org.btpos.dj2addons.registry.ModPotions;

import static org.btpos.dj2addons.bootstrapper.core.DJ2AddonsCore.LOGGER;

@Mod(modid = DJ2Addons.MOD_ID, name = DJ2Addons.MOD_NAME, version = DJ2Addons.VERSION, dependencies = DJ2Addons.DEPENDENCIES)
public class DJ2Addons {
	public static final String MOD_ID = "dj2addons";
	public static final String MOD_NAME = "Divine Journey 2 Addons";
	public static final String VERSION = "@VERSION@";
	
	public static final String DEPENDENCIES =
			"required-after:crafttweaker;" +
			"before:totemic;" +
			"before:bloodmagic;" +
			"before:bewitchment;" +
			"after:extremereactors;" +
			"after:botania";
	
	/**
	 * This is the instance of your mod as created by Forge. It will never be null.
	 */
	@Mod.Instance(MOD_ID)
	public static DJ2Addons INSTANCE;
	
	/**
	 * This is the first initialization event. Register tile entities here.
	 * The registry events below will have fired prior to entry to this method.
	 */
	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		DJ2AddonsCore.verifyCoreLoaded();
		CTChatCommand.registerCommand(new CommandHandler());
		if (DJ2AddonsCore.shouldWriteAerogelTooltip)
			DJ2AddonsCore.writeAerogelTooltip();
	}
	
	/**
	 * This is the second initialization event. Register custom recipes.
	 */
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		LOGGER.log(Level.INFO, "Voted \"Most Likely to be Factorio\"!");
	}
	
	/**
	 * This is the final initialization event. Register actions from other mods here
	 */
	@Mod.EventHandler
	public void postinit(FMLPostInitializationEvent event) {}
	
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
			ModPotions.init(evt);
		}
	}
}
