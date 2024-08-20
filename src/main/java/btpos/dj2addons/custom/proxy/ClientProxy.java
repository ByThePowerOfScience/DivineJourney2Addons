package btpos.dj2addons.custom.proxy;

import btpos.dj2addons.commands.DJ2AClientCommands;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.model.ModelLoader;

@SuppressWarnings("unused")
public class ClientProxy extends CommonProxy {
	@Override
	public void registerTexture(Item item, String variant) {
		super.registerTexture(item, variant);
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), variant));
	}
	
	@Override
	public void registerCommands() {
		super.registerCommands();
		ClientCommandHandler.instance.registerCommand(new DJ2AClientCommands());
	}
}
