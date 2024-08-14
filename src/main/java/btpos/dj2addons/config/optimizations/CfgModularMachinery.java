package btpos.dj2addons.config.optimizations;

import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;

public class CfgModularMachinery {
	@Name("Input/Output Slots: Use Larger Maps")
	@Comment({"Use ingredient maps properly sized for the number of slots the inventory has, instead of starting with a small map and resizing it later.",
	"Saves a remarkable amount of CPU time."})
	public boolean ioports_useLargerMaps = true;
}
