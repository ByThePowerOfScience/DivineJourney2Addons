package btpos.dj2addons.config.optimizations;

import net.minecraftforge.common.config.Config.Comment;

public class CfgIndustrialForegoing {
	@Comment({
			"Make Plant Interactors take the items from AgriCraft Crop Sticks directly instead of dropping them as items on the ground.",
			"Replaces the original Plant Interactor implementation, so disable this if another mod interaction isn't working as intended and notify me."
	})
	public boolean plantInteractor_takeFromCropSticksDirectly = true;
}
