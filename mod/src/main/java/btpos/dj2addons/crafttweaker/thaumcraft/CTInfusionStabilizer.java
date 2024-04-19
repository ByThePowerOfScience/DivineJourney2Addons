package btpos.dj2addons.crafttweaker.thaumcraft;

import btpos.dj2addons.common.util.zendoc.ZenDocArg;
import btpos.dj2addons.common.util.zendoc.ZenDocClass;
import btpos.dj2addons.common.util.zendoc.ZenDocMethod;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import thaumcraft.api.crafting.IInfusionStabiliser;
import thaumcraft.api.crafting.IInfusionStabiliserExt;

@ZenRegister
@ModOnly("thaumcraft")
@ZenClass("dj2addons.thaumcraft.InfusionStabilizer") @ZenDocClass("dj2addons.thaumcraft.InfusionStabilizer")
public class CTInfusionStabilizer {
	private final IInfusionStabiliserExt source;
	
	public CTInfusionStabilizer(IInfusionStabiliserExt source) {
		this.source = source;
	}
	
	@ZenMethod @ZenDocMethod(description = "Gets the stabilization amount of this stabilizer.")
	public float getStabilizationAmount() {
		return source.getStabilizationAmount(null, new BlockPos(0, 0, 0));
	}
	
	@ZenMethod @ZenDocMethod(description = "Gets an InfusionStabilizer object for a given block.", args=@ZenDocArg("block"))
	public static CTInfusionStabilizer getInfusionStabilizer(IBlock block) {
		Block b = CraftTweakerMC.getBlock(block);
		if (b instanceof IInfusionStabiliser) {
			return new CTInfusionStabilizer((IInfusionStabiliserExt) b);
		} else {
			throw new IllegalArgumentException("Block " + block + " is not an infusion stabilizer.");
		}
	}
}
