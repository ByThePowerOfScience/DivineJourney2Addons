package btpos.dj2addons.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Intermediary between the ASM phase and the Mod phase of loading.
 */
public class CoreInfo {
	public static final Logger LOGGER = LogManager.getLogger("Divine Journey 2 Addons Core");
	
	private static boolean coreLoaded = false;
	
	/**
	 * Called by {@link btpos.dj2addons.DJ2AMixinConfig#onLoad(String)}.
	 */
	public static void onLoadCore() {
		if (!coreLoaded) {
			coreLoaded = true;
			LOGGER.info("DJ2Addons loaded!");
		}
	}
	
	/**
	 * Called by {@link btpos.dj2addons.DJ2Addons#preinit DJ2Addons.preinit}
	 */
	public static void verifyCoreLoaded() {
		if (!coreLoaded) {
			try {
				Class.forName("org.spongepowered.asm.mixin.Mixin");
				Class.forName("zone.rong.mixinbooter.IEarlyMixinLoader"); // hate that I have to depend on this guy...
			} catch (ClassNotFoundException e) {
				throw new Error("DJ2Addons requires MixinBooter to run. Use MixinBooter ");
			}
		}
	}
	
}
