
package btpos.dj2addons.api.botania;

import com.google.common.base.Preconditions;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.api.recipe.RecipeBrew;

import java.util.Set;

public final class Brews {
	
	public static void registerOutputRestrictedBrewRecipe(Brew brew, Set<ItemStack> allowedOutputs, ItemStack[] objects) {
		Preconditions.checkArgument(objects.length <= 6);
		RecipeBrew recipe = new RestrictedOutputRecipeBrew(brew, allowedOutputs, objects);
		BotaniaAPI.brewRecipes.add(recipe);
	}
	
	public static void enableWarpWardPendant() {
		Internal.shouldEnableWarpWardPendant = true;
	}
	
	public static final class Internal {
		public static boolean shouldEnableWarpWardPendant = false;
	}
}
