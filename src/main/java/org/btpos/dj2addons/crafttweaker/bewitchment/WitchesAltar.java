package org.btpos.dj2addons.crafttweaker.bewitchment;

import com.bewitchment.api.registry.AltarUpgrade;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("dj2addons.bewitchment.WitchesAltar")
@ZenRegister @ModOnly("bewitchment")
public class WitchesAltar {
	
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param itemStack The ItemStack to add.
	 * @param gain For cups and pentacles: flat bonus to Magical Power. No effect on swords or wands.
	 * @param multiplier For cups and swords: total multiplier. For wands, flat bonus multiplier.
	 */
	@ZenMethod
	public static void addUpgradeCup(IItemStack itemStack, int gain, double multiplier) {
		com.bewitchment.Util.registerAltarUpgradeItemStack(CraftTweakerMC.getItemStack(itemStack), new AltarUpgrade(AltarUpgrade.Type.CUP, gain, multiplier));
	}
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param oreDict The oreDict key to add.
	 * @param gain For cups and pentacles: flat bonus to Magical Power. No effect on swords or wands.
	 * @param totalMult For cups and swords: total multiplier. For wands, flat bonus multiplier.
	 */
	@ZenMethod
	public static void addUpgradeCup(IOreDictEntry oreDict, int gain, double totalMult) {
		com.bewitchment.Util.registerAltarUpgradeOreDict(oreDict.getName(), new AltarUpgrade(AltarUpgrade.Type.CUP, gain, totalMult));
	}
	
	
	
	
	
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param itemStack The ItemStack to add.
	 * @param bonus Flat bonus to Magical Power.
	 */
	@ZenMethod
	public static void addUpgradePentacle(IItemStack itemStack, int bonus) {
		com.bewitchment.Util.registerAltarUpgradeItemStack(CraftTweakerMC.getItemStack(itemStack), new AltarUpgrade(AltarUpgrade.Type.PENTACLE, bonus, 0.0));
	}
	
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param oreDict The oredict entry to add.
	 * @param bonus Flat bonus to Magical Power.
	 */
	@ZenMethod
	public static void addUpgradePentacle(IOreDictEntry oreDict, int bonus) {
		com.bewitchment.Util.registerAltarUpgradeOreDict(oreDict.getName(), new AltarUpgrade(AltarUpgrade.Type.PENTACLE, bonus, 0.0));
	}
	
	
	
	
	
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param itemStack The ItemStack to add.
	 * @param multBoost Flat bonus multiplier.
	 */
	@ZenMethod
	public static void addUpgradeWand(IItemStack itemStack, double multBoost) {
		com.bewitchment.Util.registerAltarUpgradeItemStack(CraftTweakerMC.getItemStack(itemStack), new AltarUpgrade(AltarUpgrade.Type.WAND, 0, multBoost));
	}
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param oreDict The oreDict key to add.
	 * @param multBoost Flat bonus multiplier.
	 */
	@ZenMethod
	public static void addUpgradeWand(IOreDictEntry oreDict, double multBoost) {
		com.bewitchment.Util.registerAltarUpgradeOreDict(oreDict.getName(), new AltarUpgrade(AltarUpgrade.Type.WAND, 0, multBoost));
	}
	
	
	
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param itemStack The ItemStack to add.
	 * @param multiplier Multiplier applied to ME.
	 */
	@ZenMethod
	public static void addUpgradeSword(IItemStack itemStack, double multiplier) {
		com.bewitchment.Util.registerAltarUpgradeItemStack(CraftTweakerMC.getItemStack(itemStack), new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, multiplier));
	}
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param oreDict The oreDict key to add.
	 * @param multiplier Multiplier applied to ME.
	 */
	@ZenMethod
	public static void addUpgradeSword(IOreDictEntry oreDict, double multiplier) {
		com.bewitchment.Util.registerAltarUpgradeOreDict(oreDict.getName(), new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, multiplier));
	}
	
}
