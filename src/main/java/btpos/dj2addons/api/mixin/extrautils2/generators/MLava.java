package btpos.dj2addons.api.mixin.extrautils2.generators;

import btpos.dj2addons.api.extrautils2.ExtraUtilities.Internal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@SuppressWarnings({"UnresolvedMixinReference"})
@Mixin(targets="com/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType$3")
abstract class MLava {
	@ModifyConstant(
			remap=false,
			method={
					"basePowerGen"
			},
			constant=@Constant(floatValue=4.0f)
	)
	public float dj2addons$modifyBasePowerGen(float f) {
		return Internal.getBasePower("LAVA", f);
	}
	
	
	@ModifyConstant(
			remap=false,
			method={
					"getPowerLevel"
			},
			constant=@Constant(floatValue=2.0f, ordinal = 0)
	)
	public float dj2addons$modifyPowerLevel(float f) {
		float f1 = Internal.getBasePower("LAVA", f);
		if (f1 == f)
			return f;
		else
			return f1 / 2f;
	}
	
	@ModifyConstant(
			remap=false,
			method={
					"getPowerLevel"
			},
			constant=@Constant(floatValue=2.0f, ordinal = 1)
	)
	public float dj2addons$modifyPowerLevel2(float f) {
		float f1 = Internal.getBasePower("LAVA", f);
		if (f1 == f)
			return f;
		else
			return f / 2f / f1;
	}
}
