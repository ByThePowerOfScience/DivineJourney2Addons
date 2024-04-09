package btpos.dj2addons.api.botania;

import btpos.dj2addons.util.fastutilutils.FastUtilCollectors;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.api.brew.IBrewContainer;
import vazkii.botania.api.recipe.RecipeBrew;

import java.util.Objects;
import java.util.Set;

/**
 * A version of the Botania brew recipe that allows different recipes for different types of bottles (e.g. vial, flask, etc.), as opposed to the same recipe for all different bottles of that brew.
 * <p>For example, to make a "&lt;Managlass Vial/Alfglass Flask&gt; of MyBrew" have a different recipe than "Tainted Blood Pendant of MyBrew":
 * <pre>{@code
 * import net.minecraft.init.Items;
 * import btpos.dj2addons.api.botania.Brews;
 * import btpos.dj2addons.api.botania.RestrictedOutputRecipeBrew;
 * import vazkii.botania.common.brew.BrewMod;
 *
 * // Make your brew
 * Brew myBrew = new BrewMod("myBrew", ...);
 *
 * // Create a recipe specifically for the Vial and Flask:
 * RecipeBrew bottleRecipe = new RestrictedOutputRecipeBrew(
 *      myBrew,
 *      Set.of(Brews.MANAGLASS_VIAL, Brews.ALFGLASS_FLASK),
 *      new Object[] {
 *          new ItemStack(Items.COOKED_BEEF),
 *          new ItemStack(Items.NETHER_WART)
 *      }
 * );
 *
 * // Create a recipe specifically for Tainted Blood Pendants:
 * RecipeBrew pendantRecipe = new RestrictedOutputRecipeBrew(
 *      myBrew,
 *      Set.of(Brews.TAINTED_BLOOD_PENDANT),
 *      new Object[] {
 *          new ItemStack(Items.NETHER_STAR),
 *          new ItemStack(Items.GOLDEN_APPLE)
 *      }
 * );
 *
 * BotaniaAPI.brewRecipes.add(bottleRecipe);
 * BotaniaAPI.brewRecipes.add(pendantRecipe);
 * }</pre>
 * @see btpos.dj2addons.api.botania.Brews#registerOutputRestrictedBrewRecipe(vazkii.botania.api.brew.Brew, java.util.Set, Object...) Brews#registerOutputRestrictedBrewRecipe
 */
public class RestrictedOutputRecipeBrew extends RecipeBrew {
	public final Set<ItemStack> allowedOutputs;
	private final int hashcode;
	
	/**
	 *
	 * @param brew The brew this recipe is for.
	 * @param allowedOutputs The set of output containers that will trigger this recipe. Null values will be excluded.
	 * @param inputs An array of ItemStacks (item references with metadata) and/or Strings (OreDictionary tags).
	 * @see btpos.dj2addons.api.botania.RestrictedOutputRecipeBrew Example
	 * @see btpos.dj2addons.api.botania.Brews Brews API
	 */
	public RestrictedOutputRecipeBrew(Brew brew, Set<ItemStack> allowedOutputs, Object... inputs) {
		super(brew, inputs);
		
		this.allowedOutputs = allowedOutputs.stream()
		                                    .filter(Objects::nonNull)
		                                    .collect(FastUtilCollectors.toObjectOpenHashSet());
		
		this.hashcode = Objects.hash(super.hashCode(), allowedOutputs.hashCode());
	}
	
	@Override
	public boolean matches(IItemHandler inv) {
		// return super.matches(inv) && inv.getStackInSlot(0).getItem() instanceof IBrewContainer && allowedOutputs.contains((IBrewContainer)item);
		if (super.matches(inv)) {
			for (int i = 0; i < inv.getSlots(); i++) {
				final ItemStack itemStack = inv.getStackInSlot(i);
				if (itemStack.getItem() instanceof IBrewContainer) {
					return isValidOutput(itemStack);
				}
			}
		}
		return false;
	}
	
	@Override
	public ItemStack getOutput(ItemStack stack) {
		if (stack.getItem() instanceof IBrewContainer && isValidOutput(stack))
			return super.getOutput(stack);
		else
			return ItemStack.EMPTY;
	}
	
	private boolean isValidOutput(ItemStack stack) {
		return allowedOutputs.stream()
		                     .anyMatch(output -> output.getItem() == stack.getItem()
                                                    &&
		                                         output.getMetadata() == stack.getMetadata());
	}
	
	@Override
	public int hashCode() {
		return hashcode;
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof RestrictedOutputRecipeBrew
				&& this.allowedOutputs.equals(((RestrictedOutputRecipeBrew) o).allowedOutputs);
	}
}
