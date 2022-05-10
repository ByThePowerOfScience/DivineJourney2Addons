
package org.btpos.dj2addons.crafttweaker.botania;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.potions.IPotion;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.btpos.dj2addons.impl.botania.BrewHandler;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.value.IAny;
import vazkii.botania.api.brew.Brew;

@ZenRegister
@ZenClass("dj2addons.botania.Brews") @ModOnly("botania")
public class Brews {
	@ZenMethod
	public static Brew addBrew(String name, int cost, boolean shouldInfuseBloodPendant, boolean shouldInfuseIncenseStick, int amplifier, int duration, IPotion... potionEffects) {
		List<PotionEffect> effects = new ArrayList<>();
		
		for (IPotion p : potionEffects) {
			effects.add(CraftTweakerMC.getPotionEffect(p.makePotionEffect(duration, amplifier)));
		}
		
		return BrewHandler.registerBrew(name, cost, shouldInfuseBloodPendant, shouldInfuseIncenseStick, effects.toArray(new PotionEffect[0]));
	}
	
	@ZenMethod
	public static void addBrewRecipe(IAny brew, IItemStack... ingredients) {
		List<ItemStack> itemStacks = new ArrayList<>();
		
		for (IItemStack i : ingredients) {
			itemStacks.add(CraftTweakerMC.getItemStack(i));
		}
		
		BrewHandler.registerBrewRecipe(brew.as(Brew.class), itemStacks.toArray(new ItemStack[0]));
	}
}
