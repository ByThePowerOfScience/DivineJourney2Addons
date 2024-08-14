package btpos.dj2addons.config.patches;

import net.minecraftforge.common.config.Config.Comment;

public class CfgVanillaFix {
	@Comment("Fixes the thing that turns the error logs from \"m_12345_\" to \"setInventory\" from crashing the server.")
	public boolean fixStackTraceDeobfuscator = true;
}
