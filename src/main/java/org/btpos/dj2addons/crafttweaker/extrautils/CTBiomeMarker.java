package org.btpos.dj2addons.crafttweaker.extrautils;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenDoc;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBiome;
import epicsquid.roots.util.zen.ZenDocArg;
import epicsquid.roots.util.zen.ZenDocClass;
import epicsquid.roots.util.zen.ZenDocMethod;
import org.btpos.dj2addons.impl.api.extrautilities.VExtraUtilities;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ModOnly("extrautils2")
@ZenClass("dj2addons.extrautils2.BiomeMarker") @ZenDocClass(value="dj2addons.extrautils2.BiomeMarker", description = {
	"Disallows certain biomes to be sampled by the Biome Marker (for use in the Quantum Quarry)."
})
public class CTBiomeMarker {
	@ZenMethod @ZenDocMethod(description = "Excludes a biome from the biome marker.", args = {
			@ZenDocArg(arg = "biome", info = "The biome to exclude.")
	}) @ZenDoc("Excludes biome from Biome Marker. See docs on GitHub.")
	public static void excludeBiome(IBiome biome) {
		VExtraUtilities.addExcludedBiome(CraftTweakerMC.getBiome(biome).getRegistryName());
	}
}
