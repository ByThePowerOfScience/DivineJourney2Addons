package btpos.dj2addons;

import btpos.dj2addons.config.CfgOptimizations;
import btpos.dj2addons.config.CfgPatches;
import btpos.dj2addons.config.CfgTweaks;
import btpos.dj2addons.core.DJ2Addons;
import com.cleanroommc.configanytime.ConfigAnytime;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;

/**
 * Instantiated through ConfigAnytime
 */
@Config(modid=DJ2Addons.MOD_ID, name="dj2addons/dj2addons")
public final class DJ2AConfig {
	@Comment("Disable all tweaks.")
	public static boolean disable_tweaks = false;
	
	@Comment("Gameplay-altering changes that aren't bug fixes.")
	public static final CfgTweaks tweaks = new CfgTweaks();
	
	@Comment("Disable all optimizations.")
	public static boolean disable_optimizations = false;
	
	public static final CfgOptimizations optimizations = new CfgOptimizations();
	
	@Comment("Disable all patches.")
	public static boolean disable_patches = false;
	
	public static final CfgPatches patches = new CfgPatches();
	
	static {
		ConfigAnytime.register(DJ2AConfig.class);
	}
}
