package org.btpos.dj2addons.crafttweaker.extremereactors;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import epicsquid.roots.util.zen.ZenDocAppend;
import epicsquid.roots.util.zen.ZenDocArg;
import epicsquid.roots.util.zen.ZenDocClass;
import epicsquid.roots.util.zen.ZenDocMethod;
import erogenousbeef.bigreactors.api.data.ReactorInteriorData;
import erogenousbeef.bigreactors.api.registry.ReactorInterior;
import org.jetbrains.annotations.NotNull;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ModOnly("bigreactors")
@ZenClass("dj2addons.extremereactors.ReactorInterior")
@ZenDocClass(
		value = "dj2addons.extremereactors.ReactorInterior",
		description = {"Handles ExtremeReactors tweaks.", "Must be run with the DJ2Addons loader, specified with `#loader dj2addons` at the top of the ZS file."}
)
@ZenDocAppend({"docs/include/extremereactors.example.md", "docs/zs/heatconductivity.md"})

public final class CTReactorInterior {


	@ZenMethod
	@ZenDocMethod(
			order = 1,
			description = {"Registers a fluid for use in the reactor's interior as a coolant."},
			args = {
				@ZenDocArg(
					arg = "oreDict",
					info = "The OreDict tag to register as a valid interior block."
			), @ZenDocArg(
					arg = "absorption",
					info = "How much radiation this material absorbs and converts to heat. 0.0 = none, 1.0 = all."
			), @ZenDocArg(
					arg = "heatEfficiency",
					info = "How efficiently radiation is converted to heat. 0 = no heat, 1 = all heat."
			), @ZenDocArg(
					arg = "moderation",
					info = "How well this material moderates radiation. This is a divisor; should not be below 1."
			), @ZenDocArg(
					arg = "heatConductivity",
					info = "How well this material conducts heat to other blocks. Use "
			)}
	)
	public static void registerBlock(@NotNull IOreDictEntry oreDictEntry, float absorption, float heatEfficiency, float moderation, float heatConductivity) {
		ReactorInterior.registerBlock(oreDictEntry.getName(), absorption, heatEfficiency, moderation, heatConductivity);
	}

	@ZenMethod
	@ZenDocMethod(
			order = 2,
			description = {"Deregisters a previously-registered valid reactor interior block."}
	)
	public static void deregisterBlock(@NotNull IOreDictEntry oreDict) {
		ReactorInterior.deregisterBlock(oreDict.getName());
	}

	@ZenMethod
	@ZenDocMethod(
			order = 3,
			description = {"Registers a fluid for use in the reactor's interior as a coolant."},
			args = {
				@ZenDocArg(
					arg = "oreDict",
					info = "The OreDict tag to register as a valid interior block."
			), @ZenDocArg(
					arg = "absorption",
					info = "How much radiation this material absorbs and converts to heat. 0.0 = none, 1.0 = all."
			), @ZenDocArg(
					arg = "heatEfficiency",
					info = "How efficiently radiation is converted to heat. 0 = no heat, 1 = all heat."
			), @ZenDocArg(
					arg = "moderation",
					info = "How well this material moderates radiation. This is a divisor; should not be below 1."
			), @ZenDocArg(
					arg = "heatConductivity",
					info = "How well this material conducts heat to other blocks. Use `ReactorInterior.HeatConductivity`."
			)}
	)
	public static void registerFluid(@NotNull ILiquidStack fluid, float absorption, float heatEfficiency, float moderation, float heatConductivity) {
		ReactorInterior.registerFluid(fluid.getName(), absorption, heatEfficiency, moderation, heatConductivity);
	}

	@ZenMethod
	@ZenDocMethod(
			order = 4,
			description = {"Deregisters a previously-registered valid reactor interior block."}
	)
	public static void deregisterFluid(@NotNull ILiquidStack fluid) {
		ReactorInterior.deregisterFluid(fluid.getName());
	}

	@ZenMethod
	@ZenDocMethod(
			order = 5,
			description = {"Logs data in crafttweaker.log."}
	)
	public static void getBlockData(@NotNull IOreDictEntry oreDict) {
		ReactorInteriorData var10000 = ReactorInterior.getBlockData(oreDict.getName());
		CraftTweakerAPI.logInfo(var10000 != null ? var10000.toString() : null);
	}

	@ZenMethod
	@ZenDocMethod(
			order = 6,
			description = {"Logs data in crafttweaker.log."}
	)
	public static void getBlockData(@NotNull IItemStack stack) {
		ReactorInteriorData var10000 = ReactorInterior.getBlockData(CraftTweakerMC.getItemStack(stack));
		CraftTweakerAPI.logInfo(var10000 != null ? var10000.toString() : null);
	}

	@ZenMethod
	@ZenDocMethod(
			order = 7,
			description = {"Logs data in crafttweaker.log."}
	)
	public static void getFluidData(@NotNull ILiquidStack fluid) {
		ReactorInteriorData var10000 = ReactorInterior.getFluidData(fluid.getName());
		CraftTweakerAPI.logInfo(var10000 != null ? var10000.toString() : null);
	}


	@ZenRegister
	@ModOnly("extremereactors")
	@ZenClass("dj2addons.extremereactors.ReactorInterior.HeatConductivity")
	@ZenDocClass("dj2addons.extremereactors.ReactorInterior.HeatConductivity")
	public static class HeatConductivity {
		@ZenGetter
		public static float ambientHeat() {
			return 20.0F;
		}

		@ZenGetter
		public static float conductivityAir() {
			return 0.05F;
		}

		@ZenGetter
		public static float conductivityRubber() {
			return 0.01F;
		}

		@ZenGetter
		public static float conductivityWater() {
			return 0.1F;
		}

		@ZenGetter
		public static float conductivityStone() {
			return 0.15F;
		}

		@ZenGetter
		public static float conductivityGlass() {
			return 0.3F;
		}

		@ZenGetter
		public static float conductivityIron() {
			return 0.6F;
		}

		@ZenGetter
		public static float conductivityCopper() {
			return 1.0F;
		}

		@ZenGetter
		public static float conductivitySilver() {
			return 1.5F;
		}

		@ZenGetter
		public static float conductivityGold() {
			return 2.0F;
		}

		@ZenGetter
		public static float conductivityEmerald() {
			return 2.5F;
		}

		@ZenGetter
		public static float conductivityDiamond() {
			return 3.0F;
		}

		@ZenGetter
		public static float conductivityGraphene() {
			return 5.0F;
		}
	}

}
