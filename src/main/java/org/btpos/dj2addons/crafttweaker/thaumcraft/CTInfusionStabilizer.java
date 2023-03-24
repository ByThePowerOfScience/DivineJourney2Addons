package org.btpos.dj2addons.crafttweaker.thaumcraft;

import net.minecraft.util.math.BlockPos;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import thaumcraft.api.crafting.IInfusionStabiliserExt;

@ZenClass("dj2addons.InfusionStabilizer")
public class CTInfusionStabilizer {
	private final IInfusionStabiliserExt source;
	
	public CTInfusionStabilizer(IInfusionStabiliserExt source) {
		this.source = source;
	}
	
	@ZenMethod
	public float getStabilizationAmount() {
		return source.getStabilizationAmount(null, new BlockPos(0, 0, 0));
	}
}
