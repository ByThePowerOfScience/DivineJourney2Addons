package org.btpos.dj2addons.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Intermediary between the ASM phase and the Mod phase of loading.
 */
public class DJ2AddonsCore {
	public static final Logger LOGGER = LogManager.getLogger("Divine Journey 2 Addons");
	
	private static boolean coreLoaded = false;
	
	public static boolean shouldWriteAerogelTooltip = false;
	
	/**
	 * Called by {@link org.btpos.dj2addons.mixin.init.bootstrapper.MLoader#beforeConstructingMods MLoader.beforeConstructingMods}.
	 */
	public static void onLoadCore() {
		coreLoaded = true;
		LOGGER.info("DJ2Addons Core loaded!");
	}
	
	/**
	 * Called by {@link org.btpos.dj2addons.DJ2Addons#preinit DJ2Addons.preinit}
	 */
	public static void verifyCoreLoaded() {
		LOGGER.info("Verified DJ2Addons Core Loaded!");
		if (!coreLoaded) {
			try {
				Class.forName("org.spongepowered.asm.mixin.Mixin");
				throw new Error("DJ2Addons Mixins are not loaded! The config mixins.dj2addons.init.json was not executed.");
			} catch (ClassNotFoundException e) {
				throw new Error("DJ2Addons requires Mixin to run.");
			}
		}
	}
	
	/**
	 * Called by {@link org.btpos.dj2addons.mixin.DJ2AMixinConfig#shouldApplyMixin DJ2AMixinConfig.shouldApplyMixin} if TickCentral is detected.
	 */
	public static void onDisableAerogelPatch() {
		shouldWriteAerogelTooltip = true;
	}
}
