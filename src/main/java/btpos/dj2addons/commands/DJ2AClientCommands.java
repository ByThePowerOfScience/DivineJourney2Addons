package btpos.dj2addons.commands;

import btpos.dj2addons.commands.util.DJ2ACommandUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;
import net.minecraftforge.server.command.CommandTreeBase;
import net.minecraftforge.server.command.CommandTreeHelp;

public class DJ2AClientCommands extends CommandTreeBase implements IClientCommand {
	private static final String BASE_KEY = "dj2addons.command.client";
	
	public DJ2AClientCommands() {
		this.addSubcommand(new ActuallyAdditionsCommand(BASE_KEY, true));
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
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		Entity e = sender.getCommandSenderEntity();
		
		// ok for use in singleplayer
		if (e instanceof EntityPlayerSP || server.isSinglePlayer())
			return true;
		
		// ok for use by server console or operators
		if (sender.canUseCommand(4, "")) {
			return true;
		}
		
		if (e instanceof EntityPlayerMP) {
			// if they have crafttweaker permission then sure
			// also if they're me :3
			return DJ2ACommandUtils.me_lol.equals(((EntityPlayerMP) e).getGameProfile().getId())
			       // if they have crafttweaker access then sure
			       || sender.canUseCommand(4, "crafttweaker");
		}
		
		return false;
	}
}
