package org.btpos.dj2addons.bootstrapper.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;

public class DJ2AddonsCore {
	private static boolean coreLoaded = false;
	public static Logger LOGGER = LogManager.getLogger("Divine Journey 2 Addons");
	
	/**
	 * Called by {@link org.btpos.dj2addons.mixin.init.bootstrapper.MLoader#beforeConstructingMods MLoader.beforeConstructingMods}.
	 */
	public static void onLoadCore() {
		coreLoaded = true;
		LOGGER.info("DJ2Addons loaded!");
	}
	
	/**
	 * Called by {@link org.btpos.dj2addons.DJ2Addons#preinit DJ2Addons.preinit}
	 */
	public static void verifyCoreLoaded() {
		if (!coreLoaded) {
			throw new MixinTransformerError("DJ2Addons Mixins are not loaded! The config mixins.dj2addons.init.json was not executed.");
		}
	}
	
	
}
