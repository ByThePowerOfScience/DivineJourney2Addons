package btpos.dj2addons.api.mixin.extrautils2.generators;

import btpos.dj2addons.api.extrautils2.ExtraUtilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@SuppressWarnings({"UnresolvedMixinReference"})
@Mixin(targets="com/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType$2")
abstract class MLunar {
	@ModifyConstant(
			remap=false,
			method={
					"getPowerLevel"
			},
			constant=@Constant(floatValue=1.0f)
	)
	public float dj2addons$modifyBasePowerGen(float f) {
		return ExtraUtilities.Internal.basePowerMap.getOrDefault("LUNAR", f);
	}
}
