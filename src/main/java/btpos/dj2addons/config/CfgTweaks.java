package btpos.dj2addons.config;

import btpos.dj2addons.config.tweaks.CfgExtraUtils2;
import btpos.dj2addons.config.tweaks.CfgThaumcraft;
import net.minecraftforge.common.config.Config.Name;

public final class CfgTweaks {
	@Name("Extra Utilities 2")
	public CfgExtraUtils2 extraUtils2 = new CfgExtraUtils2();
	
	@Name("Thaumcraft")
	public CfgThaumcraft thaumcraft = new CfgThaumcraft();
}
