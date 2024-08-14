package btpos.dj2addons.config.patches;

import net.minecraftforge.common.config.Config.Comment;

public class CfgRFTools {
	@Comment("Force RFTools' Crafters to respect the NBT of ingredients in its recipes.")
	public boolean useNbtIngredients = true;
}
