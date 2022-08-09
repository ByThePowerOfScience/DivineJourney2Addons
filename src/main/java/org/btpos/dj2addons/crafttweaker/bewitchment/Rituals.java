package org.btpos.dj2addons.crafttweaker.bewitchment;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import epicsquid.roots.util.zen.ZenDocAppend;
import epicsquid.roots.util.zen.ZenDocArg;
import epicsquid.roots.util.zen.ZenDocClass;
import epicsquid.roots.util.zen.ZenDocMethod;
import org.btpos.dj2addons.impl.bewitchment.VModRecipes;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenDocAppend("docs/include/rituals.example.md")
@ZenRegister @ModOnly("bewitchment")
@ZenClass("dj2addons.bewitchment.Rituals") @ZenDocClass("dj2addons.bewitchment.Rituals")
public class Rituals {
	@ZenMethod @ZenDocMethod(order=1,args = {
			@ZenDocArg(arg="name",info="The name of the ritual to remove. e.g. \"biome_shift\".")
	}, description = "Removes a Bewitchment ritual by name.")
	public static void removeRitual(String name) {
		VModRecipes.removeRitual(new VModRecipes.DummyRitual(name));
	}
}
