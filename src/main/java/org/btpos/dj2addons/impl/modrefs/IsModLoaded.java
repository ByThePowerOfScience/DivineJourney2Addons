package org.btpos.dj2addons.impl.modrefs;

import net.minecraftforge.fml.common.Loader;

/**
 * Stores whether a given mod is loaded or not to a variable so Forge doesn't have to filter the list each time it's called.
 * <p>This is in its own class so we don't get class ref errors if the mod isn't loaded.</p>
 */
public class IsModLoaded {
	public static final boolean modularmagic = Loader.isModLoaded("modularmagic");
	public static final boolean agricraft = Loader.isModLoaded("agricraft");
	public static final boolean aether_legacy = Loader.isModLoaded("aether_legacy");
}
