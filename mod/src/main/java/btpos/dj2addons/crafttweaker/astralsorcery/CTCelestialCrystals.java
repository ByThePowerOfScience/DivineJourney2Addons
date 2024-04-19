package btpos.dj2addons.crafttweaker.astralsorcery;

import btpos.dj2addons.api.astralsorcery.AstralSorcery;
import btpos.dj2addons.common.util.zendoc.ZenDocArg;
import btpos.dj2addons.common.util.zendoc.ZenDocClass;
import btpos.dj2addons.common.util.zendoc.ZenDocMethod;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * CraftTweaker hooks for Astral Sorcery's Crystals.
 * @see btpos.dj2addons.api.astralsorcery.AstralSorcery Java API
 */
@ZenRegister @ModOnly("astralsorcery")
@ZenClass("dj2addons.astralsorcery.Crystals") @ZenDocClass(value="dj2addons.astralsorcery.Crystals")
public class CTCelestialCrystals {
	@ZenMethod @ZenDocMethod(
			order=1,
			description="Sets the block that starmetal ore turns into when a celestial crystal is grown on top of it. Throws ClassCastException if `block` is not a Block.",
			args=@ZenDocArg(value ="block", info="The IItemStack to set the block to. Must be a Block type.")
	)
	public static void setStarmetalConversion(IItemStack block) {
		AstralSorcery.setStarmetalConversion(CraftTweakerMC.getBlock(block).getDefaultState());
	}
	
	@ZenMethod @ZenDocMethod(
			order=2,
			description="Scales the time that celestial crystals take to grow. For example, `scale = 0.5` would halve the time required.",
			args=@ZenDocArg(value ="scale", info="Value to multiply the time by.")
	)
	public static void scaleGrowthTime(double scale) {
		AstralSorcery.setCelestialCrystalGrowthScale(scale / 2);
	}
}
