
package org.btpos.dj2addons.impl.botania;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.common.brew.BrewMod;

public class BrewHandler {
	public BrewHandler() {
	}
	
	private static Brew buildBrew(String key, String name, int color, int cost, boolean shouldInfuseBloodPendant, boolean shouldInfuseIncenseStick, PotionEffect... potionEffects) {
		Brew b = new Brew(key, name, color, cost, potionEffects);
		if (!shouldInfuseBloodPendant) {
			b.setNotBloodPendantInfusable();
		}
		
		if (!shouldInfuseIncenseStick) {
			b.setNotIncenseInfusable();
		}
		
		return b;
	}
	
	private static Brew buildBrew(String name, int cost, boolean shouldInfuseBloodPendant, boolean shouldInfuseIncenseStick, PotionEffect... potionEffects) {
		Brew b = new BrewMod(name, cost, potionEffects);
		if (!shouldInfuseBloodPendant) {
			b.setNotBloodPendantInfusable();
		}
		
		if (!shouldInfuseIncenseStick) {
			b.setNotIncenseInfusable();
		}
		
		return b;
	}
	
	public static Brew registerBrew(String key, String name, int color, int cost, boolean shouldInfuseBloodPendant, boolean shouldInfuseIncenseStick, PotionEffect... potionEffects) {
		Brew b = buildBrew(key, name, color, cost, shouldInfuseBloodPendant, shouldInfuseIncenseStick, potionEffects);
		BotaniaAPI.registerBrew(b);
		return b;
	}
	
	public static Brew registerBrew(String name, int cost, boolean shouldInfuseBloodPendant, boolean shouldInfuseIncenseStick, PotionEffect... potionEffects) {
		return buildBrew(name, cost, shouldInfuseBloodPendant, shouldInfuseIncenseStick, potionEffects);
	}
	
	public static void registerBrewRecipe(Brew brew, ItemStack[] objects) {
		BotaniaAPI.registerBrewRecipe(brew, (Object[])objects);
	}
}
