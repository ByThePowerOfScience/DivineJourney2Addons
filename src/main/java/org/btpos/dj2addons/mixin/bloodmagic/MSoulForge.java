package org.btpos.dj2addons.mixin.bloodmagic;


import WayofTime.bloodmagic.tile.TileInventory;
import WayofTime.bloodmagic.tile.TileSoulForge;
import net.minecraft.item.ItemStack;
import org.btpos.dj2addons.impl.bloodmagic.HellfireForgeValues;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.SoftOverride;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TileSoulForge.class)
public abstract class MSoulForge extends MTileInventory {
	/*
		TODO:
			-Allow modification of Hellfire Forge crafting speed through CraftTweaker
				DONE, need to test
			-Stop items being input into output slot
				DONE
			-Allow crafting with other types of demon will
	 */
	
	@ModifyConstant(method="update()V", constant=@Constant(intValue=TileSoulForge.ticksRequired))
	private int updateModifyTicksRequired(int value) {
		return HellfireForgeValues.getTicksRequired();
	}
	
	@ModifyConstant(method="getProgressForGui()D", remap=false, constant=@Constant(intValue=TileSoulForge.ticksRequired))
	private int guiModifyTicksRequired(int value) {
		return HellfireForgeValues.getTicksRequired();
	}
	
	
	
	@ModifyConstant(method="update()V", constant=@Constant(doubleValue=TileSoulForge.worldWillTransferRate))
	private double updateModifyWorldWillTransferRate(double value) {
		return HellfireForgeValues.getWorldWillTransferRate();
	}
	
	
	
	
	
	
	
	
	// Prevents inserters from inputting into the output slot.
	@Override
	public void isItemValidHandler(int index, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(index != TileSoulForge.outputSlot);
	}
}
