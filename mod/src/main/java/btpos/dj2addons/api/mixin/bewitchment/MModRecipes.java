package btpos.dj2addons.api.mixin.bewitchment;


import btpos.dj2addons.api.bewitchment.Rituals.Internal;
import com.bewitchment.api.registry.Ritual;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(com.bewitchment.registry.ModRecipes.class)
public class MModRecipes {
	@Shadow(remap=false)
	public static List<Ritual> ritualRecipes;
	
	@Inject(remap=false,method="addRitualRecipe()V", at=@At("TAIL"))
	private static void removeRitualRecipes(CallbackInfo ci) {
		List<Ritual> ritualsToRemove = Internal.getRitualsToRemove();
		ritualRecipes.removeAll(ritualsToRemove);
		ritualRecipes.addAll(ritualsToRemove);
	}
}
