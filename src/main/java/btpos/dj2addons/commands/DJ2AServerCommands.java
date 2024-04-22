package btpos.dj2addons.commands;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;

public class DJ2AServerCommands extends CommandTreeBase {
	private final String BASE_KEY = "dj2addons.command";
	
	public DJ2AServerCommands() {
		this.addSubcommand(new ActuallyAdditionsCommand(BASE_KEY, false));
	}
	
	@Override
	public String getName() {
		return "btpos/dj2addons";
	}
	
	@Override
	public String getUsage(ICommandSender sender) {
		return BASE_KEY + ".usage";
	}
}
