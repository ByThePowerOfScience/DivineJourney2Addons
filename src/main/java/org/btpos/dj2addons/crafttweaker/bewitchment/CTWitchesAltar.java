package org.btpos.dj2addons.crafttweaker.bewitchment;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.registry.AltarUpgrade;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import epicsquid.roots.util.zen.ZenDocArg;
import epicsquid.roots.util.zen.ZenDocClass;
import epicsquid.roots.util.zen.ZenDocMethod;
import org.btpos.dj2addons.impl.api.bewitchment.VAltarUpgrades;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister @ModOnly("bewitchment")
@ZenClass("dj2addons.bewitchment.WitchesAltar") @ZenDocClass(value="dj2addons.bewitchment.WitchesAltar", description = "Adds upgrades to the Witches Altar.")
public class CTWitchesAltar {
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param itemStack The ItemStack to add.
	 * @param gain For cups and pentacles: flat bonus to Magical Power. No effect on swords or wands.
	 * @param multiplier For cups and swords: total multiplier. For wands, flat bonus multiplier.
	 */
	@ZenMethod @ZenDocMethod(order=1,args = {
			@ZenDocArg(arg="itemStack", info="The OreDict key to add."),
			@ZenDocArg(arg="gain", info="Flat bonus to Magical Power."),
			@ZenDocArg(arg="multiplier", info="Multiplicative multiplier applied to MP.")
	})
	public static void addUpgradeCup(IItemStack itemStack, int gain, double multiplier) {
		com.bewitchment.Util.registerAltarUpgradeItemStack(CraftTweakerMC.getItemStack(itemStack), new AltarUpgrade(AltarUpgrade.Type.CUP, gain, multiplier));
	}
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param oreDict The oreDict key to add.
	 * @param gain For cups and pentacles: flat bonus to Magical Power. No effect on swords or wands.
	 * @param totalMult For cups and swords: total multiplier. For wands, flat bonus multiplier.
	 */
	@ZenMethod @ZenDocMethod(order=2,args = {
			@ZenDocArg(arg="oreDict", info="The OreDict key to add."),
			@ZenDocArg(arg="gain", info="Flat bonus to Magical Power."),
			@ZenDocArg(arg="totalMult", info="Multiplicative multiplier applied to MP.")
	})
	public static void addUpgradeCup(IOreDictEntry oreDict, int gain, double totalMult) {
		com.bewitchment.Util.registerAltarUpgradeOreDict(oreDict.getName(), new AltarUpgrade(AltarUpgrade.Type.CUP, gain, totalMult));
	}
	
	
	
	
	
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param itemStack The ItemStack to add.
	 * @param bonus Flat bonus to Magical Power.
	 */
	@ZenMethod @ZenDocMethod(order=3,args = {
			@ZenDocArg(arg="itemStack", info="The Item to add."),
			@ZenDocArg(arg="bonus", info="Flat bonus to Magical Power.")
	})
	public static void addUpgradePentacle(IItemStack itemStack, int bonus) {
		com.bewitchment.Util.registerAltarUpgradeItemStack(CraftTweakerMC.getItemStack(itemStack), new AltarUpgrade(AltarUpgrade.Type.PENTACLE, bonus, 0.0));
	}
	
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param oreDict The oredict entry to add.
	 * @param bonus Flat bonus to Magical Power.
	 */
	@ZenMethod @ZenDocMethod(order=4,args = {
			@ZenDocArg(arg="oreDict", info="The OreDict key to add."),
			@ZenDocArg(arg="bonus", info="Flat bonus to Magical Power.")
	})
	public static void addUpgradePentacle(IOreDictEntry oreDict, int bonus) {
		com.bewitchment.Util.registerAltarUpgradeOreDict(oreDict.getName(), new AltarUpgrade(AltarUpgrade.Type.PENTACLE, bonus, 0.0));
	}
	
	
	
	
	
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param itemStack The ItemStack to add.
	 * @param multBoost Flat bonus multiplier.
	 */
	@ZenMethod @ZenDocMethod(order=5,args = {
			@ZenDocArg(arg="itemStack", info="The Item to add."),
			@ZenDocArg(arg="multBoost", info="Additive multiplier to MP.")
	})
	public static void addUpgradeWand(IItemStack itemStack, double multBoost) {
		com.bewitchment.Util.registerAltarUpgradeItemStack(CraftTweakerMC.getItemStack(itemStack), new AltarUpgrade(AltarUpgrade.Type.WAND, 0, multBoost));
	}
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param oreDict The oreDict key to add.
	 * @param multBoost Flat bonus multiplier.
	 */
	@ZenMethod @ZenDocMethod(order=6,args = {
			@ZenDocArg(arg="oreDict", info="The OreDict key to add."),
			@ZenDocArg(arg="multBoost", info="Additive multiplier to MP.")
	})
	public static void addUpgradeWand(IOreDictEntry oreDict, double multBoost) {
		com.bewitchment.Util.registerAltarUpgradeOreDict(oreDict.getName(), new AltarUpgrade(AltarUpgrade.Type.WAND, 0, multBoost));
	}
	
	
	
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param itemStack The ItemStack to add.
	 * @param multiplier Multiplier applied to ME.
	 */
	@ZenMethod @ZenDocMethod(order=7,args = {
			@ZenDocArg(arg="itemStack", info="The Item to add."),
			@ZenDocArg(arg="multiplier", info="Additive multiplier to MP.")
	})
	public static void addUpgradeSword(IItemStack itemStack, double multiplier) {
		com.bewitchment.Util.registerAltarUpgradeItemStack(CraftTweakerMC.getItemStack(itemStack), new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, multiplier));
	}
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param oreDict The oreDict key to add.
	 * @param multiplier Multiplier applied to ME.
	 */
	@ZenMethod @ZenDocMethod(order=8,args = {
			@ZenDocArg(arg="oreDict", info="The OreDict key to add."),
			@ZenDocArg(arg="multiplier", info="Additive multiplier to MP.")
	})
	public static void addUpgradeSword(IOreDictEntry oreDict, double multiplier) {
		com.bewitchment.Util.registerAltarUpgradeOreDict(oreDict.getName(), new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, multiplier));
	}
	
	
	
	
	@ZenMethod @ZenDocMethod(order=9,description = "Removes altar upgrades matching the item parameter.")
	public static void removeUpgrade(IItemStack iis) {
		VAltarUpgrades.removeUpgrade(CraftTweakerMC.getItemStack(iis));
	}
	
	@ZenMethod @ZenDocMethod(order=10,description = "Removes altar upgrades matching an oredict entry.")
	public static void removeUpgrade(IOreDictEntry oreDictEntry) {
		VAltarUpgrades.removeUpgrade(oreDictEntry.getName());
	}
	
	@ZenMethod @ZenDocMethod(order=11,description = "Removes altar upgrades matching the default metadata of the given ItemStack parameter. Used if the default method does not work.")
	public static void removeUpgradeItem(IItemStack iis) {
		VAltarUpgrades.removeUpgrade(CraftTweakerMC.getItemStack(iis).getItem());
	}
	
	public static void removeAllUpgrades() {
		BewitchmentAPI.ALTAR_UPGRADES.clear();
	}
}
