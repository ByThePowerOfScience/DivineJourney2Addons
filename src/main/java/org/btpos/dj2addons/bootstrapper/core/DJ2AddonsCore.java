package org.btpos.dj2addons.bootstrapper.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.btpos.dj2addons.bootstrapper.mixin.MLoader;

public class DJ2AddonsCore {
	private static boolean coreLoaded = false;
	public static Logger LOGGER = LogManager.getLogger("Divine Journey 2 Addons");
	
	/**
	 * Called by {@link MLoader#beforeConstructingMods}.
	 */
	public static void onLoadCore() {
		coreLoaded = true;
		LOGGER.info("DJ2Addons loaded!");
	}
	
	public static void verifyCoreLoaded() {
		if (!coreLoaded) {
			throw new Error("DJ2Addons Mixins are not loaded!");
		}
	}
}
