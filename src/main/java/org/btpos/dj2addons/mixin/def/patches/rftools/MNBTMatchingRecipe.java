package org.btpos.dj2addons.mixin.def.patches.rftools;

import mcjty.rftools.crafting.NBTMatchingRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import org.btpos.dj2addons.mixin.init.accessors.minecraftforge.IngredientNBTAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Forces RFTools to use NBT-matching ingredients.
 * <p>Fixes <a href="https://github.com/Divine-Journey-2/Divine-Journey-2/issues/717">Divine-Journey-2#717</a></p>
 */
@Mixin(NBTMatchingRecipe.class)
public abstract class MNBTMatchingRecipe {
	@Redirect(
			remap = false,
			method = "getIngredients",
			at = @At(
					target = "Lnet/minecraft/item/crafting/Ingredient;fromStacks([Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/crafting/Ingredient;",
					value = "INVOKE"
			)
	)
	private static Ingredient toNBTIngredient(ItemStack[] itemstack) {
		return IngredientNBTAccessor.createIngredientNBT(itemstack[0]);
	}
}

