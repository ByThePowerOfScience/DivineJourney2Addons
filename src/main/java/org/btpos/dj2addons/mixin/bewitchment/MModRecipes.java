package org.btpos.dj2addons.mixin.bewitchment;


import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.ritual.RitualBiomeShift;
import com.bewitchment.registry.ModRecipes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static org.spongepowered.asm.mixin.injection.At.Shift.BY;

@Mixin(ModRecipes.class)
public class MModRecipes {
	@Shadow
	public static List<Ritual> ritualRecipes;
	
	
	@Inject(remap=false,method="addRitualRecipe()V", at=@At(target="com/bewitchment/common/ritual/RitualBiomeShift", value="NEW", shift=BY, by=5))
	private static void removeBiomeShift(CallbackInfo ci) {
		ritualRecipes.remove(new RitualBiomeShift());
	}
}
