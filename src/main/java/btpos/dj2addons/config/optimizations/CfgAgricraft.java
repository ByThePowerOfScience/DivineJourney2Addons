package btpos.dj2addons.config.optimizations;

import net.minecraftforge.common.config.Config.Comment;

public class CfgAgricraft {
	@Comment("Stop crops from checking if they can overtake their neighbors if they aren't Aggressive-type and therefore will never do so.")
	public boolean noOvertakeIfNotAggressive = true;
}
