
package org.btpos.dj2addons.impl.api.botania;

import com.google.common.base.Preconditions;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.api.brew.IBrewContainer;
import vazkii.botania.api.recipe.RecipeBrew;
import vazkii.botania.common.brew.BrewMod;

import java.util.Set;

public class BrewHandler {
	public static boolean shouldEnableWarpWardPendant = false;
	
	public BrewHandler() {
	}
	
	public static Brew buildBrew(String key, String name, int color, int cost, PotionEffect... effects) {
		return new Brew(key, name, color, cost, effects);
	}
	
	public static Brew buildBrew(String key, int cost,  PotionEffect... potionEffects) {
		return new BrewMod(key, cost, potionEffects);
	}
	
	public static void registerBrew(Brew b) {
		BotaniaAPI.registerBrew(b);
	}
	
	
	public static void registerBrewRecipe(Brew brew, ItemStack[] objects) {
		BotaniaAPI.registerBrewRecipe(brew, (Object[])objects);
	}
	
	public static void registerOutputRestrictedBrewRecipe(Brew brew, Set<IBrewContainer> allowedOutputs, ItemStack[] objects) {
		Preconditions.checkArgument(objects.length <= 6);
		RecipeBrew recipe = new RestrictedOutputRecipeBrew(brew, allowedOutputs, (Object[])objects);
		BotaniaAPI.brewRecipes.add(recipe);
	}
}
