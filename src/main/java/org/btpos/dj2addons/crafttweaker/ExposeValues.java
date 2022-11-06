package org.btpos.dj2addons.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import org.btpos.dj2addons.core.DJ2AddonsCore;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("dj2addons.Values")
public class ExposeValues {
	@ZenMethod
	public static boolean shouldWriteAerogelTooltip() {
		return DJ2AddonsCore.shouldWriteAerogelTooltip;
	}
}
