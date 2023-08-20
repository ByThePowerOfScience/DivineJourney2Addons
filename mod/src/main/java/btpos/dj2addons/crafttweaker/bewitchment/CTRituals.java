package btpos.dj2addons.crafttweaker.bewitchment;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import btpos.dj2addons.common.util.zendoc.ZenDocAppend;
import btpos.dj2addons.common.util.zendoc.ZenDocArg;
import btpos.dj2addons.common.util.zendoc.ZenDocClass;
import btpos.dj2addons.common.util.zendoc.ZenDocMethod;
import btpos.dj2addons.api.bewitchment.Rituals;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;


@ZenRegister @ModOnly("bewitchment")
@ZenClass("dj2addons.bewitchment.Rituals") @ZenDocClass(value="dj2addons.bewitchment.Rituals", description = {
		"Handles Bewitchment ritual tweaks.",
		"Must be run with the preinit loader, specified with `#loader preinit` at the top of the ZS file."
}) @ZenDocAppend("docs/include/rituals.example.md")
public class CTRituals {
	@ZenMethod @ZenDocMethod(order=1,args = {
			@ZenDocArg(value ="name",info="The name of the ritual to remove. e.g. \"biome_shift\".")
	}, description = "Removes a Bewitchment ritual by name.")
	public static void removeRitual(String name) {
		Rituals.removeRitual(name);
	}
}
