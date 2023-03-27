
package org.btpos.dj2addons.crafttweaker.botania;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.potions.IPotionEffect;
import org.btpos.dj2addons.util.zendoc.*;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import org.btpos.dj2addons.impl.api.botania.BrewHandler;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import vazkii.botania.api.brew.Brew;

import java.util.ArrayList;
import java.util.List;

@ZenDocAppend({"docs/include/brews.example.md"})
@ZenRegister @ModOnly("botania")
@ZenClass("dj2addons.botania.Brews") @ZenDocClass("dj2addons.botania.Brews") @ZenDocInclude(CTBrews.ZenBrew.class)
public class CTBrews {
	
	@ZenRegister @ModOnly("botania")
	@ZenClass("dj2addons.botania.Brew") @ZenDocClass(value="dj2addons.botania.Brew", onlyInOther = true)
	public static class ZenBrew {
		private final Brew internal;
		private ZenBrew(Brew brew) {
			internal = brew;
			BrewHandler.registerBrew(internal);
		}
		
		@ZenMethod("disableBloodPendant") @ZenDocMethod(order=1, description = "Disables the Tainted Blood Pendant recipe for this brew.")
		public void setDisableBloodPendant() {
			getInternal().setNotBloodPendantInfusable();
		}
		
		@ZenMethod("disableIncenseStick") @ZenDocMethod(order=2, description = "Disables the Incense Stick recipe for this brew.")
		public void setDisableIncenseStick() {
			getInternal().setNotIncenseInfusable();
		}
		
		public Brew getInternal() {
			return internal;
		}
	}
	
	@ZenMethod @ZenDocMethod(order=1, args = {
			@ZenDocArg(value ="key", info="The registry key to be assigned to the Brew."),
			@ZenDocArg(value ="cost", info="The base mana cost of the brew. Amplified automatically for flasks, etc."),
			@ZenDocArg(value ="potionEffects", info="A/an array of potion effects.")
	}, description = {
			"Creates a Brew instance and registers its existence with Botania, then returns it.",
			"The key is set to \"botania.brews.\\<name\\>\" and the color is taken from the source potion."
	})
	public static ZenBrew makeBrew(String key, int cost, IPotionEffect... potionEffects) {
		List<PotionEffect> effects = new ArrayList<>();
		for (IPotionEffect p : potionEffects) {
			effects.add(CraftTweakerMC.getPotionEffect(p));
		}
		return new ZenBrew(BrewHandler.buildBrew(key, cost, effects.toArray(new PotionEffect[0])));
	}
	
	@ZenMethod @ZenDocMethod(order=2, args = {
			@ZenDocArg(value ="key", info="The registry key to be assigned to the Brew."),
			@ZenDocArg(value ="name", info="The display name of the Brew. e.g. \"Flask of <name>\""),
			@ZenDocArg(value ="cost", info="The base mana cost of the brew. Amplified automatically for flasks, etc."),
			@ZenDocArg(value ="color", info="The hexadecimal color of the brew."),
			@ZenDocArg(value ="potionEffects", info="A/an array of potion effects.")
	}, description = {
			"Creates a Brew instance and registers its existence with Botania, then returns it."
	})
	public static ZenBrew makeBrew(String key, String name, int cost, int color, IPotionEffect... potionEffects) {
		List<PotionEffect> effects = new ArrayList<>();
		for (IPotionEffect p : potionEffects) {
			effects.add(CraftTweakerMC.getPotionEffect(p));
		}
		return new ZenBrew(BrewHandler.buildBrew(key,name,color,cost,effects.toArray(new PotionEffect[0])));
	}
	
	@ZenMethod @ZenDocMethod(order = 3, args = {
			@ZenDocArg(value = "brew", info = "The Brew instance to register a recipe for."),
			@ZenDocArg(value = "ingredients", info = "An array of item ingredients to set as the recipe.")
	}, description = {
			"Registers the recipe for a given brew."
	})
	public static void addBrewRecipe(ZenBrew brew, IItemStack... ingredients) {
		List<ItemStack> itemStacks = new ArrayList<>();
		
		for (IItemStack i : ingredients) {
			itemStacks.add(CraftTweakerMC.getItemStack(i));
		}
		
		BrewHandler.registerBrewRecipe(brew.getInternal(), itemStacks.toArray(new ItemStack[0]));
	}
	
	@ZenMethod @ZenDocMethod(order=4, description = "Enables the Tainted Blood Pendant of Warp Ward. Only valid if Thaumcraft is installed.")
	public static void enableWarpWardPendant() {
		BrewHandler.shouldEnableWarpWardPendant = true;
	}
}
