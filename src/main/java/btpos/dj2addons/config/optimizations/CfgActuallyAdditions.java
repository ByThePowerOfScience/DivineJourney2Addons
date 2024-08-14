package btpos.dj2addons.config.optimizations;

import net.minecraftforge.common.config.Config.Comment;

public class CfgActuallyAdditions {
	@Comment({
					"Replaces the Laser Relay network with an optimized graph-based version for significant CPU gains.",
					"Currently in beta, disabled by default."
	})
	public boolean useOptimizedLaserNetwork = false;
}
