package btpos.dj2addons.patches.mixin.bloodmagic;

import WayofTime.bloodmagic.tile.TileSoulForge;
import net.minecraft.item.ItemStack;
import btpos.dj2addons.api.mixin.bloodmagic.MTileInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TileSoulForge.class)
public abstract class MSoulForge extends MTileInventory {
	/**
	 * Prevents inserters from inputting into the output slot.
	 */
	@Override
	public void isItemValidHandler(int index, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(index != TileSoulForge.outputSlot);
	}
}
