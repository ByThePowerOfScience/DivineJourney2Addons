
package org.btpos.dj2addons.crafttweaker.botania;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenDoc;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.potions.IPotion;
import java.util.ArrayList;
import java.util.List;

import crafttweaker.api.potions.IPotionEffect;
import crafttweaker.mc1120.potions.MCPotion;
import epicsquid.roots.util.zen.ZenDocAppend;
import epicsquid.roots.util.zen.ZenDocArg;
import epicsquid.roots.util.zen.ZenDocClass;
import epicsquid.roots.util.zen.ZenDocMethod;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.btpos.dj2addons.DJ2Addons;
import org.btpos.dj2addons.impl.botania.BrewHandler;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenMethodStatic;
import stanhebben.zenscript.annotations.ZenSetter;
import stanhebben.zenscript.value.IAny;
import vazkii.botania.api.brew.Brew;

@ZenDocAppend("docs/include/brews.example.md")
@ZenRegister @ModOnly("botania")
@ZenClass(DJ2Addons.MOD_ID + ".botania.Brews") @ZenDocClass(DJ2Addons.MOD_ID + ".botania.Brews")
public class Brews {
	
	@ZenRegister @ModOnly("botania")
	@ZenClass(DJ2Addons.MOD_ID + ".botania.Brew")
	public static class ZenBrew {
		private final Brew internal;
		private ZenBrew(Brew brew) {
			internal = brew;
			BrewHandler.registerBrew(internal);
		}
		
		@ZenMethod("disableBloodPendant")
		public static void setDisableBloodPendant(ZenBrew instance) {
			instance.getInternal().setNotBloodPendantInfusable();
		}
		
		@ZenMethod("disableIncenseStick")
		public static void setDisableIncenseStick(ZenBrew instance) {
			instance.getInternal().setNotIncenseInfusable();
		}
		
		public Brew getInternal() {
			return internal;
		}
	}
	
	@ZenMethod @ZenDocMethod(order=1, args = {
			@ZenDocArg(arg="key", info="The registry key to be assigned to the Brew."),
			@ZenDocArg(arg="cost", info="The base mana cost of the brew. Amplified automatically for flasks, etc."),
			@ZenDocArg(arg="potionEffects", info="A/an array of potion effects.")
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
			@ZenDocArg(arg="key", info="The registry key to be assigned to the Brew."),
			@ZenDocArg(arg="name", info="The display name of the Brew. e.g. \"Flask of <name>\""),
			@ZenDocArg(arg="cost", info="The base mana cost of the brew. Amplified automatically for flasks, etc."),
			@ZenDocArg(arg="color", info="The hexadecimal color of the brew."),
			@ZenDocArg(arg="potionEffects", info="A/an array of potion effects.")
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
			@ZenDocArg(arg = "brew", info = "The Brew instance to register a recipe for."),
			@ZenDocArg(arg = "ingredients", info = "An array of item ingredients to set as the recipe.")
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
}
