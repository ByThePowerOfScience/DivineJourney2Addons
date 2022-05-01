package org.btpos.dj2addons.crafttweaker.bloodmagic;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import org.btpos.dj2addons.impl.bloodmagic.SoulForgeValues;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;


@ZenClass("mods.dj2addons.bloodmagic.HellfireForge")
@ZenRegister @ModOnly("bloodmagic")
public class ZenSoulForge {
	/**
	 * Sets the speed for all Hellfire Forge crafts. Default is 100.
	 *
	 * @param ticksRequired Number of ticks taken to craft any given item.
	 */
	@ZenMethod
	public static void setCraftingTicksRequired(int ticksRequired) {
		SoulForgeValues.setTicksRequired(ticksRequired);
	}
	
	/**
	 * Sets how many souls are transferred from the given chunk to the table's soul gem per tick. Default is 1.
	 *
	 * @param transferRate Souls per tick.
	 */
	@ZenMethod
	public static void setWorldWillTransferRate(double transferRate) {
		SoulForgeValues.setWorldWillTransferRate(transferRate);
	}
	
	/**
	 * Allows any Will type to be used in crafting in place of Raw. Default is false.
	 *
	 * @param enabled True to enable.
	 */
	@ZenMethod
	public static void setCraftWithAllWillTypes(boolean enabled) {
		SoulForgeValues.setCraftWithAllWillTypes(enabled);
	}
}
