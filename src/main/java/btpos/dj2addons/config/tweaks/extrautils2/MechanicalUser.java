package btpos.dj2addons.config.tweaks.extrautils2;

import net.minecraftforge.common.config.Config.Comment;

public class MechanicalUser {
	@Comment("If the block's inventory can be read by a comparator'.")
	public boolean enableComparatorOutput = true;
	
	@Comment({
			"If the block should be able to activate Thaumcraft Runic Matrixes using the research of the player who placed it.",
			"Enables vanilla Runic Matrix automation."
	})
	public boolean usePlacersThaumcraftResearch = true;
}
