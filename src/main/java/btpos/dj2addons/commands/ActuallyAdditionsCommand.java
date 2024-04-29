package btpos.dj2addons.commands;

import btpos.dj2addons.commands.util.MessageHelper;
import btpos.dj2addons.optimizations.impl.actuallyadditions.OptimizedLaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.mod.data.WorldData;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.command.CommandTreeHelp;

public class ActuallyAdditionsCommand extends AbstractDJ2ATreeCommand {
	
	public ActuallyAdditionsCommand(String parentTranslationKey, boolean isRemote) {
		super(parentTranslationKey, "actuallyadditions", isRemote);
		this.addSubcommand(new ListNetworksCommand(baseTranslationKey, isRemote));
//		this.addSubcommand(new ClearNetworksCommand(baseTranslationKey));
		this.addSubcommand(new CommandTreeHelp(this));
	}
	
	static class ListNetworksCommand extends AbstractDJ2ATreeCommand {
		public ListNetworksCommand(String parentKey, boolean isRemote) {
			super(parentKey, "listnetworks", isRemote);
		}
		
		@Override
		public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
			MessageHelper m = new MessageHelper(sender, baseTranslationKey).withChat()
			                                                               .setLogger(isRemote ? DJ2AClientCommands.LOG_ACTION : DJ2AServerCommands.LOG_ACTION)
			                                                               .withLog();
			
			
			m.sendHeading("Printing AA networks:");
			m.sendHeading("From map:");
			if (!(ActuallyAdditionsAPI.connectionHandler instanceof OptimizedLaserRelayConnectionHandler)) {
				m.sendError("Map's handler isn't a DJ2A handler!");
			} else {
				StringBuilder sb = new StringBuilder();
				((OptimizedLaserRelayConnectionHandler) ActuallyAdditionsAPI.connectionHandler).networkLookupMap.values()
				                                                                                                .stream()
				                                                                                                .distinct()
				                                                                                                .forEach(n -> sb.append("\t")
				                                                                                                                .append(n)
				                                                                                                                .append("\n"));
				m.send(sb.toString());
			}
			m.sendHeading("From world " + sender.getEntityWorld() + ":");
			StringBuilder sb2 = new StringBuilder();
			WorldData.get(isRemote ? sender.getEntityWorld() : server.getWorld(sender.getEntityWorld().provider.getDimension())).laserRelayNetworks.forEach(n -> sb2.append("\t")
			                                                                          .append(n)
			                                                                          .append("\n"));
			m.send(sb2.toString());
		}
	}
	
}
