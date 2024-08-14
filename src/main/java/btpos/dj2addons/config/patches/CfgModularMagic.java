package btpos.dj2addons.config.patches;

import net.minecraftforge.common.config.Config.Comment;

public class CfgModularMagic {
	@Comment("Prevents a crash when a player removes the blood orb from the Life Essence Provider before an operation finishes.")
	public boolean fixBloodOrbCrash = true;
}
