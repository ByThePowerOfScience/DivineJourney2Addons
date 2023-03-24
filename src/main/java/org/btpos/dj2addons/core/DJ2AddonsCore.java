package org.btpos.dj2addons.core;

import org.apache.logging.log4j.Logger;
import org.btpos.dj2addons.mixin.DJ2AMixinConfig;

/**
 * Intermediary between the ASM phase and the Mod phase of loading.
 */
public class DJ2AddonsCore {
	private static boolean coreLoaded = false;
	
	public static boolean shouldWriteAerogelTooltip = false;
	
	/**
	 * Called by {@link org.btpos.dj2addons.mixin.init.bootstrapper.MLoader#beforeConstructingMods MLoader.beforeConstructingMods}.
	 */
	public static void onLoadCore() {
		coreLoaded = true;
		DJ2AMixinConfig.LOGGER.info("DJ2Addons loaded!");
	}
	
	/**
	 * Called by {@link org.btpos.dj2addons.DJ2Addons#preinit DJ2Addons.preinit}
	 */
	public static void verifyCoreLoaded() {
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
