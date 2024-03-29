package btpos.dj2addons.common.modrefs;

import net.minecraftforge.fml.common.Loader;

/**
 * Stores whether a given mod is loaded or not to a variable so Forge doesn't have to filter the list each time it's called.
 */
public class IsModLoaded {
	public static final boolean modularmagic = Loader.isModLoaded("modularmagic");
	public static final boolean agricraft = Loader.isModLoaded("agricraft");
	public static final boolean aether_legacy = Loader.isModLoaded("aether_legacy");
	public static final boolean thaumcraft = Loader.isModLoaded("thaumcraft");
	public static final boolean bigreactors = Loader.isModLoaded("bigreactors");
	public static final boolean crafttweaker = Loader.isModLoaded("crafttweaker");
}
