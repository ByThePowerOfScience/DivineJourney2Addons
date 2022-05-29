package org.btpos.dj2addons.crafttweaker;

import com.google.common.base.Enums;
import com.google.common.base.Optional;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.btpos.dj2addons.DJ2Addons;
import org.btpos.dj2addons.impl.bewitchment.VModRecipes;
import pokefenn.totemic.api.music.MusicInstrument;

public class CommandHandler extends CraftTweakerCommand {
	
	public CommandHandler() {
		super(DJ2Addons.MOD_ID);
	}
	
	@Override
	protected void init() {
		setDescription(new TextComponentTranslation("dj2addons.commands.desc"));
	}
	
	private enum SubCommand {
		all, bewitchment, totemic
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
					sender.sendMessage(new TextComponentTranslation("dj2addons.commands.header.bewitchment.rituals"));
					VModRecipes.getAllRituals()
							   .forEach(r -> sender.sendMessage(new TextComponentString("  -" + r.getRegistryName())));
					if (command.get().equals(SubCommand.bewitchment))
						break;
				case totemic:
					sender.sendMessage(new TextComponentTranslation("dj2addons.commands.header.totemic.instruments"));
					GameRegistry.findRegistry(MusicInstrument.class).forEach(i -> sender.sendMessage(new TextComponentString("  -" + i.getRegistryName())));
					if (command.get().equals(SubCommand.totemic))
						break;
			}
		} else {
			sender.sendMessage(new TextComponentTranslation("dj2addons.commands.usage"));
		}
		
	}
}
