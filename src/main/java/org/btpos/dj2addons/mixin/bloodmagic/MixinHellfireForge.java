package org.btpos.dj2addons.mixin.bloodmagic;


import WayofTime.bloodmagic.tile.TileSoulForge;
import org.btpos.dj2addons.impl.bloodmagic.HellfireForgeValues;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(TileSoulForge.class)
public abstract class MixinHellfireForge {
	/*
		TODO:
			-Allow modification of Hellfire Forge crafting speed through CraftTweaker
				DONE, need to test
			-Stop items being input into output slot
			-Allow crafting with other types of demon will
	 */
	
	@ModifyConstant(method="update()V", constant=@Constant(intValue=100))
	private int updateModifyTicksRequired(int value) {
		return HellfireForgeValues.getTicksRequired();
	}
	
	@ModifyConstant(method="getProgressForGui()D", remap=false, constant=@Constant(intValue=100))
	private int guiModifyTicksRequired(int value) {
		return HellfireForgeValues.getTicksRequired();
	}
}
