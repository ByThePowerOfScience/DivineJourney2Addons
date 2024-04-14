
package btpos.dj2addons.api.botania;

import com.google.common.base.Preconditions;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.api.recipe.RecipeBrew;
import vazkii.botania.common.item.ModItems;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * API for Botania Brews, including allowing different brew recipes for different containers.
 * @see btpos.dj2addons.crafttweaker.botania.CTBrews CraftTweaker API
 */
public final class Brews {
	/**
	 * Convenient itemstack for the Managlass Vial.
	 */
	public static final ItemStack MANAGLASS_VIAL = new ItemStack(ModItems.vial, 1, 0);
	/**
	 * Convenient itemstack for the Alfglass Flask.
	 */
	public static final ItemStack ALFGLASS_FLASK = new ItemStack(ModItems.vial, 1, 1);
	/**
	 * Convenient itemstack for the Incense Stick.
	 */
	public static final ItemStack INCENSE_STICK = new ItemStack(ModItems.incenseStick, 1, 0);
	
	/**
	 * Convenient itemstack for the Tainted Blood Pendant.
	 */
	public static final ItemStack TAINTED_BLOOD_PENDANT = new ItemStack(ModItems.bloodPendant, 1, 0);
	
	/**
	 * Creates AND registers a brew recipe that is only valid for a certain set of container items.
	 * <p>For example, to make a "&lt;Managlass Vial/Alfglass Flask&gt; of MyBrew" have a different recipe than "Tainted Blood Pendant of MyBrew":
	 * <pre>{@code
	 * import net.minecraft.init.Items;
	 * import btpos.dj2addons.api.botania.Brews;
	 * import vazkii.botania.common.brew.BrewMod;
	 *
	 * // Make your brew
	 * Brew myBrew = new BrewMod("myBrew", ...);
	 *
	 * // Register a recipe specifically for the Vial and Flask:
	 * Brews.registerOutputRestrictedBrewRecipe(
	 *      myBrew,
	 *      Set.of(Brews.MANAGLASS_VIAL, Brews.ALFGLASS_FLASK),
	 *      new ItemStack[] {
	 *          new ItemStack(Items.COOKED_BEEF),
	 *          new ItemStack(Items.NETHER_WART)
	 *      }
	 * );
	 *
	 * // Register a recipe specifically for Tainted Blood Pendants:
	 * Brews.registerOutputRestrictedBrewRecipe(
	 *      myBrew,
	 *      Set.of(Brews.TAINTED_BLOOD_PENDANT),
	 *      new ItemStack[] {
	 *          Items.NETHER_STAR,
	 *          Items.GOLDEN_APPLE
	 *      }
	 * );
	 * }</pre>
	 * @param brew The brew this recipe should create.
	 * @param allowedOutputs The only containers this recipe should be valid for.
	 * @param ingredients The items in the recipe. Can be a String or an ItemStack.
	 * @see btpos.dj2addons.api.botania.RestrictedOutputRecipeBrew RestrictedOutputRecipeBrew
	 * @see #MANAGLASS_VIAL
	 * @see #ALFGLASS_FLASK
	 * @see #INCENSE_STICK
	 * @see #TAINTED_BLOOD_PENDANT
	 * @see btpos.dj2addons.crafttweaker.botania.CTBrews#addOutputRestrictedBrewRecipe(btpos.dj2addons.crafttweaker.botania.CTBrews.ZenBrewWrapper, crafttweaker.api.item.IItemStack[], crafttweaker.api.item.IItemStack[]) CraftTweaker API
	 */
	public static void registerOutputRestrictedBrewRecipe(Brew brew, Set<ItemStack> allowedOutputs, Object... ingredients) {
		Preconditions.checkArgument(ingredients.length <= 6);
		RecipeBrew recipe = new RestrictedOutputRecipeBrew(brew, allowedOutputs, ingredients);
		BotaniaAPI.brewRecipes.add(recipe);
	}
	//		NonNullList<ItemStack> ores = OreDictionary.getOres(((String) el));
	//		if (ores.isEmpty())
	//			throw CraftTweakerHelpers.fillExc(new IllegalArgumentException("Invalid OreDict key " + el + " provided. Key was either empty or nonexistent."));
	//		return ores.get(0);
	
	/**
	 *
	 * @param key The key for the brew, e.g. "botania.brews.warpWard". Use "/ct dj2addons brews" in-game to get the keys for all registered brews.
	 * @param ingredientsToMatch An array of ItemStacks or OreDict Strings that are the ingredients to match.
	 */
	public static void removeRecipe(String key, Object[] ingredientsToMatch) {
		for (int i = 0; i < ingredientsToMatch.length; i++) {
			Object o = ingredientsToMatch[i];
			if (!(o instanceof ItemStack || o instanceof String)) {
				throw new IllegalArgumentException("Ingredients must be either ItemStacks or OreDict Strings.");
			}
		}
		
		List<RecipeBrew> brewRecipes = BotaniaAPI.brewRecipes;
		
		IntList indicesToRemove = new IntArrayList();
		boolean[] hasParamFoundMatchAlready = new boolean[ingredientsToMatch.length];
		
		loopAllRecipes:
		for (int idx_allRecipes = 0, brewRecipesSize = brewRecipes.size(); idx_allRecipes < brewRecipesSize; idx_allRecipes++) {
			RecipeBrew recipe = brewRecipes.get(idx_allRecipes);
			if (!key.equals(recipe.getBrew().getKey()))
				continue;
			
			Arrays.fill(hasParamFoundMatchAlready, false);
			
			for (Object ingredientFromRecipe : recipe.getInputs()) {
				boolean ingredientExistsInParamSet = false;
				
				for (int i = 0; i < ingredientsToMatch.length; i++) {
					if (hasParamFoundMatchAlready[i])
						continue;
					
					Object fromParam = ingredientsToMatch[i];
					if (ingredientFromRecipe.equals(fromParam)) {
						hasParamFoundMatchAlready[i] = true;
						ingredientExistsInParamSet = true;
						break;
					}
					if (fromParam instanceof ItemStack && ingredientFromRecipe instanceof ItemStack) {
						if (OreDictionary.itemMatches(((ItemStack) fromParam), ((ItemStack) ingredientFromRecipe), false)) {
							hasParamFoundMatchAlready[i] = true;
							ingredientExistsInParamSet = true;
							break;
						}
					}
					else if (ingredientFromRecipe instanceof String && fromParam instanceof ItemStack) {
						if (OreDictionary.containsMatch(false, OreDictionary.getOres(((String) ingredientFromRecipe), false), ((ItemStack) fromParam))) {
							hasParamFoundMatchAlready[i] = true;
							ingredientExistsInParamSet = true;
							break;
						}
					}
				}
				if (!ingredientExistsInParamSet)
					break;
			}
			for (int i = 0; i < hasParamFoundMatchAlready.length; i++) {
				if (!hasParamFoundMatchAlready[i])
					continue loopAllRecipes;
			}
			indicesToRemove.add(idx_allRecipes);
		}
		
		for (int i = indicesToRemove.size() - 1; i >= 0; i--) {
			brewRecipes.remove(indicesToRemove.getInt(i));
		}
	}
	
	/**
	 * Enables the Tainted Blood Pendant of Warp Ward.  Only applicable if Thaumcraft is installed.
	 * @see btpos.dj2addons.crafttweaker.botania.CTBrews#enableWarpWardPendant() CraftTweaker API
	 */
	public static void enableWarpWardPendant() {
		Internal.shouldEnableWarpWardPendant = true;
	}
	
	/**
	 * Internal implementation of this API.
	 */
	public static final class Internal {
		/**
		 * @see btpos.dj2addons.api.mixin.botania.MModBrews Implementation
		 */
		public static boolean shouldEnableWarpWardPendant = false;
	}
}
