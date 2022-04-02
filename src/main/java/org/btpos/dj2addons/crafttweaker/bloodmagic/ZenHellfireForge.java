package org.btpos.dj2addons.crafttweaker.bloodmagic;

import crafttweaker.annotations.ZenRegister;
import org.btpos.dj2addons.impl.bloodmagic.HellfireForgeValues;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.dj2addons.bloodmagic.HellfireForge")
public class ZenHellfireForge {
	
	@ZenMethod
	public static void modifyTicksRequired(int ticksRequired) {
		HellfireForgeValues.setTicksRequired(ticksRequired);
	}
}
