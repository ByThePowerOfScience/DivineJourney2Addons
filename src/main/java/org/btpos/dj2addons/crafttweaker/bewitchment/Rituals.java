package org.btpos.dj2addons.crafttweaker.bewitchment;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import org.btpos.dj2addons.impl.bewitchment.VModRecipes;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.dj2addons.bewitchment.Rituals") @ModOnly("bewitchment")
public class Rituals {
	@ZenMethod
	public static void removeRitual(String name) {
		VModRecipes.removeRitual(new VModRecipes.DummyRitual(name));
	}
}
