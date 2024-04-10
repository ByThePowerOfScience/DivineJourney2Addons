package btpos.dj2addons.api.mixin.extrautils2.generators;

import btpos.dj2addons.api.extrautils2.ExtraUtilities.Internal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@SuppressWarnings({"UnresolvedMixinReference"})
@Mixin(targets="com/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType$5")
abstract class MWind {
	@ModifyConstant(
			remap=false,
			method={
					"getBasePower"
			},
			constant=@Constant(floatValue=1.0f)
	)
	public float dj2addons$modifyBasePowerGen(float f) {
		return Internal.getBasePower("WIND", f);
	}
}
