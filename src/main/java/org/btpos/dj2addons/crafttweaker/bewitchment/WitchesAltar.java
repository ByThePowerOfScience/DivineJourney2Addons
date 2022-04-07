package org.btpos.dj2addons.crafttweaker.bewitchment;

import com.bewitchment.api.registry.AltarUpgrade;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("dj2addons.bewitchment.WitchesAltar")
@ZenRegister @ModOnly("bewitchment")
public class WitchesAltar {
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param item The Item to add.
	 * @param gain For cups and pentacles: flat bonus to Magical Power. No effect on swords or wands.
	 * @param multiplier For cups and swords: total multiplier. For wands, flat bonus multiplier.
	 */
	@ZenMethod
	public static void addUpgradeCup(Item item, int gain, double multiplier) {
		com.bewitchment.Util.registerAltarUpgradeItem(item, new AltarUpgrade(AltarUpgrade.Type.CUP, gain, multiplier));
	}
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param itemStack The ItemStack to add.
	 * @param gain For cups and pentacles: flat bonus to Magical Power. No effect on swords or wands.
	 * @param multiplier For cups and swords: total multiplier. For wands, flat bonus multiplier.
	 */
	@ZenMethod
	public static void addUpgradeCup(ItemStack itemStack, int gain, double multiplier) {
		com.bewitchment.Util.registerAltarUpgradeItemStack(itemStack, new AltarUpgrade(AltarUpgrade.Type.CUP, gain, multiplier));
	}
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param oreDict The oreDict key to add.
	 * @param gain For cups and pentacles: flat bonus to Magical Power. No effect on swords or wands.
	 * @param totalMult For cups and swords: total multiplier. For wands, flat bonus multiplier.
	 */
	@ZenMethod
	public static void addUpgradeCup(String oreDict, int gain, double totalMult) {
		com.bewitchment.Util.registerAltarUpgradeOreDict(oreDict, new AltarUpgrade(AltarUpgrade.Type.CUP, gain, totalMult));
	}
	
	
	
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param item The Item to add.
	 * @param bonus Flat bonus to Magical Power.
	 */
	@ZenMethod
	public static void addUpgradePentacle(Item item, int bonus) {
		com.bewitchment.Util.registerAltarUpgradeItem(item, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, bonus, 0.0));
	}
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param itemStack The ItemStack to add.
	 * @param bonus Flat bonus to Magical Power.
	 */
	@ZenMethod
	public static void addUpgradePentacle(ItemStack itemStack, int bonus) {
		com.bewitchment.Util.registerAltarUpgradeItemStack(itemStack, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, bonus, 0.0));
	}
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param oreDict The oreDict key to add.
	 * @param bonus Flat bonus to Magical Power.
	 */
	@ZenMethod
	public static void addUpgradePentacle(String oreDict, int bonus) {
		com.bewitchment.Util.registerAltarUpgradeOreDict(oreDict, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, bonus, 0.0));
	}
	
	
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param item The Item to add.
	 * @param multBoost Flat bonus multiplier.
	 */
	@ZenMethod
	public static void addUpgradeWand(Item item, double multBoost) {
		com.bewitchment.Util.registerAltarUpgradeItem(item, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, multBoost));
	}
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param itemStack The ItemStack to add.
	 * @param multBoost Flat bonus multiplier.
	 */
	@ZenMethod
	public static void addUpgradeWand(ItemStack itemStack, double multBoost) {
		com.bewitchment.Util.registerAltarUpgradeItemStack(itemStack, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, multBoost));
	}
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param oreDict The oreDict key to add.
	 * @param multBoost Flat bonus multiplier.
	 */
	@ZenMethod
	public static void addUpgradeWand(String oreDict, double multBoost) {
		com.bewitchment.Util.registerAltarUpgradeOreDict(oreDict, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, multBoost));
	}
	
	
	
	
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param item The Item to add.
	 * @param multiplier Flat bonus multiplier.
	 */
	@ZenMethod
	public static void addUpgradeSword(Item item, double multiplier) {
		com.bewitchment.Util.registerAltarUpgradeItem(item, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, multiplier));
	}
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param itemStack The ItemStack to add.
	 * @param multiplier Flat bonus multiplier.
	 */
	@ZenMethod
	public static void addUpgradeSword(ItemStack itemStack, double multiplier) {
		com.bewitchment.Util.registerAltarUpgradeItemStack(itemStack, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, multiplier));
	}
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param oreDict The oreDict key to add.
	 * @param multiplier Flat bonus multiplier.
	 */
	@ZenMethod
	public static void addUpgradeSword(String oreDict, double multiplier) {
		com.bewitchment.Util.registerAltarUpgradeOreDict(oreDict, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, multiplier));
	}
	
}
