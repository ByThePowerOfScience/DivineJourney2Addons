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


@ZenRegister @ModOnly("bewitchment")
@ZenClass("dj2addons.bewitchment.Rituals") @ZenDocClass(value="dj2addons.bewitchment.Rituals", description = {
		"Handles Bewitchment ritual tweaks.",
		"Must be run with the DJ2Addons loader, specified with `#loader dj2addons` at the top of the ZS file."
}) @ZenDocAppend("docs/include/rituals.example.md")
public class Rituals {
	@ZenMethod @ZenDocMethod(order=1,args = {
			@ZenDocArg(arg="name",info="The name of the ritual to remove. e.g. \"biome_shift\".")
	}, description = "Removes a Bewitchment ritual by name.")
	public static void removeRitual(String name) {
		VModRecipes.addRitualToRemove(new VModRecipes.DummyRitual(name));
	}
}
