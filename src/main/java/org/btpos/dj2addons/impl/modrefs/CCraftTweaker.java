package org.btpos.dj2addons.impl.modrefs;

import crafttweaker.mc1120.commands.CTChatCommand;
import org.btpos.dj2addons.crafttweaker.CommandHandler;
import org.btpos.dj2addons.impl.api.bewitchment.VAltarUpgrades;

public class CCraftTweaker {
	public static void loadCommandHandler() {
		CTChatCommand.registerCommand(new CommandHandler());
	}
	
	public static void postInit() {
		VAltarUpgrades.executeUpgradeRemoval();
	}
}
