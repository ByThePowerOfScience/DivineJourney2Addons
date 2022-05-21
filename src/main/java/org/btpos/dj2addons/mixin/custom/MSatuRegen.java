package org.btpos.dj2addons.mixin.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.FoodStats;
import net.minecraftforge.client.GuiIngameForge;
import org.btpos.dj2addons.impl.custom.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import squeek.appleskin.client.HUDOverlayHandler;

import static org.spongepowered.asm.mixin.injection.At.Shift.AFTER;
import static org.spongepowered.asm.mixin.injection.At.Shift.BEFORE;

/**
 * Handles the SatuRegen visuals, namely mimicking Regeneration's health bar heart cascade effect.
 */
@Mixin(GuiIngameForge.class)
public abstract class MSatuRegen extends GuiIngame  {
	public MSatuRegen(Minecraft mcIn) {
		super(mcIn);
	}
	
	private int regen = -1;
	private int i = -1;
	
	@Inject(method= "renderFood(II)V", locals= LocalCapture.CAPTURE_FAILSOFT, at=@At(target = "Lnet/minecraft/client/renderer/GlStateManager;enableBlend()V", value="INVOKE"))
	private void getRegenValue(int width, int height, CallbackInfo ci, EntityPlayer player) {
		regen = -1;
		if (player.isPotionActive(StatusEffects.SATUREGENTRIGGER))
		{
			regen = updateCounter % 25;
		}
	}
	
	@Inject(method= "renderFood(II)V", locals=LocalCapture.CAPTURE_FAILSOFT,  at=@At(target = "net/minecraftforge/client/GuiIngameForge.drawTexturedModalRect (IIIIII)V", value="INVOKE", ordinal = 0,  shift=BEFORE))
	private void getIteratorIndex(int width, int height, CallbackInfo ci, EntityPlayer player, int left, int top, boolean unused, FoodStats stats, int level, int i, int idx, int x, int y) {
		this.i = i;
	}
	
	@Redirect(method= "renderFood(II)V", at=@At(target = "net/minecraftforge/client/GuiIngameForge.drawTexturedModalRect (IIIIII)V", value="INVOKE"))
	private void regenEffect(GuiIngameForge instance, int x, int y, int textureX, int textureY, int width, int height) {
		if (i == regen)
			y -= 2;
		instance.drawTexturedModalRect(x, y, textureX, textureY, width, height);
	}
	
	
	
}

@Mixin(HUDOverlayHandler.class)
abstract class OverlayHandler {
	
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

