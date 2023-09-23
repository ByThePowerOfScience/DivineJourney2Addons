package btpos.dj2addons.initmixins.custom.minecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.FoodStats;
import net.minecraftforge.client.GuiIngameForge;
import btpos.dj2addons.custom.impl.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static org.spongepowered.asm.mixin.injection.At.Shift.BEFORE;

/**
 * Handles the SatuRegen visuals, namely mimicking Regeneration's health bar heart cascade effect.
 */
@Mixin(GuiIngameForge.class)
public abstract class MSatuRegen extends GuiIngame  {
	public MSatuRegen(Minecraft mcIn) {
		super(mcIn);
	}
	
	@Unique private int dj2addons$regen = -1;
	@Unique private int dj2addons$renderIterator = -1;
	
	@Inject(method= "renderFood(II)V", locals= LocalCapture.CAPTURE_FAILSOFT, at=@At(target = "Lnet/minecraft/client/renderer/GlStateManager;enableBlend()V", value="INVOKE"))
	private void getRegenValue(int width, int height, CallbackInfo ci, EntityPlayer player) {
		dj2addons$regen = -1;
		if (player.isPotionActive(StatusEffects.UIEffectTrigger_HungerShankWave.apply(null)))
		{
			dj2addons$regen = updateCounter % 25;
		}
	}
	
	@Inject(method= "renderFood(II)V", locals=LocalCapture.CAPTURE_FAILSOFT,  at=@At(target = "net/minecraftforge/client/GuiIngameForge.drawTexturedModalRect (IIIIII)V", value="INVOKE", ordinal = 0,  shift=BEFORE))
	private void getIteratorIndex(int width, int height, CallbackInfo ci, EntityPlayer player, int left, int top, boolean unused, FoodStats stats, int level, int i, int idx, int x, int y) {
		this.dj2addons$renderIterator = i;
	}
	
	@Redirect(method= "renderFood(II)V", at=@At(target = "net/minecraftforge/client/GuiIngameForge.drawTexturedModalRect (IIIIII)V", value="INVOKE"))
	private void regenEffect(GuiIngameForge instance, int x, int y, int textureX, int textureY, int width, int height) {
		if (dj2addons$renderIterator == dj2addons$regen)
			y -= 2;
		instance.drawTexturedModalRect(x, y, textureX, textureY, width, height);
	}
	
	
	
}



