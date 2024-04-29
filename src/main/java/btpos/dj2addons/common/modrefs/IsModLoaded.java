package btpos.dj2addons.common.modrefs;

import net.minecraftforge.fml.common.Loader;

/**
 * Stores whether a given mod is loaded or not to a variable so Forge doesn't have to filter the list each time it's called.
 */
public class IsModLoaded {
	public static boolean modularmagic = Loader.isModLoaded("modularmagic");
	public static boolean agricraft = Loader.isModLoaded("agricraft");
	public static boolean aether_legacy = Loader.isModLoaded("aether_legacy");
	public static boolean thaumcraft = Loader.isModLoaded("thaumcraft");
	public static boolean bigreactors = Loader.isModLoaded("bigreactors");
	public static boolean crafttweaker = Loader.isModLoaded("crafttweaker");
	
	public static void update() {
		modularmagic = Loader.isModLoaded("modularmagic");
		agricraft = Loader.isModLoaded("agricraft");
		aether_legacy = Loader.isModLoaded("aether_legacy");
		thaumcraft = Loader.isModLoaded("thaumcraft");
		bigreactors = Loader.isModLoaded("bigreactors");
		crafttweaker = Loader.isModLoaded("crafttweaker");
	}
}
