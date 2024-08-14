package btpos.dj2addons.config.patches;

import net.minecraftforge.common.config.Config.Comment;

public class CfgAetherLegacy {
	@Comment({
			"Try to stop Aerogel automation from crashing the server.",
			"Disable this if you have TickProfiler installed!"
	})
	public boolean stopAerogelCrash = true;
	
	@Comment("Stop the Enchanter from leaving an empty bucket in the input slot if the output is also in a bucket.")
	public boolean enchanterBucketFix = true;
}
