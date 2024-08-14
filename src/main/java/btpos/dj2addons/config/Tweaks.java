package btpos.dj2addons.config;

import btpos.dj2addons.config.tweaks.ExtraUtils2;
import btpos.dj2addons.config.tweaks.Thaumcraft;
import net.minecraftforge.common.config.Config.Name;

public final class Tweaks {
	@Name("Extra Utilities 2")
	public ExtraUtils2 extraUtils2 = new ExtraUtils2();
	
	@Name("Thaumcraft")
	public Thaumcraft thaumcraft = new Thaumcraft();
}
