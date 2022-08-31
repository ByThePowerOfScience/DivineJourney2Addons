package org.btpos.dj2addons.crafttweaker.zen.bewitchment;

import com.bewitchment.api.registry.AltarUpgrade;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import epicsquid.roots.util.zen.ZenDocArg;
import epicsquid.roots.util.zen.ZenDocClass;
import epicsquid.roots.util.zen.ZenDocMethod;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister @ModOnly("bewitchment")
@ZenClass("dj2addons.bewitchment.WitchesAltar") @ZenDocClass(value="dj2addons.bewitchment.WitchesAltar", description = "Adds upgrades to the Witches Altar.")
public class WitchesAltar {
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param itemStack The ItemStack to add.
	 * @param gain For cups and pentacles: flat bonus to Magical Power. No effect on swords or wands.
	 * @param multiplier For cups and swords: total multiplier. For wands, flat bonus multiplier.
	 */
	@ZenMethod @ZenDocMethod(args = {
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
	@ZenMethod @ZenDocMethod(args = {
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
	@ZenMethod @ZenDocMethod(args = {
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
	@ZenMethod @ZenDocMethod(args = {
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
	@ZenMethod @ZenDocMethod(args = {
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
	@ZenMethod @ZenDocMethod(args = {
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
	@ZenMethod @ZenDocMethod(args = {
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
	@ZenMethod @ZenDocMethod(args = {
			@ZenDocArg(arg="oreDict", info="The OreDict key to add."),
			@ZenDocArg(arg="multiplier", info="Additive multiplier to MP.")
	})
	public static void addUpgradeSword(IOreDictEntry oreDict, double multiplier) {
		com.bewitchment.Util.registerAltarUpgradeOreDict(oreDict.getName(), new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, multiplier));
	}
	
}
