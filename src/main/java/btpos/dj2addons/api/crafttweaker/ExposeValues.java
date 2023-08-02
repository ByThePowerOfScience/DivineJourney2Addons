package btpos.dj2addons.api.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import btpos.dj2addonscore.common.CoreInfo;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("dj2addons.Values")
public class ExposeValues {
	@ZenMethod
	public static boolean shouldWriteAerogelTooltip() {
		return CoreInfo.shouldWriteAerogelTooltip;
	}
}
