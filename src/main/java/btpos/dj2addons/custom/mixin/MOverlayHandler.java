package btpos.dj2addons.custom.mixin;

import btpos.dj2addons.api.client.SatuRegen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import squeek.appleskin.client.HUDOverlayHandler;

import static org.spongepowered.asm.mixin.injection.At.Shift.AFTER;

/**
 * AppleSkin part of {@link btpos.dj2addons.initmixins.custom.minecraft.MSatuRegen MSatuRegen}.
 */
@Mixin(HUDOverlayHandler.class)
abstract class MOverlayHandler {
	
	@Unique private static int dj2addons$i = -1;
	@Unique private static int dj2addons$regen = -1;
	
	@Inject(
			method = "drawSaturationOverlay(FFLnet/minecraft/client/Minecraft;IIF)V",
			at = @At(
					target = "Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V",
					value = "INVOKE",
					shift = AFTER
			)
	)
	private static void getRegenValue(float saturationGained, float saturationLevel, Minecraft mc, int left, int top, float alpha, CallbackInfo ci) {
		EntityPlayer player = (EntityPlayer) mc.getRenderViewEntity();
		if (player == null || SatuRegen.Internal.shouldActivateHungerShankWave(player)) {
			dj2addons$regen = -1;
		} else {
			dj2addons$regen = mc.ingameGUI.getUpdateCounter() % 25;
		}
	}
	
	@ModifyVariable(
			remap = false,
			method = "drawSaturationOverlay(FFLnet/minecraft/client/Minecraft;IIF)V",
			name = "i",
			at = @At("LOAD")
	)
	private static int getIteratorVal(int value) {
		dj2addons$i = value;
		return value;
	}
	
	@Redirect(
			method = "drawSaturationOverlay(FFLnet/minecraft/client/Minecraft;IIF)V",
			at = @At(
					target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V",
					value = "INVOKE"
			)
	)
	private static void saturationOverlayRegenEffect(GuiIngame instance, int x, int y, int textureX, int textureY, int width, int height) {
		if (dj2addons$i == dj2addons$regen) {
			y -= 2;
		}
		instance.drawTexturedModalRect(x, y, textureX, textureY, width, height);
	}
}
