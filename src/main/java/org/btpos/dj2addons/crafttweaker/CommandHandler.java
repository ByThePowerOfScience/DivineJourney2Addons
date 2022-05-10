package org.btpos.dj2addons.crafttweaker;

import com.google.common.base.Enums;
import com.google.common.base.Optional;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import org.btpos.dj2addons.DJ2Addons;
import org.btpos.dj2addons.impl.bewitchment.VModRecipes;

public class CommandHandler extends CraftTweakerCommand {
	
	public CommandHandler() {
		super(DJ2Addons.MOD_ID);
	}
	
	@Override
	protected void init() {
		setDescription(new TextComponentTranslation("dj2addons.commands.desc"));
	}
	
	private enum SubCommand {
		all, bewitchment
	}
	
	@Override
	public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
		if (args.length < 1) {
			sender.sendMessage(new TextComponentTranslation("dj2addons.commands.usage"));
			return;
		}
		
		Optional<SubCommand> command = Enums.getIfPresent(SubCommand.class, args[0]);
		if (command.isPresent()) {
			switch (command.get()) {
				case all:
				case bewitchment:
					CraftTweakerAPI.getLogger().logInfo("Bewitchment ritual IDs:");
					VModRecipes.getAllRituals().forEach(r -> {
						CraftTweakerAPI.getLogger().logInfo("  -" + r.getRegistryName());
					});
					if (command.get().equals(SubCommand.bewitchment))
						break;
			}
		}
		
	}
}
