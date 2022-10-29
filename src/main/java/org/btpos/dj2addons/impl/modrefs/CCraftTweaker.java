package org.btpos.dj2addons.impl.modrefs;

import com.gildedgames.the_aether.blocks.BlocksAether;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.formatting.IFormatter;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.tooltip.IngredientTooltips;
import crafttweaker.mc1120.formatting.FormattedString;

/**
 * Stores references to CraftTweaker so they are only loaded after the mod is loaded.
 */
public class CCraftTweaker {
	public static boolean shouldWriteAerogelTooltip = false;
	/**
	 * Called by {@link org.btpos.dj2addons.mixin.DJ2AMixinConfig#shouldApplyMixin DJ2AMixinConfig.shouldApplyMixin} if TickCentral is detected.
	 */
	public static void onDisableAerogelPatch() {
		shouldWriteAerogelTooltip = true;
	}
	
	/**
	 * Adds warnings to Aerogel block description. Does it in the exact same way as CraftTweaker would from a script.
	 * <p>Called by {@link org.btpos.dj2addons.DJ2Addons#preinit DJ2Addons.preinit}</p>
	 */
	public static void writeAerogelTooltip() {
		IIngredient aerogel = CraftTweakerMC.getIIngredient(BlocksAether.aerogel);
		final IFormatter formatter = CraftTweakerAPI.format;
		IngredientTooltips.addTooltip(aerogel, formatter.bold(formatter.red(new FormattedString("Due to a bug with Aether Legacy, attempted automation of Aerogel creation will cause crashes."))));
		IngredientTooltips.addTooltip(aerogel, formatter.bold(formatter.red(new FormattedString("It is highly recommended to wait until Mystical Agriculture to automate this."))));
	}
	
}
