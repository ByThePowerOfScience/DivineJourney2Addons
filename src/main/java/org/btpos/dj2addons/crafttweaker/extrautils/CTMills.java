package org.btpos.dj2addons.crafttweaker.extrautils;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenDoc;
import crafttweaker.annotations.ZenRegister;
import epicsquid.roots.util.zen.ZenDocAppend;
import epicsquid.roots.util.zen.ZenDocArg;
import epicsquid.roots.util.zen.ZenDocClass;
import epicsquid.roots.util.zen.ZenDocMethod;
import org.btpos.dj2addons.impl.extrautilities.VExtraUtilities;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.Map;

@ZenRegister @ModOnly("extrautils2")
@ZenClass("dj2addons.extrautils2.Mills") @ZenDocClass(value="dj2addons.extrautils2.Mills",description = {
		"Handles Grid Power (GP) generator (mill) tweaks.",
		"Must be run with the preinit loader, specified with `#loader preinit` at the top of the file."
}) @ZenDocAppend("docs/include/extrautils.example.md")
public class CTMills {
	@ZenMethod @ZenDocMethod(description = {
			"Sets mill power scaling.",
			"Use `/dj2addons extrautils2` in-game to print all mill names."
	}, args= {
			@ZenDocArg(arg="millName", info="The name of the mill to change."),
			@ZenDocArg(arg="values", info="An associative array of [Grid Power threshold : production percentage].")
	}) @ZenDoc("Sets mill power scaling. See docs on GitHub.")
	public static void setScaling(String millName, Map<Float, Float> values) {
		final float[] arr = new float[values.size() * 2];
		int i = 0;
		for (Map.Entry<Float, Float> entry : new ArrayList<>(values.entrySet())) {
			arr[i] = entry.getKey();
			arr[i+1] = entry.getValue();
			i += 2;
		}
		VExtraUtilities.setScaling(millName.toUpperCase().replace(" ", "_").trim(), arr);
	}
}
