package btpos.dj2addons.crafttweaker.extrautils2;

import btpos.dj2addons.api.extrautils2.ExtraUtilities;
import btpos.dj2addons.common.util.zendoc.ZenDocAppend;
import btpos.dj2addons.common.util.zendoc.ZenDocArg;
import btpos.dj2addons.common.util.zendoc.ZenDocClass;
import btpos.dj2addons.common.util.zendoc.ZenDocMethod;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenDoc;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Map;

@ZenRegister @ModOnly("extrautils2")
@ZenClass("dj2addons.extrautils2.Mills") @ZenDocClass(value="dj2addons.extrautils2.Mills", description = {
		"Handles Grid Power (GP) generator/mill tweaks.",
		"Must be run with the preinit loader, specified with `#loader preinit` at the top of the file."
}) @ZenDocAppend("docs/include/extrautils.example.md")
public class CTMills {
	@ZenMethod @ZenDocMethod(description = {
			"Sets mill power scaling.",
			"Use `/dj2addons extrautils2` in-game to print all mill names."
	}, args = {
			@ZenDocArg(value ="millName", info="The name of the mill to change."),
			@ZenDocArg(value ="values", info="An associative array of [Grid Power threshold : production percentage]. See example.")
	}) @ZenDoc("Sets mill power scaling. See docs on GitHub.")
	public static void setScaling(String millName, Map<Float, Float> values) {
		ExtraUtilities.setScaling(millName, values);
	}
	
	@ZenMethod @ZenDocMethod(order = 1, description = {
			"Roughly sets the maximum amount of power that each mill provides.",
			"Note that due to how the Lava and Water mills are calculated, the value provided is a theoretical maximum and will not likely be reached.",
			"Use `/dj2addons extrautils2` in-game to print all mill names."
	}, args= {
			@ZenDocArg(value ="millName", info="The name of the mill to change."),
			@ZenDocArg(value ="value", info="The amount of Grid Power the mill should provide.")
	}) @ZenDoc("Sets mill base GP production. See docs on GitHub.")
	public static void setBaseValue(String millName, Float value) {
		ExtraUtilities.setBasePower(millName, value);
	}
}
