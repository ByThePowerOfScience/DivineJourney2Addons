
package btpos.dj2addons.crafttweaker.botania;

import btpos.dj2addons.api.botania.Brews;
import btpos.dj2addons.common.util.zendoc.ZenDocAppend;
import btpos.dj2addons.common.util.zendoc.ZenDocArg;
import btpos.dj2addons.common.util.zendoc.ZenDocClass;
import btpos.dj2addons.common.util.zendoc.ZenDocInclude;
import btpos.dj2addons.common.util.zendoc.ZenDocMethod;
import btpos.dj2addons.crafttweaker.CraftTweakerHelpers;
import btpos.dj2addons.crafttweaker.botania.CTBrews.ZenBrewWrapper;
import btpos.dj2addons.util.fastutilutils.FastUtilCollectors;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.potions.IPotionEffect;
import net.minecraft.potion.PotionEffect;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.api.brew.IBrewContainer;
import vazkii.botania.common.brew.BrewMod;

import java.util.Arrays;

/**
 * @see btpos.dj2addons.api.botania.Brews Java API
 */
@ZenRegister @ModOnly("botania")
@ZenClass("dj2addons.botania.Brews")
@ZenDocClass(value = "dj2addons.botania.Brews", description = "APIs for handling Botania's brews, including adding and removing recipes, creating new brews, and making recipes that are only valid for specific output containers only.")
@ZenDocInclude(ZenBrewWrapper.class) @ZenDocAppend({"docs/include/brews.example.md"})
public class CTBrews {
	@ZenRegister @ModOnly("botania")
	@ZenClass("dj2addons.botania.Brew")
	@ZenDocClass(value="dj2addons.botania.Brew", description = "Represents a Brew for use in CraftTweaker.", onlyInOther = true)
	public static class ZenBrewWrapper {
		private final Brew internal;
		private ZenBrewWrapper(Brew brew) {
			internal = brew;
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
	
	@ZenMethod @ZenDocMethod(order=0, description="Gets a registered brew by name.", args = {
			@ZenDocArg(value="key", info="The key for the brew, e.g. \"botania.brews.warpWard\". Use \"/ct dj2addons brews\" in-game to get the keys for all registered brews.")
	})
	public static ZenBrewWrapper getBrew(String key) {
		return new ZenBrewWrapper(BotaniaAPI.brewMap.get(key));
	}
	
	@ZenMethod @Deprecated
	public static ZenBrewWrapper makeBrew(String key, int cost, IPotionEffect... potionEffects) {
		return newBrew(key, cost, potionEffects);
	}
	
	@ZenMethod @ZenDocMethod(order=1, args = {
			@ZenDocArg(value ="key", info="The registry key to be assigned to the Brew."),
			@ZenDocArg(value ="cost", info="The base mana cost of the brew. Amplified automatically for flasks, etc."),
			@ZenDocArg(value ="potionEffects", info="An array of potion effects.")
	}, description = {
			"Creates a Brew instance and registers its existence with Botania, then returns it.",
			"The key is set to \"botania.brews.[key]\" and the color is taken from the source potion."
	})
	public static ZenBrewWrapper newBrew(String key, int cost, IPotionEffect[] potionEffects) {
		return new ZenBrewWrapper(new BrewMod(key, cost, Arrays.stream(potionEffects)
		                                                       .map(CraftTweakerMC::getPotionEffect)
		                                                       .toArray(PotionEffect[]::new)));
	}
	
	@ZenMethod @Deprecated
	public static ZenBrewWrapper makeBrew(String key, String name, int cost, int color, IPotionEffect... potionEffects) {
		return newBrew(key, name, cost, color, (IPotionEffect[]) potionEffects);
	}
	
	@ZenMethod @ZenDocMethod(order=2, args = {
			@ZenDocArg(value ="key", info="The registry key to be assigned to the Brew."),
			@ZenDocArg(value ="name", info="The translation key for the display name of the Brew. e.g. \"Flask of <name>\""),
			@ZenDocArg(value ="cost", info="The base mana cost of the brew. Amplified automatically by Botania for flasks, etc."),
			@ZenDocArg(value ="color", info="The hexadecimal color of the brew."),
			@ZenDocArg(value ="potionEffects", info="An array of potion effects.")
	}, description = {
			"Creates a Brew instance and registers its existence with Botania, then returns it."
	})
	public static ZenBrewWrapper newBrew(String key, String name, int cost, int color, IPotionEffect[] potionEffects) {
		Brew brew = new Brew(key, name, color, cost, Arrays.stream(potionEffects)
		                                                   .map(CraftTweakerMC::getPotionEffect)
		                                                   .toArray(PotionEffect[]::new));
		BotaniaAPI.registerBrew(brew);
		return new ZenBrewWrapper(brew);
	}
	
	@ZenMethod @ZenDocMethod(order=3, description="Remove all recipes for the given brew.", args = {
			@ZenDocArg(value="key", info="The key for the brew, e.g. \"botania.brews.warpWard\". Use \"/ct dj2addons brews\" in-game to get the keys for all registered brews.")
	})
	public static void removeRecipe(final String key) {
		BotaniaAPI.brewRecipes.removeIf(recipeBrew -> key.equals(recipeBrew.getBrew().getKey()));
	}
	
	@ZenMethod @ZenDocMethod(order=3, description="Removes a registered brew recipe by name and ingredients.", args = {
			@ZenDocArg(value="key", info="The key for the brew, e.g. \"botania.brews.warpWard\". Use \"/ct dj2addons brews\" in-game to get the keys for all registered brews."),
			@ZenDocArg(value="ingredients", info="The set of ItemStacks/OreDict keys of the recipe to remove."),
	})
	public static void removeRecipe(String key, Object[] ingredientsList) {
		Brews.removeRecipe(key, mapIItemStacksToItemStack(ingredientsList));
	}
	
	@ZenMethod @Deprecated
	public static void addBrewRecipe(ZenBrewWrapper brew, IItemStack... ingredients) {
		addStandardBrewRecipe(brew, ingredients);
	}
	
	@ZenMethod @ZenDocMethod(order = 4, args = {
			@ZenDocArg(value = "brew", info = "The Brew instance to register a recipe for."),
			@ZenDocArg(value = "ingredients", info = "An array of item ingredients/oredict keys to set as the recipe.")
	}, description = {
			"Registers the recipe for a given brew."
	})
	public static void addStandardBrewRecipe(ZenBrewWrapper brew, Object[] ingredients) {
		BotaniaAPI.registerBrewRecipe(brew.getInternal(), mapIItemStacksToItemStack(ingredients));
	}
	
	
	@ZenMethod @ZenDocMethod(order = 5, args = {
			@ZenDocArg(value = "brew", info = "The Brew instance to register a recipe for."),
			@ZenDocArg(value = "allowedContainers", info="The containers that this brew recipe will be allowed for. (e.g. <botania:vial:0> = Managlass Vial, <botania:vial:1> = Alfglass Flask)"),
			@ZenDocArg(value = "ingredients", info = "An array of item ingredients/oredict keys to set as the recipe.")
	}, description = {
			"Registers the recipe for brew with a restricted set of valid containers.",
			"Use in combination with ModTweaker's `mods.botania.Brew.removeRecipe()` to replace Botania's own brew recipes with output-specific versions."
	})
	public static void addOutputRestrictedBrewRecipe(ZenBrewWrapper brew, IItemStack[] allowedContainers, IItemStack[] ingredients) {
		Brews.registerOutputRestrictedBrewRecipe(
				brew.getInternal(),
				Arrays.stream(allowedContainers)
				      .map(CraftTweakerMC::getItemStack)
				      .peek(is -> { if (!(is.getItem() instanceof IBrewContainer)) throw CraftTweakerHelpers.fillExc(new IllegalArgumentException("All \"allowedContainers\" must implement `IBrewContainer`.")); })
				      .collect(FastUtilCollectors.toObjectOpenHashSet()),
				mapIItemStacksToItemStack(ingredients));
	}
	
	@ModOnly("thaumcraft")
	@ZenMethod @ZenDocMethod(order=6, description = "Enables the Tainted Blood Pendant of Warp Ward. Only valid if Thaumcraft is installed.")
	public static void enableWarpWardPendant() {
		Brews.enableWarpWardPendant();
	}
	
	
	
	private static Object[] mapIItemStacksToItemStack(Object[] ingredients) {
		for (int i = 0; i < ingredients.length; i++) {
			if (ingredients[i] instanceof IItemStack) {
				ingredients[i] = CraftTweakerMC.getItemStack((IItemStack) ingredients[i]);
			}
		}
		return ingredients;
	}
}


