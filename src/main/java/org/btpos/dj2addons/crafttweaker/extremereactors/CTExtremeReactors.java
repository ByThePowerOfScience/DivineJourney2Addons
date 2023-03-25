package org.btpos.dj2addons.crafttweaker.extremereactors;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import org.btpos.dj2addons.util.zendoc.ZenDocAppend;
import org.btpos.dj2addons.util.zendoc.ZenDocArg;
import org.btpos.dj2addons.util.zendoc.ZenDocClass;
import org.btpos.dj2addons.util.zendoc.ZenDocMethod;
import org.btpos.dj2addons.impl.api.extremereactors.VExtremeReactors;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister @ModOnly("bigreactors")
@ZenClass("dj2addons.extremereactors.ExtremeReactors")
@ZenDocClass(value="dj2addons.extremereactors.ExtremeReactors", description = {
		"Handles general ExtremeReactors tweaks.",
		"Must be run with the preinit loader, specified with `#loader preinit` at the top of the ZS file."
}) @ZenDocAppend("docs/include/extremereactors.misc.example.md")
public class CTExtremeReactors {
	@ZenMethod @ZenDocMethod(
			order=1,
			description = "Sets the maximum energy the reactor can store in its output buffer.",
			args=@ZenDocArg(arg="value", info="The maximum energy stored."))
	public static void setMaxEnergyStored(long value) {
		VExtremeReactors.maxEnergyStored = value;
	}
}
