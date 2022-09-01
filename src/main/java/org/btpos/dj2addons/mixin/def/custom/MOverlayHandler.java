package org.btpos.dj2addons.mixin.def.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.entity.player.EntityPlayer;
import org.btpos.dj2addons.impl.custom.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import squeek.appleskin.client.HUDOverlayHandler;

import static org.spongepowered.asm.mixin.injection.At.Shift.AFTER;

/**
 * AppleSkin part of {@link org.btpos.dj2addons.mixin.init.custom.MSatuRegen MSatuRegen}.
 */
@Mixin(HUDOverlayHandler.class)
abstract class MOverlayHandler {
	
	private static int i = -1;
	private static int regen = -1;
	
	@Inject(method="drawSaturationOverlay(FFLnet/minecraft/client/Minecraft;IIF)V", at=@At(target="Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V", value="INVOKE", shift=AFTER))
	private static void getRegenValue(float saturationGained, float saturationLevel, Minecraft mc, int left, int top, float alpha, CallbackInfo ci) {
		EntityPlayer player = (EntityPlayer)mc.getRenderViewEntity();
		if (player == null)
			return;
		regen = -1;
		if (player.isPotionActive(StatusEffects.SATUREGENTRIGGER)) {
			regen = mc.ingameGUI.getUpdateCounter() % 25;
		}
	}
	
	@ModifyVariable(remap=false,method="drawSaturationOverlay(FFLnet/minecraft/client/Minecraft;IIF)V", name="i", at=@At(value="LOAD"))
	private static int getIteratorVal(int value) {
		i = value;
		return value;
	}
	
	@Redirect(method= "drawSaturationOverlay(FFLnet/minecraft/client/Minecraft;IIF)V", at=@At(target="Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V", value="INVOKE"))
	private static void saturationOverlayRegenEffect(GuiIngame instance, int x, int y, int textureX, int textureY, int width, int height) {
		if (i == regen) {
			y -= 2;
		}
		instance.drawTexturedModalRect(x, y, textureX, textureY, width, height);
	}
}
