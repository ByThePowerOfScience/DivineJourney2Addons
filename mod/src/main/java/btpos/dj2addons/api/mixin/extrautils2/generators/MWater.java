package btpos.dj2addons.api.mixin.extrautils2.generators;

import btpos.dj2addons.api.impl.extrautils2.VExtraUtilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@SuppressWarnings({"UnresolvedMixinReference"})
@Mixin(targets="com/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType$4")
abstract class MWater {
	@ModifyConstant(
			remap=false,
			method={
					"basePowerGen"
			},
			constant=@Constant(floatValue=4.0f)
	)
	public float dj2addons$modifyBasePowerGen(float f) {
		return VExtraUtilities.basePowerMap.getOrDefault("WATER", f / 2f) * 2;
	}
	
	@ModifyConstant(
			remap=false,
			method={
					"getPowerLevel"
			},
			constant=@Constant(floatValue=2.0f)
	)
	public float dj2addons$modifyPowerLevel2(float f) {
		float f1 = VExtraUtilities.basePowerMap.getOrDefault("WATER", f);
		if (f1 == f) {
			return f;
		} else {
			return f / f1;
		}
	}
}
