package org.btpos.dj2addons.mixin.bloodmagic;


import WayofTime.bloodmagic.item.soul.ItemSoulGem;
import WayofTime.bloodmagic.soul.EnumDemonWillType;
import WayofTime.bloodmagic.soul.IDemonWill;
import WayofTime.bloodmagic.soul.IDemonWillGem;
import WayofTime.bloodmagic.tile.TileSoulForge;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.btpos.dj2addons.impl.bloodmagic.SoulForgeValues;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TileSoulForge.class)
public abstract class MSoulForge extends MTileInventory {
	@Shadow(remap = false)
	@Final
	public static int soulSlot;
	
	@ModifyConstant(method = "update", constant = @Constant(intValue = TileSoulForge.ticksRequired))
	private int updateModifyTicksRequired(int value) {
		return SoulForgeValues.getTicksRequired();
	}
	
	@ModifyConstant(method = "getProgressForGui", remap = false, constant = @Constant(doubleValue = TileSoulForge.ticksRequired))
	private double guiModifyTicksRequired(double value) {
		return SoulForgeValues.getTicksRequired();
	}
	
	
	@ModifyConstant(method = "update", constant = @Constant(doubleValue = TileSoulForge.worldWillTransferRate))
	private double updateModifyWorldWillTransferRate(double value) {
		return SoulForgeValues.getWorldWillTransferRate();
	}
	
	
	// Prevents inserters from inputting into the output slot.
	@Override
	public void isItemValidHandler(int index, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(index != TileSoulForge.outputSlot);
	}
	
	
	@ModifyArg(method = "update", at = @At(value = "INVOKE", target = "LWayofTime/bloodmagic/tile/TileSoulForge;getWill(LWayofTime/bloodmagic/soul/EnumDemonWillType;)D"), index = 0)
	private EnumDemonWillType modifyDemonWillTypeUsed(EnumDemonWillType type) {
		if (SoulForgeValues.shouldCraftWithAllWillTypes()) {
			ItemStack soulStack = getStackInSlot(soulSlot);
			Item soul = soulStack.getItem();
			if (soul instanceof ItemSoulGem)
				return ((ItemSoulGem) soul).getCurrentType(soulStack);
			if (soul instanceof IDemonWill)
				return ((IDemonWill)soul).getType(soulStack);
		}
		return type;
	}
	
	@ModifyArg(method = "update", at = @At(value = "INVOKE", target = "LWayofTime/bloodmagic/tile/TileSoulForge;consumeSouls(LWayofTime/bloodmagic/soul/EnumDemonWillType;D)D"))
	private EnumDemonWillType modifyConsumeSoulsType(EnumDemonWillType type) {
		if (SoulForgeValues.shouldCraftWithAllWillTypes()) {
			ItemStack soulStack = getStackInSlot(TileSoulForge.soulSlot);
			Item soul = soulStack.getItem();
			if (soul instanceof IDemonWill)
				return ((IDemonWill) soul).getType(soulStack);
			if (soul instanceof ItemSoulGem) {
				return ((ItemSoulGem) soul).getCurrentType(soulStack);
			}
		}
		return type;
	}
	
	
}
