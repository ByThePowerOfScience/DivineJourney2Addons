package btpos.dj2addons.crafttweaker.bloodmagic;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import btpos.dj2addons.common.util.zendoc.ZenDocArg;
import btpos.dj2addons.common.util.zendoc.ZenDocClass;
import btpos.dj2addons.common.util.zendoc.ZenDocMethod;
import btpos.dj2addons.api.bloodmagic.HellfireForge;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;


@ZenRegister @ModOnly("bloodmagic")
@ZenClass("dj2addons.bloodmagic.HellfireForge") @ZenDocClass("dj2addons.bloodmagic.HellfireForge")
public class CTSoulForge {
	/**
	 * Sets the speed for all Hellfire Forge crafts. Default is 100.
	 *
	 * @param ticksRequired Number of ticks taken to craft any given item.
	 */
	@ZenMethod @ZenDocMethod(order=1, args = {
			@ZenDocArg(value ="ticksRequired", info="Number of ticks taken to craft any given item.")
	}, description = "Sets the crafting speed for all Hellfire Forge crafts. Default is 100.")
	public static void setCraftingTicksRequired(int ticksRequired) {
		HellfireForge.setTicksRequired(ticksRequired);
	}
	
	/**
	 * Sets how many souls are transferred from the given chunk to the table's soul gem per tick. Default is 1.
	 *
	 * @param transferRate Souls per tick.
	 */
	@ZenMethod @ZenDocMethod(order=2, args = {
			@ZenDocArg(value ="transferRate", info="Souls per tick.")
	}, description = "Sets how many souls are transferred from the given chunk to the table's soul gem per tick. Default is 1.")
	public static void setWorldWillTransferRate(double transferRate) {
		HellfireForge.setWorldWillTransferRate(transferRate);
	}
	
	/**
	 * Allows any Will type to be used in crafting in place of Raw. Default is false.
	 *
	 * @param enabled True to enable.
	 */
	@ZenMethod @ZenDocMethod(order=3, args={
			@ZenDocArg(value ="enabled", info="True to enable.")
	}, description = "Allows any Will type to be used in crafting in place of Raw. Default is false.")
	public static void setCraftWithAllWillTypes(boolean enabled) {
		HellfireForge.setCraftWithAllWillTypes(enabled);
	}
}
