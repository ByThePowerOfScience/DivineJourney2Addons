package btpos.dj2addons;

import btpos.dj2addons.config.CfgOptimizations;
import btpos.dj2addons.config.CfgPatches;
import btpos.dj2addons.config.CfgTweaks;
import net.minecraftforge.common.config.Config;

/**
 * Instantiated through ConfigAnytime
 */
@Config(modid=DJ2Addons.MOD_ID)
public final class DJ2AConfig {
	public static boolean disable_tweaks = false;
	
	public static final CfgTweaks tweaks = new CfgTweaks();
	
	
	public static boolean disable_optimizations = false;
	
	public static final CfgOptimizations optimizations = new CfgOptimizations();
	
	public static boolean disable_patches = false;
	
	public static final CfgPatches patches = new CfgPatches();
}
