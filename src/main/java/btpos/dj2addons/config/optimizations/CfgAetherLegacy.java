package btpos.dj2addons.config.optimizations;

import net.minecraftforge.common.config.Config.Comment;

public class CfgAetherLegacy {
	@Comment({"Stops heavy Garbage Collection times caused by wrapping and unwrapping items immediately.",
	"Specifically, instead of wrapping Items in an ItemStack for a method and then immediately just getting the Item from it, this makes the program use the Item directly without wrapping."})
	public boolean changeAccessoriesHandler = true;
	
	@Comment("Makes Aether's response to someone using a bucket check if the player is in an affected dimension first before doing more-expensive checks.")
	public boolean onBucketUsed_checkDimensionFirst = true;
}
