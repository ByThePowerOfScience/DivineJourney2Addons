package btpos.dj2addons.api.crafttweaker.mysticalagriculture;

import com.blakebr0.mysticalagriculture.crafting.ReprocessorManager;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenDoc;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import btpos.dj2addons.common.util.zendoc.ZenDocArg;
import btpos.dj2addons.common.util.zendoc.ZenDocClass;
import btpos.dj2addons.common.util.zendoc.ZenDocMethod;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister @ModOnly("mysticalagriculture")
@ZenClass("dj2addons.mysticalagriculture.SeedReprocessor") @ZenDocClass()
public class CTSeedReprocessor {
	@ZenMethod
	@ZenDocMethod(description = "Adds a recipe for the seed reprocessor.", args = {
			@ZenDocArg(value ="input", info="The input item stack."),
			@ZenDocArg(value ="output", info ="The output item stack.")
	}) @ZenDoc("Adds a recipe for the seed reprocessor.")
	public static void addRecipe(IItemStack input, IItemStack output) {
		ReprocessorManager.addRecipe(CraftTweakerMC.getItemStack(input), CraftTweakerMC.getItemStack(output));
	}
}
