package btpos.dj2addons.core;

import btpos.dj2addons.asmducks.InfusionStabilizerDelegateDuck;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Intermediary between the ASM phase and the Mod phase of loading.
 */
public class CoreInfo {
	public static final Logger LOGGER = LogManager.getLogger("Divine Journey 2 Addons Core");
	public static boolean RUNTIME_DEOBF = false;
	public static File MC_LOCATION = null;
	
	public static List<InfusionStabilizerDelegateDuck> objectsToSetLogicFor = new LinkedList<>();
	
	private static boolean coreLoaded = false;
	
	/**
	 * Called by {@link btpos.dj2addons.core.DJ2AMixinConfig#onLoad(String)}.
	 */
	public static void onLoadCore() {
		if (!coreLoaded) {
			coreLoaded = true;
			LOGGER.info("DJ2Addons Core loaded!");
		}
	}
	
	/**
	 * Called by {@link btpos.dj2addons.core.DJ2Addons#preinit DJ2Addons.preinit}
	 */
	public static void verifyCoreLoaded() {
		if (!coreLoaded) {
			try {
				Class.forName("org.spongepowered.asm.mixin.Mixin");
				Class.forName("zone.rong.mixinbooter.IEarlyMixinLoader"); // hate that I have to depend on this guy...
			} catch (ClassNotFoundException e) {
				throw new Error("DJ2Addons requires MixinBooter to run.");
			}
		}
	}
	
}
