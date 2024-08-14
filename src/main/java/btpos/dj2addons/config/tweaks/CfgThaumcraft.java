package btpos.dj2addons.config.tweaks;

import net.minecraftforge.common.config.Config.Comment;

public class CfgThaumcraft {
	@Comment("Allows Runic Matrix recipe outputs to have stack sizes greater than 1.")
	public boolean runicMatrix_enableStackOutput = true;
	
	@Comment({"Prevent Emptying Essentia Transfusers from draining Modular Magic's 'Aspect Provider' blocks."})
	public boolean transfuser_noDrainModularMagic = true;
}
