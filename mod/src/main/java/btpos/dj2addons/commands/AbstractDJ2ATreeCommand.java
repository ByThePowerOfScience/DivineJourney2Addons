package btpos.dj2addons.commands;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;

public abstract class AbstractDJ2ATreeCommand extends CommandTreeBase {
	protected final String baseTranslationKey;
	protected final String name;
	protected final boolean isRemote;
	
	public AbstractDJ2ATreeCommand(String parentTranslationKey, String name, boolean isRemote) {
		this.name = name;
		this.baseTranslationKey = (parentTranslationKey + name).intern();
		this.isRemote = isRemote;
	}
	
	@Override
	public String getUsage(ICommandSender sender) {
		return baseTranslationKey + ".usage";
	}
	
	@Override
	public String getName() {
		return name;
	}
}
