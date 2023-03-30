package org.btpos.dj2addons.crafttweaker.datafixers;

import crafttweaker.annotations.ZenRegister;
import net.minecraftforge.common.util.ModFixs;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.btpos.dj2addons.util.zendoc.ZenDocArg;
import org.btpos.dj2addons.util.zendoc.ZenDocClass;
import org.btpos.dj2addons.util.zendoc.ZenDocInclude;
import org.btpos.dj2addons.util.zendoc.ZenDocMethod;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister @ZenClass("dj2addons.datafixers.DataFixerManager")
@ZenDocClass(description= {
		"Registers datafixers for your modpack.",
		"DataFixers transform save files, allowing developers to resolve problems caused by mod/pack updates.",
		"Examples include:",
		"- Transforming all of one type of block to another (including tile entity data) when changing out a mod in a modpack,",
		"- Transforming all NBT of a type of item,",
		"- Transforming existing entities into a different entity,",
		"- Transforming one item into another."
}) @ZenDocInclude(CTMigrationFixer.class)
public class CTDataFixerManager {
	private final ModFixs scriptFixer;
	
	
	private CTDataFixerManager(String packName, int fixVersion) {
		scriptFixer = FMLCommonHandler.instance().getDataFixer().init(packName, fixVersion); //TODO get good fixer version
	}
	
	@ZenMethod @ZenDocMethod(description = "Creates a new DataFixer registrar.", args={
			@ZenDocArg(value="identifier", info="The identifier of this registrar."),
			@ZenDocArg(value="fixVersion", info="The version of the world that these fixes should run on. (TODO: figure out what this means besides \"'higher = better' except sometimes it isn't\")")
	})
	public static CTDataFixerManager createManager(String identifier, int fixVersion) {
		return new CTDataFixerManager(identifier, fixVersion);
	}
	
	@ZenMethod
	@ZenDocMethod(description = "Registers a datafixer with Forge.", args=@ZenDocArg("dataFixer"))
	public void register(ICTDataFixer dataFixer) {
		dataFixer.registerSelf(scriptFixer);
	}
}
