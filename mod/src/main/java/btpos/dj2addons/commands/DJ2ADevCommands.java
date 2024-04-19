package btpos.dj2addons.commands;

import net.minecraftforge.server.command.CommandTreeHelp;

/**
 * Commands for helping with development.
 */
public class DJ2ADevCommands extends AbstractDJ2ATreeCommand {
	public DJ2ADevCommands(String parentTranslationKey, boolean isRemote) {
		super(parentTranslationKey, "dev", isRemote);
		
		this.addSubcommand(new ActuallyAdditionsCommand(baseTranslationKey, isRemote));
		this.addSubcommand(new CommandTreeHelp(this));
	}
	
}
