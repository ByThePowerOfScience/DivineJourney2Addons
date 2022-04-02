package org.btpos.dj2addons.mixin.bloodmagic;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WayofTime.bloodmagic.tile.TileInventory.class)
public abstract class MTileInventory {
	@Shadow
	public abstract ItemStack getStackInSlot(int index);
	
	@Inject(method = "isItemValidForSlot", at = @At("RETURN"))
	public void isItemValidHandler(int index, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
	}
	
	
}