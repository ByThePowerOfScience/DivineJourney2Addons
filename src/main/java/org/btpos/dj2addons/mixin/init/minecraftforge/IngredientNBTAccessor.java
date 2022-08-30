package org.btpos.dj2addons.mixin.init.minecraftforge;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.crafting.IngredientNBT;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(IngredientNBT.class)
public interface IngredientNBTAccessor {
	/**
	 * Made public in latest Forge version, but can't use because ForgeGradle 2.3.
 	 */
	@Invoker("<init>")
	static IngredientNBT createIngredientNBT(ItemStack stack) {throw new UnsupportedOperationException("Invoker not merged properly, or invoker called in interface instead of target class.");}
}
