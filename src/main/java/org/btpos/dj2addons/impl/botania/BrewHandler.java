
package org.btpos.dj2addons.impl.botania;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.common.brew.BrewMod;

public class BrewHandler {
	public BrewHandler() {
	}
	
	public static Brew buildBrew(String key, String name, int color, int cost, PotionEffect... effects) {
		return new Brew(key, name, color, cost, effects);
	}
	
	public static Brew buildBrew(String key, int cost,  PotionEffect... potionEffects) {
		return new BrewMod(key, cost, potionEffects);
	}
	
	public static void registerBrew(Brew b) {
		BotaniaAPI.registerBrew(b);
	}
	
	
	
	public static void registerBrewRecipe(Brew brew, ItemStack[] objects) {
		BotaniaAPI.registerBrewRecipe(brew, (Object[])objects);
	}
}
