package org.btpos.dj2addons.mixin.moretweaker;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.CraftTweaker;
import moretweaker.CraftingPart;
import moretweaker.Inputs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.IngredientNBT;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Inputs.class)
public abstract class MInputs {
	
	@Inject(method= "getPart", at=@At(value="HEAD"), remap=false)
	private static void getPart(IIngredient stack, CallbackInfoReturnable<CraftingPart> cir) {
		cir.setReturnValue(new CraftingPart(CraftTweakerMC.getIngredient(stack), stack.getAmount()));
	}
	
	@Inject(method="getIngredient", at=@At(value="HEAD"), remap=false)
	private static void getIngredient(Object o, CallbackInfoReturnable<Ingredient> cir) {
		cir.setReturnValue(CraftTweakerMC.getIngredient(CraftTweakerMC.getIIngredient(o)));
	}
}
