package org.btpos.dj2addons.mixin.def.bewitchment;


import com.bewitchment.api.registry.Ritual;
import com.bewitchment.registry.ModRecipes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import org.btpos.dj2addons.impl.bewitchment.VModRecipes;

import java.util.List;

@Mixin(ModRecipes.class)
public class MModRecipes {
	@Shadow
	public static List<Ritual> ritualRecipes;
	
	@Inject(remap=false,method="addRitualRecipe()V", at=@At("TAIL"))
	private static void removeRitualRecipes(CallbackInfo ci) {
		ritualRecipes.removeAll(VModRecipes.getRitualsToRemove());
		ritualRecipes.addAll(VModRecipes.getRitualsToRemove());
	}
}
