package btpos.dj2addons.api.mixin.extrautils2.generators;

import btpos.dj2addons.api.impl.extrautils2.VExtraUtilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@SuppressWarnings({"UnresolvedMixinReference"})
@Mixin(targets="com/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType$9")
abstract class MDragonEgg {
	@ModifyConstant(
			remap=false,
			method={
					"basePowerGen",
					"getPowerLevel"
			},
			constant=@Constant(floatValue=500.0f)
	)
	public float dj2addons$modifyBasePowerGen(float f) {
		return VExtraUtilities.basePowerMap.getOrDefault("DRAGON_EGG", f);
	}
}