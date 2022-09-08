package org.btpos.dj2addons.mixin.def.patches.bewitchment;

import com.bewitchment.Util;
import com.bewitchment.api.registry.OvenRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(OvenRecipe.class)
public abstract class MOvenRecipe {
	
	@Redirect(
			remap = false,
			method = "isValid",
			at = @At(
					target = "Lcom/bewitchment/Util;canMerge(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z",
					value = "INVOKE"
			)
	)
	private boolean fixSwappedParams(ItemStack stack0, ItemStack stack1) {
		return Util.canMerge(stack1, stack0);
	}
	
	@Redirect(
			remap = false,
			method="giveOutput",
			at=@At(
					value="FIELD",
					target="com/bewitchment/api/registry/OvenRecipe.requiresJar:Z"
			)
	) //TODO test that this fixes the bug https://github.com/Divine-Journey-2/Divine-Journey-2/issues/679
	private boolean checkByproductSlotBeforeConsumeJar(OvenRecipe instance, Random rand, ItemStackHandler input, ItemStackHandler output) {
		ItemStack byproductSlot = output.getStackInSlot(1);
		return instance.requiresJar
		       && (byproductSlot.isEmpty() || Util.canMerge(instance.byproduct, byproductSlot))
		       && !(byproductSlot.getCount() == byproductSlot.getMaxStackSize()) ;
	}
	
	
}

