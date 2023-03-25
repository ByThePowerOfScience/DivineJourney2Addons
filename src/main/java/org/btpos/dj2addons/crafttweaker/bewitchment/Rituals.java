package org.btpos.dj2addons.crafttweaker.bewitchment;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import org.btpos.dj2addons.util.zendoc.ZenDocAppend;
import org.btpos.dj2addons.util.zendoc.ZenDocArg;
import org.btpos.dj2addons.util.zendoc.ZenDocClass;
import org.btpos.dj2addons.util.zendoc.ZenDocMethod;
import org.btpos.dj2addons.impl.api.bewitchment.VModRecipes;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;


@ZenRegister @ModOnly("bewitchment")
@ZenClass("dj2addons.bewitchment.Rituals") @ZenDocClass(value="dj2addons.bewitchment.Rituals", description = {
		"Handles Bewitchment ritual tweaks.",
		"Must be run with the preinit loader, specified with `#loader preinit` at the top of the ZS file."
}) @ZenDocAppend("docs/include/rituals.example.md")
public class Rituals {
	@ZenMethod @ZenDocMethod(order=1,args = {
			@ZenDocArg(arg="name",info="The name of the ritual to remove. e.g. \"biome_shift\".")
	}, description = "Removes a Bewitchment ritual by name.")
	public static void removeRitual(String name) {
		VModRecipes.addRitualToRemove(new VModRecipes.DummyRitual(name));
	}
}
