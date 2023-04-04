package org.btpos.dj2addons.impl.api.botania;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.api.brew.IBrewContainer;
import vazkii.botania.api.recipe.RecipeBrew;

import java.util.Set;

/**
 * A version of the Botania brew recipe that allows different recipes for different types of bottles (e.g. vial, flask, etc.), as opposed to the same recipe for all different bottles of that brew.
 */
public class RestrictedOutputRecipeBrew extends RecipeBrew {
	public final Set<IBrewContainer> allowedOutputs;
	
	public RestrictedOutputRecipeBrew(Brew brew, Set<IBrewContainer> allowedOutputs, Object... inputs) {
		super(brew, inputs);
		this.allowedOutputs = allowedOutputs;
	}
	
	@Override
	public boolean matches(IItemHandler inv) {
		// return super.matches(inv) && inv.getStackInSlot(0).getItem() instanceof IBrewContainer && allowedOutputs.contains((IBrewContainer)item);
		if (super.matches(inv)) {
			for (int i = 0; i < inv.getSlots(); i++) {
				Item item = inv.getStackInSlot(i).getItem();
				if (item instanceof IBrewContainer) {
					return allowedOutputs.contains((IBrewContainer)item);
				}
			}
		}
		return false;
	}
	
	@Override
	public ItemStack getOutput(ItemStack stack) {
		if (stack.getItem() instanceof IBrewContainer && !allowedOutputs.contains((IBrewContainer)stack.getItem()))
			return super.getOutput(stack);
		else
			return ItemStack.EMPTY;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() * allowedOutputs.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof RestrictedOutputRecipeBrew) {
			return this.allowedOutputs.equals(((RestrictedOutputRecipeBrew) o).allowedOutputs);
		} else {
			return false;
		}
	}
}
