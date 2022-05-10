package org.btpos.dj2addons.crafttweaker.bloodmagic;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import epicsquid.roots.util.zen.ZenDocAppend;
import epicsquid.roots.util.zen.ZenDocArg;
import epicsquid.roots.util.zen.ZenDocClass;
import epicsquid.roots.util.zen.ZenDocMethod;
import org.btpos.dj2addons.DJ2Addons;
import org.btpos.dj2addons.impl.bloodmagic.VSoulForge;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;


@ZenRegister @ModOnly("bloodmagic")
@ZenClass(DJ2Addons.MOD_ID + ".bloodmagic.HellfireForge") @ZenDocClass(DJ2Addons.MOD_ID + ".bloodmagic.HellfireForge")
public class ZenSoulForge {
	/**
	 * Sets the speed for all Hellfire Forge crafts. Default is 100.
	 *
	 * @param ticksRequired Number of ticks taken to craft any given item.
	 */
	@ZenMethod @ZenDocMethod(order=1, args = {
			@ZenDocArg(arg="ticksRequired", info="Number of ticks taken to craft any given item.")
	}, description = "Sets the crafting speed for all Hellfire Forge crafts. Default is 100.")
	public static void setCraftingTicksRequired(int ticksRequired) {
		VSoulForge.setTicksRequired(ticksRequired);
	}
	
	/**
	 * Sets how many souls are transferred from the given chunk to the table's soul gem per tick. Default is 1.
	 *
	 * @param transferRate Souls per tick.
	 */
	@ZenMethod @ZenDocMethod(order=2, args = {
			@ZenDocArg(arg="transferRate", info="Souls per tick.")
	}, description = "Sets how many souls are transferred from the given chunk to the table's soul gem per tick. Default is 1.")
	public static void setWorldWillTransferRate(double transferRate) {
		VSoulForge.setWorldWillTransferRate(transferRate);
	}
	
	/**
	 * Allows any Will type to be used in crafting in place of Raw. Default is false.
	 *
	 * @param enabled True to enable.
	 */
	@ZenMethod @ZenDocMethod(order=3, args={
			@ZenDocArg(arg="enabled", info="True to enable.")
	}, description = "Allows any Will type to be used in crafting in place of Raw. Default is false.")
	public static void setCraftWithAllWillTypes(boolean enabled) {
		VSoulForge.setCraftWithAllWillTypes(enabled);
	}
}
