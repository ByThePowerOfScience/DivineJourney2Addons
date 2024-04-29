package btpos.dj2addons.common.modrefs;

import btpos.dj2addons.api.bewitchment.WitchesAltar;
import crafttweaker.mc1120.commands.CTChatCommand;
import net.minecraftforge.fml.common.Loader;
import btpos.dj2addons.crafttweaker.CTCommandHandler;

public class CCraftTweaker {
	public static void loadCommandHandler() {
		CTChatCommand.registerCommand(new CTCommandHandler());
	}
	
	public static void postInit() {
		if (Loader.isModLoaded("bewitchment"))
			WitchesAltar.Internal.executeUpgradeRemoval();
	}
}
