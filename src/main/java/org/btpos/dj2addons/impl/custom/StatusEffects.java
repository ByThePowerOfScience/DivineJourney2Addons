package org.btpos.dj2addons.impl.custom;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import org.btpos.dj2addons.DJ2Addons;
import org.btpos.dj2addons.registry.ModPotions;
import org.jetbrains.annotations.NotNull;

public class StatusEffects {
	public static final Potion SATUREGENTRIGGER = ModPotions.Registered.saturegen;
	
	
	public static class SatuRegen extends Potion {
		private static final int COLOR = MobEffects.SATURATION.getLiquidColor();
		
		public SatuRegen() {
			super(false, COLOR);
			this.setRegistryName(new ResourceLocation(DJ2Addons.MOD_ID, "saturegen"));
			this.setPotionName(DJ2Addons.MOD_ID + ".effect.regenerateHunger");
			setIconIndex(7, 0); //TODO assign hunger shank icon
			setEffectiveness(0.25D);
		}
		
		@Override
		public void performEffect(@NotNull EntityLivingBase entityLivingBaseIn, int amplifier) {
			if (entityLivingBaseIn instanceof EntityPlayer) {
				((EntityPlayer)entityLivingBaseIn).getFoodStats().addStats(amplifier + 1, 1.0F);
			}
		}
		
		@Override
		public boolean isReady(int duration, int amplifier) {
			int k = 50 >> amplifier;
			
			if (k > 0)
			{
				return duration % k == 0;
			}
			else
			{
				return true;
			}
		}
	}
}
