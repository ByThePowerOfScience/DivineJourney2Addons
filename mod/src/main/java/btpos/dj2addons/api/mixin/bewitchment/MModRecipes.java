package btpos.dj2addons.api.mixin.bewitchment;


import btpos.dj2addons.api.bewitchment.Rituals.Internal;
import com.bewitchment.api.registry.Ritual;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = com.bewitchment.registry.ModRecipes.class, remap = false)
public class MModRecipes {
	@Shadow
	public static List<Ritual> ritualRecipes;
	
	@Inject(
			method="addRitualRecipe()V",
			at=@At("TAIL")
	)
	private static void dj2addons$removeRitualRecipes(CallbackInfo ci) {
		List<Ritual> ritualsToRemove = Internal.getRitualsToRemove();
		// remove the real recipes and replace them with dummies so Patchouli doesn't crash
		ritualRecipes.removeAll(ritualsToRemove);
		ritualRecipes.addAll(ritualsToRemove);
	}
}