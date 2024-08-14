package btpos.dj2addons.config.patches;

import net.minecraftforge.common.config.Config.Comment;

public class CfgPackagedAuto {
	@Comment("Makes PackagedAuto give outputs with the correct NBT.")
	public boolean forceCheckNBT = true;
}
