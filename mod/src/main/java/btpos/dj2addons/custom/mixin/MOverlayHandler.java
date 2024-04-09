package btpos.dj2addons.custom.mixin;

import btpos.dj2addons.custom.impl.StatusEffects;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import squeek.appleskin.client.HUDOverlayHandler;

import static org.spongepowered.asm.mixin.injection.At.Shift.AFTER;

/**
 * AppleSkin part of {@link btpos.dj2addons.initmixins.custom.minecraft.MSatuRegen MSatuRegen}.
 */
@Mixin(HUDOverlayHandler.class)
abstract class MOverlayHandler {
	@Inject(method="drawSaturationOverlay(FFLnet/minecraft/client/Minecraft;IIF)V", at=@At(target="Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V", value="INVOKE", shift=AFTER))
	private static void getRegenValue(float saturationGained, float saturationLevel, Minecraft mc, int left, int top, float alpha, CallbackInfo ci, @Share("dj2addons$regen") LocalIntRef regen) {
		EntityPlayer player = (EntityPlayer)mc.getRenderViewEntity();
		if (player == null || !player.isPotionActive(StatusEffects.UIEffectTrigger_HungerShankWave.apply(null))) {
			regen.set(-1);
		} else {
			regen.set(mc.ingameGUI.getUpdateCounter() % 25);
		}
	}
	
	@Redirect(method= "drawSaturationOverlay(FFLnet/minecraft/client/Minecraft;IIF)V", at=@At(target="Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V", value="INVOKE"))
	private static void saturationOverlayRegenEffect(GuiIngame instance, int x, int y, int textureX, int textureY, int width, int height,
	                                                 @Local(name="i") int i, @Share("dj2addons$regen") LocalIntRef regen)
	{
		if (i == regen.get()) {
			y -= 2;
		}
		instance.drawTexturedModalRect(x, y, textureX, textureY, width, height);
	}
}
