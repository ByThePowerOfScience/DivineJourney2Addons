package btpos.dj2addonscore.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.throwables.MixinError;

/**
 * Intermediary between the ASM phase and the Mod phase of loading.
 */
public class CoreInfo {
	public static final Logger LOGGER = LogManager.getLogger("Divine Journey 2 Addons");
	
	private static boolean coreLoaded = false;
	private static final String CORENOTLOADEDSTRING = "Something stopped DJ2Addons' Mixins from loading! The config 'mixins.dj2addons.bootstrap.json' was not executed. Report this to https://github.com/ByThePowerOfScience/DivineJourney2Addons/issues and include the log!";
	
	public static boolean shouldWriteAerogelTooltip = false;
	
	public static boolean doesTickProfilerExist = false;
	
	/**
	 * Called by {@link btpos.dj2addonscore.bootstrap.MLoader#beforeConstructingMods MLoader.beforeConstructingMods}.
	 */
	public static void onLoadCore() {
		coreLoaded = true;
		LOGGER.info("DJ2Addons loaded!");
	}
	
	public static void failedToLoadCore(Throwable e) throws MixinError {
		throw new MixinError(CORENOTLOADEDSTRING, e);
	}
	
	/**
	 * Called by {@link btpos.dj2addons.DJ2Addons#preinit DJ2Addons.preinit}
	 */
	public static void verifyCoreLoaded() {
		if (!coreLoaded) {
			try {
				Class.forName("org.spongepowered.asm.mixin.Mixin");
				throw new MixinError(CORENOTLOADEDSTRING);
			} catch (ClassNotFoundException e) {
				throw new Error("DJ2Addons requires Mixin to run. Install MixinBootstrap (NOT MixinBooter) and try launching again.");
			}
		}
	}
	
	/**
	 * Called by {@link btpos.dj2addonscore.DJ2AMixinConfig#shouldApplyMixin DJ2AMixinConfig.shouldApplyMixin} if TickCentral is detected.
	 */
	public static void onDisableAerogelPatch() {
		shouldWriteAerogelTooltip = true;
	}
}
