package btpos.dj2addons;

import btpos.dj2addons.config.Optimizations;
import btpos.dj2addons.config.Tweaks;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Name;

/**
 * Instantiated through ConfigAnytime
 */
@Config(modid=DJ2Addons.MOD_ID)
public final class DJ2AConfig {
	@Name("Disable all tweaks")
	public static boolean disableTweaks = false;
	
	public static final Tweaks tweaks = new Tweaks();
	
	
	@Name("Disable all optimizations")
	public static boolean disableOptimizations = false;
	
	public static final Optimizations optimizations = new Optimizations();
}
