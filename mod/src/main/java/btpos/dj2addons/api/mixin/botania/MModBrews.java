//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package btpos.dj2addons.api.mixin.botania;

import btpos.dj2addons.api.botania.Brews;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.common.brew.BrewMod;
import vazkii.botania.common.brew.ModBrews;

@Mixin({ModBrews.class})
public class MModBrews {
	public MModBrews() {
	}
	
	@Redirect(remap = false, method = "initTC()V",
			at = @At(target = "Lvazkii/botania/common/brew/BrewMod;setNotBloodPendantInfusable()Lvazkii/botania/api/brew/Brew;", value = "INVOKE"))
	private static Brew enableWarpWardPendant(BrewMod instance) {
		if (Brews.Internal.shouldEnableWarpWardPendant)
			return instance;
		else
			return instance.setNotBloodPendantInfusable();
	}
}
