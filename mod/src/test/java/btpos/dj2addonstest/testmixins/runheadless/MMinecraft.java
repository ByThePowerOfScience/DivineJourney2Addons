package btpos.dj2addonstest.testmixins.runheadless;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Avoids potential OpenGL segfault on Mac by running minecraft without a display at all.
 */
@Mixin(Minecraft.class)
public abstract class MMinecraft {
	@Redirect(
			method = "init",
			at = @At(
					target = "Lnet/minecraft/client/Minecraft;createDisplay()V",
					value = "INVOKE"
			)
	)
	private void dontCreateDisplay(Minecraft instance) {}
	
	@Redirect(
			method = "init",
			at = @At(
					target = "Lnet/minecraft/client/renderer/OpenGlHelper;initializeTextures()V",
					value = "INVOKE"
			)
	)
	private void dontInitializeTextures() {}
}

