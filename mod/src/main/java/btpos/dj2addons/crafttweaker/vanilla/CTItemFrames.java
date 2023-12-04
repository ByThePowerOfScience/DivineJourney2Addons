package btpos.dj2addons.crafttweaker.vanilla;

import btpos.dj2addons.api.minecraft.ItemFrames;
import btpos.dj2addons.common.util.zendoc.ZenDocClass;
import btpos.dj2addons.common.util.zendoc.ZenDocMethod;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.minecraft.CraftTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("dj2addons.vanilla.ItemFrames")
@ZenDocClass(value="dj2addons.vanilla.ItemFrames")
public class CTItemFrames {
	
	@ZenMethod @ZenDocMethod(description = "Prevents item frames from being placed on ")
	public static void disallowPlaceOn(IBlock block) {
		ItemFrames.disallowPlaceOn(CraftTweakerMC.getBlockState(block.getDefinition().getStateFromMeta(block.getMeta())));
	}
}
