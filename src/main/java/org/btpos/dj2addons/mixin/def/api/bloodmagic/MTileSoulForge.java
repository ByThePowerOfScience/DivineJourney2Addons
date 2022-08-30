package org.btpos.dj2addons.mixin.def.api.bloodmagic;


import WayofTime.bloodmagic.item.soul.ItemSoulGem;
import WayofTime.bloodmagic.soul.EnumDemonWillType;
import WayofTime.bloodmagic.soul.IDemonWill;
import WayofTime.bloodmagic.tile.TileSoulForge;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.btpos.dj2addons.impl.api.bloodmagic.VSoulForge;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(TileSoulForge.class)
public abstract class MTileSoulForge {
	@ModifyConstant(method = "update", constant = @Constant(intValue = TileSoulForge.ticksRequired))
	private int updateModifyTicksRequired(int value) {
		return VSoulForge.getTicksRequired();
	}
	
	@ModifyConstant(method = "update", constant = @Constant(intValue = TileSoulForge.ticksRequired + 10))
	private int updateModifyTicksRequired2(int value) {
		return VSoulForge.getTicksRequired() + 10;
	}
	
	@ModifyConstant(method = "getProgressForGui", remap = false, constant = @Constant(doubleValue = TileSoulForge.ticksRequired))
	private double guiModifyTicksRequired(double value) {
		return VSoulForge.getTicksRequired();
	}
	
	
	@ModifyConstant(method = "update", constant = @Constant(doubleValue = TileSoulForge.worldWillTransferRate))
	private double updateModifyWorldWillTransferRate(double value) {
		return VSoulForge.getWorldWillTransferRate();
	}
	
	
	
	@Shadow(remap = false)
	@Final
	public static int soulSlot;
	
	@ModifyArg(method = "update", at = @At(value = "INVOKE", target = "LWayofTime/bloodmagic/tile/TileSoulForge;getWill(LWayofTime/bloodmagic/soul/EnumDemonWillType;)D"), index = 0)
	private EnumDemonWillType modifyDemonWillTypeUsed(EnumDemonWillType type) {
		if (VSoulForge.shouldCraftWithAllWillTypes()) {
			
			ItemStack soulStack = ((TileSoulForge)(Object)this).getStackInSlot(soulSlot);
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
		if (VSoulForge.shouldCraftWithAllWillTypes()) {
			ItemStack soulStack = ((TileSoulForge)(Object)this).getStackInSlot(TileSoulForge.soulSlot);
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
