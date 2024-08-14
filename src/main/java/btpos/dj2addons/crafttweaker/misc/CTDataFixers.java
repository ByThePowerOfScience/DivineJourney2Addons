package btpos.dj2addons.crafttweaker.misc;

import btpos.dj2addons.common.util.zendoc.ZenDocClass;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("dj2addons.unstable.DataFixers")
@ZenDocClass(value="dj2addons.unstable.DataFixers",
             description="Use the same magic that lets old world saves upgrade to newer Minecraft versions. Swap out entire mods or single blocks, reformat NBT, change tile entities, or other world changes. Use with caution." +
		             "\nWARNING:" +
		             "\n\t 1. Data fixers must remain in the file FOREVER to ensure they apply to all users migrating to future updates." +
		             "\n\t 2. Changes to old data fixers will NOT be applied to worlds that have already been patched, so make sure you get it right the first time!")
public class CTDataFixers {
	//TODO
}
