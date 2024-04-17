package btpos.dj2addons.commands;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.server.command.CommandTreeBase;

import java.util.UUID;

@SideOnly(Side.CLIENT)
public class DJ2AClientCommands extends CommandTreeBase implements IClientCommand {
	
	private static final UUID me_lol = UUID.fromString("48a5a976-9d08-421c-b4f3-1128c8f1aeba");
	
	public DJ2AClientCommands() {
		this.addSubcommand(new AACommand());
	}
	
	@Override
	public String getName() {
		return "dj2addons";
	}
	
	@Override
	public String getUsage(ICommandSender sender) {
		return "dj2addons.command.client.usage";
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
			return me_lol.equals(((EntityPlayerMP) e).getGameProfile().getId())
			       // if they have crafttweaker access then sure
			       || sender.canUseCommand(4, "crafttweaker");
		}
		
		return false;
	}
	
	static class AACommand extends CommandTreeBase {
		
		@Override
		public String getName() {
			return "actuallyadditions";
		}
		
		@Override
		public String getUsage(ICommandSender sender) {
			return "";
		}
	}
}
