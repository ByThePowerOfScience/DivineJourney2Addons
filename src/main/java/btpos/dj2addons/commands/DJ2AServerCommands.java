package btpos.dj2addons.commands;

import btpos.dj2addons.DJ2Addons;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;

import java.util.function.Consumer;

public class DJ2AServerCommands extends CommandTreeBase {
	public static final Consumer<String> LOG_ACTION = (msg) -> DJ2Addons.LOGGER.info("[Command|SERVER] {}", msg);
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
