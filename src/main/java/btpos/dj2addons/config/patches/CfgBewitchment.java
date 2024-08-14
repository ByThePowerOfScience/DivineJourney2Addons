package btpos.dj2addons.config.patches;

import net.minecraftforge.common.config.Config.Comment;

public class CfgBewitchment {
	@Comment("Stops excess jars from being voided if the output is full.")
	public boolean witchesOven_fixJarOverflow = true;
	
	@Comment("Stops crash when crafting a destructible item in an autocrafter.")
	public boolean poppetHandler_addNullCheck = true;
}
