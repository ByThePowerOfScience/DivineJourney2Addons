
package btpos.dj2addons.api.crafttweaker.botania;

import btpos.dj2addons.common.util.zendoc.*;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.IllegalDataException;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.potions.IPotionEffect;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import btpos.dj2addons.api.crafttweaker.botania.CTBrews.ZenBrewWrapper;
import btpos.dj2addons.api.impl.botania.BrewHandler;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.api.brew.IBrewContainer;

import java.util.Arrays;
import java.util.stream.Collectors;

@ZenDocAppend({"docs/include/brews.example.md"})
@ZenRegister @ModOnly("botania")
@ZenClass("dj2addons.botania.Brews") @ZenDocClass("dj2addons.botania.Brews") @ZenDocInclude(ZenBrewWrapper.class)
public class CTBrews {
	@ZenRegister @ModOnly("botania")
	@ZenClass("dj2addons.botania.Brew") @ZenDocClass(value="dj2addons.botania.Brew", onlyInOther = true)
	public static class ZenBrewWrapper {
		private final Brew internal;
		private ZenBrewWrapper(Brew brew) {
			internal = brew;
			BrewHandler.registerBrew(internal);
		}
		
		@ZenMethod("disableBloodPendant") @ZenDocMethod(order=1, description = "Disables the Tainted Blood Pendant recipe for this brew. Returns self.")
		public ZenBrewWrapper setDisableBloodPendant() {
			getInternal().setNotBloodPendantInfusable();
			return this;
		}
		
		@ZenMethod("disableIncenseStick") @ZenDocMethod(order=2, description = "Disables the Incense Stick recipe for this brew. Returns self.")
		public ZenBrewWrapper setDisableIncenseStick() {
			getInternal().setNotIncenseInfusable();
			return this;
		}
		
		public Brew getInternal() {
			return internal;
		}
	}
	
	@ZenMethod @Deprecated
	public static ZenBrewWrapper makeBrew(String key, int cost, IPotionEffect... potionEffects) {
		return newBrew(key, cost, potionEffects);
	}
	
	@ZenMethod @ZenDocMethod(order=1, args = {
			@ZenDocArg(value ="key", info="The registry key to be assigned to the Brew."),
			@ZenDocArg(value ="cost", info="The base mana cost of the brew. Amplified automatically for flasks, etc."),
			@ZenDocArg(value ="potionEffects", info="A/an array of potion effects.")
	}, description = {
			"Creates a Brew instance and registers its existence with Botania, then returns it.",
			"The key is set to \"botania.brews.\\<key\\>\" and the color is taken from the source potion."
	})
	public static ZenBrewWrapper newBrew(String key, int cost, IPotionEffect[] potionEffects) {
		return new ZenBrewWrapper(BrewHandler.buildBrew(key, cost, Arrays.stream(potionEffects)
		                                                                 .map(CraftTweakerMC::getPotionEffect)
		                                                                 .toArray(PotionEffect[]::new)));
	}
	
	@ZenMethod @Deprecated
	public static ZenBrewWrapper makeBrew(String key, String name, int cost, int color, IPotionEffect... potionEffects) {
		return newBrew(key, name, cost, color, potionEffects);
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
	public static ZenBrewWrapper newBrew(String key, String name, int cost, int color, IPotionEffect[] potionEffects) {
		return new ZenBrewWrapper(BrewHandler.buildBrew(key, name, color, cost, Arrays.stream(potionEffects)
		                                                                              .map(CraftTweakerMC::getPotionEffect)
		                                                                              .toArray(PotionEffect[]::new)));
	}
	
	@ZenMethod @Deprecated
	public static void addBrewRecipe(ZenBrewWrapper brew, IItemStack... ingredients) {
		addStandardBrewRecipe(brew, ingredients);
	}
	
	@ZenMethod @ZenDocMethod(order = 3, args = {
			@ZenDocArg(value = "brew", info = "The Brew instance to register a recipe for."),
			@ZenDocArg(value = "ingredients", info = "An array of item ingredients to set as the recipe.")
	}, description = {
			"Registers the recipe for a given brew."
	})
	public static void addStandardBrewRecipe(ZenBrewWrapper brew, IItemStack[] ingredients) {
		BrewHandler.registerBrewRecipe(brew.getInternal(),
		                               Arrays.stream(ingredients)
		                                     .map(CraftTweakerMC::getItemStack)
		                                     .toArray(ItemStack[]::new));
	}
	
	
	
	@ZenMethod @ZenDocMethod(order = 3, args = {
			@ZenDocArg(value = "brew", info = "The Brew instance to register a recipe for."),
			@ZenDocArg(value="allowedContainers", info="The containers that this brew recipe will be allowed for. (e.g. <botania:vial:0> = Managlass Vial, <botania:vial:1> = Alfglass Flask)"),
			@ZenDocArg(value = "ingredients", info = "An array of item ingredients to set as the recipe.")
	}, description = {
			"Registers the recipe for brew with a restricted set of valid containers.",
			"Use in combination with ModTweaker's `mods.botania.Brew.removeRecipe()` to replace Botania's own brew recipes with output-specific versions."
	})
	public static void addOutputRestrictedBrewRecipe(ZenBrewWrapper brew, IItemStack[] allowedContainers, IItemStack[] ingredients) {
		BrewHandler.registerOutputRestrictedBrewRecipe(brew.getInternal(),
		                                               Arrays.stream(allowedContainers)
		                                                     .map(CraftTweakerMC::getItemStack)
		                                                     .peek(is -> {
			                                                     if (!(is.getItem() instanceof IBrewContainer))
				                                                     throw new IllegalDataException("All \"allowedContainers\" must implement `IBrewContainer`.");
		                                                     })
		                                                     .collect(Collectors.toSet()),
		                                               Arrays.stream(ingredients)
		                                                     .map(CraftTweakerMC::getItemStack)
		                                                     .toArray(ItemStack[]::new));
	}
	
	@ZenMethod @ZenDocMethod(order=4, description = "Enables the Tainted Blood Pendant of Warp Ward. Only valid if Thaumcraft is installed.")
	public static void enableWarpWardPendant() {
		BrewHandler.shouldEnableWarpWardPendant = true;
	}
}
