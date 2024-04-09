
package btpos.dj2addons.api.botania;

import com.google.common.base.Preconditions;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.api.recipe.RecipeBrew;
import vazkii.botania.common.item.ModItems;

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
	 * @param ingredients The items in the recipe.
	 * @see btpos.dj2addons.api.botania.RestrictedOutputRecipeBrew RestrictedOutputRecipeBrew
	 * @see #MANAGLASS_VIAL
	 * @see #ALFGLASS_FLASK
	 * @see #INCENSE_STICK
	 * @see #TAINTED_BLOOD_PENDANT
	 */
	public static void registerOutputRestrictedBrewRecipe(Brew brew, Set<ItemStack> allowedOutputs, ItemStack... ingredients) {
		Preconditions.checkArgument(ingredients.length <= 6);
		RecipeBrew recipe = new RestrictedOutputRecipeBrew(brew, allowedOutputs, ingredients);
		BotaniaAPI.brewRecipes.add(recipe);
	}
	
	/**
	 * Enables the Tainted Blood Pendant of Warp Ward.  Only applicable if Thaumcraft is installed.
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
