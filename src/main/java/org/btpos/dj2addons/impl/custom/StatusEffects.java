package org.btpos.dj2addons.impl.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StatusEffects {
	public static Potion SATUREGENTRIGGER = MobEffects.REGENERATION;
	public static class SatuRegen extends Potion {
		private static final int COLOR = 0xFFFFFF;
		
		protected SatuRegen() {
			super(false, COLOR);
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
