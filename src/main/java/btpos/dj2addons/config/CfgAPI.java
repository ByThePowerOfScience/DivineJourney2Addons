package btpos.dj2addons.config;

import btpos.dj2addons.core.DJ2Addons;
import com.cleanroommc.configanytime.ConfigAnytime;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RequiresMcRestart;

@Config(modid = DJ2Addons.MOD_ID, name = "dj2addons/features", category = "")
public class CfgAPI {
	public static Thaumcraft thaumcraft = new Thaumcraft();
	
	public static final class Thaumcraft {
		@Name("Infusion Stabilizers: Class Names")
		@Comment({"This is a two-part config due to implementation restrictions.",
		          "(The only way to add stabilizers is to literally change the code itself, and that happens LONG before block names are registered.)",
		          "",
		          "This is a list of the CLASS NAMES of the blocks that you want to be turned into infusion stabilizers.",
		          "Use `/ct dj2addons info classes` in-game while looking at a block to get its class name.",
		          "(e.g. thaumcraft.common.blocks.basic.BlockCandle)",
		          "",
		          "After putting the class names here, use either the CraftTweaker (dj2addons.thaumcraft.InfusionStabilizers) or Java (btpos.dj2addons.api.thaumcraft.InfusionStabilizers) APIs to add the logic."
		})
		@RequiresMcRestart
		public String[] infusionStabilizers = new String[0];
	}
	
	static {
		ConfigAnytime.register(CfgAPI.class);
	}
}
