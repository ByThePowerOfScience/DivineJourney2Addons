package btpos.dj2addons.commands;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;

/**
 * Commands for helping with development.
 */
public class DJ2ADevCommands extends CommandTreeBase {
	@Override
	public String getName() {
		return "dev";
	}
	
	@Override
	public String getUsage(ICommandSender sender) {
		return "";
	}
}
