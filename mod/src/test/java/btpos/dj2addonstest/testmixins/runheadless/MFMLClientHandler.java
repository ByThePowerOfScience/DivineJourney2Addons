package btpos.dj2addonstest.testmixins.runheadless;

import net.minecraftforge.fml.client.FMLClientHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FMLClientHandler.class)
public abstract class MFMLClientHandler {
	@Redirect(
			remap = false,
			method = "beginMinecraftLoading",
			at = @At(
					target = "Lnet/minecraftforge/fml/client/SplashProgress;start()V",
					value = "INVOKE"
			)
	)
	private void dontDoProgressBar() {}
}

