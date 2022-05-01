package org.btpos.dj2addons.mixin.roots;

import epicsquid.roots.block.itemblock.ItemBlockUnendingBowl;
import epicsquid.roots.config.GeneralConfig;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(epicsquid.roots.block.itemblock.ItemBlockUnendingBowl.UnendingBowlItemHandler.class)
public abstract class MUnendingBowlItemHandler {
	
	@Shadow
	public abstract boolean canDrainFluidType(FluidStack fluid);
	
	@Inject(method="drain(Lnet/minecraftforge/fluids/FluidStack;Z)Lnet/minecraftforge/fluids/FluidStack;", at=@At("HEAD"), remap=false, cancellable = true)
	private void drain(FluidStack resource, boolean doDrain, CallbackInfoReturnable<FluidStack> cir) {
		if (canDrainFluidType(resource)) {
			cir.setReturnValue(resource.copy());
		}
		else
			cir.setReturnValue(null);
	}
}
