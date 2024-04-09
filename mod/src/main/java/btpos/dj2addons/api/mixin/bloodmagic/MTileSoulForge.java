package btpos.dj2addons.api.mixin.bloodmagic;


import WayofTime.bloodmagic.item.soul.ItemSoulGem;
import WayofTime.bloodmagic.soul.EnumDemonWillType;
import WayofTime.bloodmagic.soul.IDemonWill;
import WayofTime.bloodmagic.tile.TileSoulForge;
import btpos.dj2addons.api.bloodmagic.HellfireForge.Internal;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import static WayofTime.bloodmagic.tile.TileSoulForge.soulSlot;

@Mixin(value = TileSoulForge.class, remap = false)
public abstract class MTileSoulForge {
	
	@ModifyArg(
			method = "update",
			at = @At(
					value = "INVOKE",
					target = "LWayofTime/bloodmagic/tile/TileSoulForge;getWill(LWayofTime/bloodmagic/soul/EnumDemonWillType;)D"
			),
			index = 0)
	private EnumDemonWillType modifyDemonWillTypeUsed(EnumDemonWillType type) {
		if (Internal.shouldCraftWithAllWillTypes()) {
			
			ItemStack soulStack = ((TileSoulForge)(Object)this).getStackInSlot(soulSlot);
			Item soul = soulStack.getItem();
			if (soul instanceof ItemSoulGem)
				return ((ItemSoulGem) soul).getCurrentType(soulStack);
			if (soul instanceof IDemonWill)
				return ((IDemonWill)soul).getType(soulStack);
		}
		return type;
	}
	
	@ModifyArg(
			method = "update",
			at = @At(
					value = "INVOKE",
					target = "LWayofTime/bloodmagic/tile/TileSoulForge;consumeSouls(LWayofTime/bloodmagic/soul/EnumDemonWillType;D)D")
	)
	private EnumDemonWillType modifyConsumeSoulsType(EnumDemonWillType type) {
		if (Internal.shouldCraftWithAllWillTypes()) {
			ItemStack soulStack = ((TileSoulForge)(Object)this).getStackInSlot(soulSlot);
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
