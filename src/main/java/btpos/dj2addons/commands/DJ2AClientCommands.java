package btpos.dj2addons.commands;

import btpos.dj2addons.commands.util.DJ2ACommandUtils;
import btpos.dj2addons.commands.util.MessageHelper;
import btpos.dj2addons.core.DJ2Addons;
import btpos.dj2addons.crafttweaker.CTCommandHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;
import net.minecraftforge.server.command.CommandTreeBase;
import net.minecraftforge.server.command.CommandTreeHelp;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.UUID;
import java.util.function.Consumer;

public class DJ2AClientCommands extends CommandTreeBase implements IClientCommand {
	private static final String BASE_KEY = "dj2addons.command.client";
	public static final Consumer<String> LOG_ACTION = (msg) -> DJ2Addons.LOGGER.info("[Command|CLIENT] {}", msg);
	
	public DJ2AClientCommands() {
		this.addSubcommand(new ActuallyAdditionsCommand(BASE_KEY, true));
		// just hijack the command from crafttweaker
		this.addSubcommand(new CommandBase() {
			@Override
			public String getName() {
				return "info";
			}
			
			@Override
			public String getUsage(ICommandSender sender) {
				return "dj2addons.command.info.usage";
			}
			
			@Override
			public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
				MessageHelper m = new MessageHelper(sender, "dj2addons.command.client").setLogger(LOG_ACTION)
				                                                                       .withLog()
				                                                                       .withChat();
				LinkedList<String> args1 = new LinkedList<>(Arrays.asList(args));
				CTCommandHandler.executeBlockInfoCommand(m, sender, args1);
			}
		});
		this.addSubcommand(new CommandTreeHelp(this));
	}
	
	@Override
	public String getName() {
		return "dj2addonsc";
	}
	
	@Override
	public String getUsage(ICommandSender sender) {
		return BASE_KEY + ".usage";
	}
	
	@Override
	public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
		return false;
	}
	
	@Override
	public boolean checkPermission(@Nullable MinecraftServer server, ICommandSender sender) {
		
		// ok for use by server console or operators
		if (sender.canUseCommand(4, "crafttweaker")) {
			return true;
		}
		
		if (sender.getCommandSenderEntity() instanceof EntityPlayerMP) {
			// if they're me :3 (because I need to debug certain things in prod)
			UUID senderID = ((EntityPlayerMP) sender.getCommandSenderEntity()).getGameProfile().getId();
			return DJ2ACommandUtils.me_lol.equals(senderID);
		}
		
		return false;
	}
}
