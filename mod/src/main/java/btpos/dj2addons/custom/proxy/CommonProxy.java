package btpos.dj2addons.custom.proxy;

import btpos.dj2addons.common.modrefs.CCraftTweaker;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Loader;

public class CommonProxy {
	public void registerTexture(Item item, String variant) {}
	
	public void registerCommands() {
		if (Loader.isModLoaded("crafttweaker"))
			CCraftTweaker.loadCommandHandler();
	}
}
