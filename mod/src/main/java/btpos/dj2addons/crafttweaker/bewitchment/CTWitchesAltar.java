package btpos.dj2addons.crafttweaker.bewitchment;

import btpos.dj2addons.api.bewitchment.WitchesAltar;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.registry.AltarUpgrade;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import btpos.dj2addons.common.util.zendoc.ZenDocArg;
import btpos.dj2addons.common.util.zendoc.ZenDocClass;
import btpos.dj2addons.common.util.zendoc.ZenDocMethod;
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
			@ZenDocArg(value ="itemStack", info="The Item to add. Will be a TileEntityPlacedItem on the Altar."),
			@ZenDocArg(value ="gain", info="Amount added to Recharge Rate."),
			@ZenDocArg(value ="multiplier", info="Multiplier applied to total MP.")
	})
	public static void addUpgradeCup(IItemStack itemStack, int gain, double multiplier) {
		com.bewitchment.Util.registerAltarUpgradeItemStack(CraftTweakerMC.getItemStack(itemStack), new AltarUpgrade(AltarUpgrade.Type.CUP, gain, multiplier));
	}
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param oreDict The oreDict key to add.
	 * @param gain For cups and pentacles: flat bonus to Magical Power. No effect on swords or wands.
	 * @param multiplier For cups and swords: total multiplier. For wands, flat bonus multiplier.
	 */
	@ZenMethod @ZenDocMethod(order=2,args = {
			@ZenDocArg(value ="oreDict", info="The OreDict key to add."),
			@ZenDocArg(value ="gain", info="Amount added to Recharge Rate."),
			@ZenDocArg(value ="multiplier", info="Multiplier applied to total MP.")
	})
	public static void addUpgradeCup(IOreDictEntry oreDict, int gain, double multiplier) {
		com.bewitchment.Util.registerAltarUpgradeOreDict(oreDict.getName(), new AltarUpgrade(AltarUpgrade.Type.CUP, gain, multiplier));
	}
	
	@ZenMethod @ZenDocMethod(order=3,args = {
			@ZenDocArg(value ="block", info="The Block key to add."),
			@ZenDocArg(value ="gain", info="Amount added to Recharge Rate."),
			@ZenDocArg(value ="multiplier", info="Multiplier applied to total MP.")
	})
	public static void addUpgradeCup(IBlock block, int gain, double multiplier) {
		IBlockState state = block.getDefinition().getStateFromMeta(block.getMeta());
		WitchesAltar.addAltarUpgradeBlock(CraftTweakerMC.getBlockState(state), new AltarUpgrade(AltarUpgrade.Type.CUP, gain, multiplier));
	}
	
	
	
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param itemStack The ItemStack to add.
	 * @param bonus Flat bonus to Magical Power.
	 */
	@ZenMethod @ZenDocMethod(order=4,args = {
			@ZenDocArg(value ="itemStack", info="The Item to add."),
			@ZenDocArg(value ="bonus", info="Amount added to recharge rate.")
	})
	public static void addUpgradePentacle(IItemStack itemStack, int bonus) {
		com.bewitchment.Util.registerAltarUpgradeItemStack(CraftTweakerMC.getItemStack(itemStack), new AltarUpgrade(AltarUpgrade.Type.PENTACLE, bonus, 0.0));
	}
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param oreDict The oredict entry to add.
	 * @param bonus Flat bonus to Magical Power.
	 */
	@ZenMethod @ZenDocMethod(order=5,args = {
			@ZenDocArg(value ="oreDict", info="The OreDict key to add."),
			@ZenDocArg(value ="bonus", info="Amount added to recharge rate.")
	})
	public static void addUpgradePentacle(IOreDictEntry oreDict, int bonus) {
		com.bewitchment.Util.registerAltarUpgradeOreDict(oreDict.getName(), new AltarUpgrade(AltarUpgrade.Type.PENTACLE, bonus, 0.0));
	}
	
	@ZenMethod @ZenDocMethod(order=6,args = {
			@ZenDocArg(value ="block", info="The Block to add."),
			@ZenDocArg(value ="bonus", info="Amount added to recharge rate.")
	})
	public static void addUpgradePentacle(IBlock block, int bonus) {
		IBlockState state = block.getDefinition().getStateFromMeta(block.getMeta());
		WitchesAltar.addAltarUpgradeBlock(CraftTweakerMC.getBlockState(state),new AltarUpgrade(AltarUpgrade.Type.PENTACLE, bonus, 0.0));
	}
	
	
	
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param itemStack The ItemStack to add.
	 * @param bonus Flat bonus multiplier.
	 */
	@ZenMethod @ZenDocMethod(order=7,args = {
			@ZenDocArg(value ="itemStack", info="The Item to add."),
			@ZenDocArg(value ="bonus", info="Flat bonus to MP.")
	})
	public static void addUpgradeWand(IItemStack itemStack, double bonus) {
		com.bewitchment.Util.registerAltarUpgradeItemStack(CraftTweakerMC.getItemStack(itemStack), new AltarUpgrade(AltarUpgrade.Type.WAND, 0, bonus));
	}
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param oreDict The oreDict key to add.
	 * @param bonus Flat bonus multiplier.
	 */
	@ZenMethod @ZenDocMethod(order=8,args = {
			@ZenDocArg(value ="oreDict", info="The OreDict key to add."),
			@ZenDocArg(value ="bonus", info="Flat bonus to MP.")
	})
	public static void addUpgradeWand(IOreDictEntry oreDict, double bonus) {
		com.bewitchment.Util.registerAltarUpgradeOreDict(oreDict.getName(), new AltarUpgrade(AltarUpgrade.Type.WAND, 0, bonus));
	}
	
	@ZenMethod @ZenDocMethod(order=9,args = {
			@ZenDocArg(value ="block", info="The Block to add."),
			@ZenDocArg(value ="bonus", info="Flat bonus to MP.")
	})
	public static void addUpgradeWand(IBlock block, double bonus) {
		IBlockState state = block.getDefinition().getStateFromMeta(block.getMeta());
		WitchesAltar.addAltarUpgradeBlock(CraftTweakerMC.getBlockState(state), new AltarUpgrade(AltarUpgrade.Type.WAND, 0, bonus));
	}
	
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param itemStack The ItemStack to add.
	 * @param multiplier Multiplier applied to ME.
	 */
	@ZenMethod @ZenDocMethod(order=10,args = {
			@ZenDocArg(value ="itemStack", info="The Item to add."),
			@ZenDocArg(value ="multiplier", info="Flat multiplier to MP.")
	})
	public static void addUpgradeSword(IItemStack itemStack, double multiplier) {
		com.bewitchment.Util.registerAltarUpgradeItemStack(CraftTweakerMC.getItemStack(itemStack), new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, multiplier));
	}
	
	/**
	 * Adds a new Bewitchment altar upgrade.
	 * @param oreDict The oreDict key to add.
	 * @param multiplier Multiplier applied to ME.
	 */
	@ZenMethod @ZenDocMethod(order=11,args = {
			@ZenDocArg(value ="oreDict", info="The OreDict key to add."),
			@ZenDocArg(value ="multiplier", info="Flat multiplier to MP.")
	})
	public static void addUpgradeSword(IOreDictEntry oreDict, double multiplier) {
		com.bewitchment.Util.registerAltarUpgradeOreDict(oreDict.getName(), new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, multiplier));
	}
	
	@ZenMethod @ZenDocMethod(order=11, args = {
			@ZenDocArg(value ="block", info="The Block to add."),
			@ZenDocArg(value ="multiplier", info="Flat multiplier to MP.")
	})
	public static void addUpgradePentacle(IBlock block, double multiplier) {
		IBlockState state = block.getDefinition().getStateFromMeta(block.getMeta());
		WitchesAltar.addAltarUpgradeBlock(CraftTweakerMC.getBlockState(state), new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, multiplier));
	}
	
	
	
	
	@ZenMethod @ZenDocMethod(order=12,description = "Removes altar upgrades matching the item parameter.", args={@ZenDocArg(value ="iItemStack")})
	public static void removeUpgrade(IItemStack iItemStack) {
		WitchesAltar.removeUpgrade(CraftTweakerMC.getItemStack(iItemStack));
	}
	
	@ZenMethod @ZenDocMethod(order=13,description = "Removes altar upgrades matching an oredict entry.", args=@ZenDocArg(value ="oreDictEntry"))
	public static void removeUpgrade(IOreDictEntry oreDictEntry) {
		WitchesAltar.removeUpgrade(oreDictEntry.getName());
	}
	
	@ZenMethod @ZenDocMethod(order=14,description = "Removes altar upgrades matching a block state.", args=@ZenDocArg(value ="block"))
	public static void removeUpgrade(IBlock block) {
		WitchesAltar.removeUpgrade(CraftTweakerMC.getBlockState(block.getDefinition().getStateFromMeta(block.getMeta())));
	}
	
	@ZenMethod @ZenDocMethod(order=11, description = "Removes all registered altar upgrades.")
	public static void removeAllUpgrades() {
		BewitchmentAPI.ALTAR_UPGRADES.clear();
	}
}
