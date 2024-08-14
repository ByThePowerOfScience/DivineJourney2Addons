package btpos.dj2addons.config.patches;

import net.minecraftforge.common.config.Config.Comment;

public class CfgMoreTweaker {
	@Comment({"Forces MoreTweaker to use CraftTweaker's way of registering recipes instead of their custom way.", "When enabled, MoreTweaker recipes (e.g. Witches' Oven) properly respect NBT."})
	public boolean useCraftTweakerRecipeApi = true;
}
