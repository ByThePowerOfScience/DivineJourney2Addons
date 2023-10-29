package btpos.dj2addons.patches.mixin.rftools;

import mcjty.rftools.blocks.crafter.CrafterBaseTE;
import mcjty.rftools.craftinggrid.CraftingRecipe;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(CrafterBaseTE.class)
public abstract class MCrafterBaseTE {
	
	@Unique
	private CraftingRecipe dj2addons$cachedRecipe;
	@Inject(
			remap=false,
			method="craftOneItemNew",
			at=@At(
				target="Lnet/minecraft/item/crafting/IRecipe;getRemainingItems(Lnet/minecraft/inventory/InventoryCrafting;)Lnet/minecraft/util/NonNullList;",
				value="INVOKE_ASSIGN",
				shift= Shift.BEFORE
			)
	)
	private void captureRecipe(final CraftingRecipe craftingRecipe, CallbackInfoReturnable<Boolean> cir) {
		dj2addons$cachedRecipe = craftingRecipe;
	}
	
	/**
	 * Makes it so any item that a) is not consumed when crafting and b) doesn't have a container item
	 * will be placed in the output slot with the stack size used in the crafting recipe instead of
	 * the stack size that existed in the input inventory.
	 * <p>Fixes <a href="https://github.com/Divine-Journey-2/Divine-Journey-2/issues/1042">Divine-Journey-2#1042</a>.</p>
	 */
	@ModifyVariable(
			remap = false,
			method = "craftOneItemNew",
			name="remaining",
            at=@At("STORE")
	)
	private List<ItemStack> dj2addons$reduceStackSizesToOne(List<ItemStack> remaining) {
		if (remaining.size() != dj2addons$cachedRecipe.getInventory().getSizeInventory())
			return remaining; // failsoft
		
		for (int i = 0; i < remaining.size(); i++) {
			remaining.get(i).setCount(dj2addons$cachedRecipe.getInventory().getStackInSlot(i).getCount()); // TODO test
		}
		return remaining;
	}
}

