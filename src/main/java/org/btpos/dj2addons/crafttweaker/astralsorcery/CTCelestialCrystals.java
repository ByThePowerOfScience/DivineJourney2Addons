package org.btpos.dj2addons.crafttweaker.astralsorcery;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import epicsquid.roots.util.zen.ZenDocArg;
import epicsquid.roots.util.zen.ZenDocClass;
import epicsquid.roots.util.zen.ZenDocMethod;
import net.minecraft.block.Block;
import org.btpos.dj2addons.impl.astralsorcery.VAstralSorcery;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister @ModOnly("astralsorcery")
@ZenClass("dj2addons.astralsorcery.Crystals") @ZenDocClass(value="dj2addons.astralsorcery.Crystals")
public class CTCelestialCrystals {
	@ZenMethod @ZenDocMethod(
			order=1,
			description="Sets the block that starmetal ore turns into when a celestial crystal is grown on top of it.",
			args=@ZenDocArg(arg="block", info="The IItemStack to set the block to. Must be a block type.")
	)
	public static void setStarmetalConversion(IItemStack block) {
		Block b = CraftTweakerMC.getBlock(block);
		if (b == null) {
			CraftTweakerAPI.logError("No block found for " + block.getDisplayName());
			return;
		}
		VAstralSorcery.starmetalConversion = b.getDefaultState();
		
	}
	
	@ZenMethod @ZenDocMethod(
			order=2,
			description="Scales the time that celestial crystals take to grow. For example, `scale = 0.5` would halve the time required.",
			args=@ZenDocArg(arg="scale", info="Value to multiply the time by.")
	)
	public static void scaleGrowthTime(double scale) {
		VAstralSorcery.celestialCrystalGrowthScale = scale / 2;
	}
}
