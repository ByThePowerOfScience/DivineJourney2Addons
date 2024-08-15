package btpos.dj2addons.custom.impl;

import btpos.dj2addons.common.util.Util;
import btpos.dj2addons.core.DJ2Addons;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import org.jetbrains.annotations.NotNull;

public class PotionSatuRegen extends Potion {
	private static final int COLOR = MobEffects.SATURATION.getLiquidColor();
	
	public PotionSatuRegen() {
		super(false, COLOR);
		this.setRegistryName(Util.loc("saturegen"));
		this.setPotionName(DJ2Addons.MOD_ID + ".effect.regenerateHunger");
		setIconIndex(7, 0); //TODO assign hunger shank icon
		setBeneficial();
		setEffectiveness(0.25D);
	}
	
	@Override
	public void performEffect(@NotNull EntityLivingBase entityLivingBaseIn, int amplifier) {
		if (entityLivingBaseIn instanceof EntityPlayer) {
			((EntityPlayer) entityLivingBaseIn).getFoodStats().addStats(amplifier + 1, 1.0F);
		}
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		int k = 50 >> amplifier;
		
		if (k > 0) {
			return duration % k == 0;
		} else {
			return true;
		}
	}
}
