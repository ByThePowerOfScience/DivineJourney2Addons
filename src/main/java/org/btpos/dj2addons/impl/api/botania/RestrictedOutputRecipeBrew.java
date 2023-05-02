package org.btpos.dj2addons.impl.api.botania;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.api.brew.IBrewContainer;
import vazkii.botania.api.recipe.RecipeBrew;

import java.util.Objects;
import java.util.Set;

/**
 * A version of the Botania brew recipe that allows different recipes for different types of bottles (e.g. vial, flask, etc.), as opposed to the same recipe for all different bottles of that brew.
 */
public class RestrictedOutputRecipeBrew extends RecipeBrew {
	public final Set<ItemStack> allowedOutputs;
	private final int hashcode;
	
	public RestrictedOutputRecipeBrew(Brew brew, Set<ItemStack> allowedOutputs, Object[] inputs) {
		super(brew, inputs);
		this.allowedOutputs = allowedOutputs;
		this.hashcode = Objects.hash(super.hashCode(), allowedOutputs.hashCode());
	}
	
	@Override
	public boolean matches(IItemHandler inv) {
		// return super.matches(inv) && inv.getStackInSlot(0).getItem() instanceof IBrewContainer && allowedOutputs.contains((IBrewContainer)item);
		if (super.matches(inv)) {
			for (int i = 0; i < inv.getSlots(); i++) {
				final ItemStack itemStack = inv.getStackInSlot(i);
				if (itemStack.getItem() instanceof IBrewContainer) {
					return allowedOutputs.stream().anyMatch(allowed -> ItemStack.areItemStacksEqual(allowed, itemStack));
				}
			}
		}
		return false;
	}
	
	@Override
	public ItemStack getOutput(ItemStack stack) {
		if (stack.getItem() instanceof IBrewContainer && allowedOutputs.stream().anyMatch(allowed -> ItemStack.areItemStacksEqual(allowed, stack)))
			return super.getOutput(stack);
		else
			return ItemStack.EMPTY;
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
