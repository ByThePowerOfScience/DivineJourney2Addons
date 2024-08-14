package btpos.dj2addons.config.optimizations;

import net.minecraftforge.common.config.Config.Comment;

public class CfgEnderIO {
	@Comment("Stops the error handler from setting up a ton of overhead to loop through entire arrays when it only has a single element.")
	public boolean optimizeNullHelper = true;
	
	@Comment("Makes the profiler's searching a good bit faster by storing the pattern it looks for instead of recreating it each tick.")
	public boolean cacheProfilerRegex = true;
}
