package btpos.dj2addons.common.modrefs;

import crafttweaker.mc1120.commands.CTChatCommand;
import net.minecraftforge.fml.common.Loader;
import btpos.dj2addons.common.CommandHandler;
import btpos.dj2addons.api.impl.bewitchment.VAltarUpgrades;

public class CCraftTweaker {
	public static void loadCommandHandler() {
		CTChatCommand.registerCommand(new CommandHandler());
	}
	
	public static void postInit() {
		if (Loader.isModLoaded("bewitchment"))
			VAltarUpgrades.executeUpgradeRemoval();
	}
}
