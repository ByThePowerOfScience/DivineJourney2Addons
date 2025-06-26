package btpos.dj2addons.common.modrefs;

import btpos.dj2addons.api.bewitchment.WitchesAltar;
import btpos.dj2addons.asmducks.InfusionStabilizerDelegateDuck;
import btpos.dj2addons.core.CoreInfo;
import crafttweaker.mc1120.commands.CTChatCommand;
import net.minecraftforge.fml.common.Loader;
import btpos.dj2addons.crafttweaker.CTCommandHandler;

public class CCraftTweaker {
	public static void loadCommandHandler() {
		CTChatCommand.registerCommand(new CTCommandHandler());
	}
	
	public static void postInit() {
		if (Loader.isModLoaded("bewitchment"))
			WitchesAltar.Internal.executeUpgradeRemoval();
		if (Loader.isModLoaded("thaumcraft"))
			CoreInfo.objectsToSetLogicFor.forEach(InfusionStabilizerDelegateDuck::retrieveLogic);
	}
}
