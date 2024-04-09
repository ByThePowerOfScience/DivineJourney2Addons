package btpos.dj2addons.api.mixin.extrautils2.generators;

import btpos.dj2addons.api.extrautils2.ExtraUtilities.Internal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@SuppressWarnings({"UnresolvedMixinReference"})
@Mixin(targets="com/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType$6")
abstract class MFire {
	@ModifyConstant(
			remap=false,
			method={
					"getPowerLevel",
					"basePowerGen"
			},
			constant=@Constant(floatValue=4.0f)
	)
	public float dj2addons$modifyBasePowerGen(float f) {
		return Internal.basePowerMap.getOrDefault("FIRE", f);
	}
}
