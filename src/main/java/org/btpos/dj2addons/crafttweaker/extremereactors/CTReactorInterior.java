package org.btpos.dj2addons.crafttweaker.extremereactors;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenDoc;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.oredict.IOreDictEntry;
import epicsquid.roots.util.zen.*;
import erogenousbeef.bigreactors.api.registry.ReactorInterior;
import org.jetbrains.annotations.NotNull;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

@ZenRegister @ModOnly("bigreactors")
@ZenClass("dj2addons.extremereactors.ReactorInterior")
@ZenDocClass(
		value = "dj2addons.extremereactors.ReactorInterior",
		description = {"Exposes API for interior blocks/fluids."}
)
@ZenDocAppend({"docs/include/extremereactors.reactorinterior.example.md", "docs/zs/heatconductivity.md"})
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
					info = "How well this material conducts heat to other blocks. Use `ReactorInterior.HeatConductivity`."
			)}
	) @ZenDoc("Registers a fluid for use in the reactor's interior as a coolant.")
	public static void registerBlock(@NotNull IOreDictEntry oreDictEntry, float absorption, float heatEfficiency, float moderation, float heatConductivity) {
		ReactorInterior.registerBlock(oreDictEntry.getName(), absorption, heatEfficiency, moderation, heatConductivity);
	}
	
	@ZenMethod
	@ZenDocMethod(
			order = 2,
			description = {"Deregisters a previously-registered valid reactor interior block."},
			args = @ZenDocArg(arg="oreDict", info="The oredict entry to remove.")
	) @ZenDoc("Registers an oredict block for use in the reactor's interior as a coolant.")
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
	) @ZenDoc("Registers a fluid for use in the reactor's interior as a coolant.")
	public static void registerFluid(@NotNull ILiquidStack fluid, float absorption, float heatEfficiency, float moderation, float heatConductivity) {
		ReactorInterior.registerFluid(fluid.getName(), absorption, heatEfficiency, moderation, heatConductivity);
	}
	
	@ZenMethod
	@ZenDocMethod(
			order = 4,
			description = {"Deregisters a previously-valid coolant fluid."},
			args = @ZenDocArg(arg="fluid", info="The fluid to deregister.")
	) @ZenDoc("Deregisters a previously-valid coolant fluid.")
	public static void deregisterFluid(@NotNull ILiquidStack fluid) {
		ReactorInterior.deregisterFluid(fluid.getName());
	}
	
	
	
	
	@ZenRegister
	@ModOnly("bigreactors")
	@ZenClass("dj2addons.extremereactors.HeatConductivity")
	@ZenDocClass("dj2addons.extremereactors.HeatConductivity")
	public static class HeatConductivity {
		@ZenProperty @ZenDocProperty
		public static final float ambientHeat = 20.0F;
		
		@ZenProperty @ZenDocProperty
		public static final float air = 0.05F;
		
		@ZenProperty @ZenDocProperty
		public static final float rubber = 0.01F;
		
		@ZenProperty @ZenDocProperty
		public static final float water = 0.1F;
		
		@ZenProperty @ZenDocProperty
		public static final float stone = 0.15F;
		
		@ZenProperty @ZenDocProperty
		public static final float glass = 0.3F;
		
		@ZenProperty @ZenDocProperty
		public static final float iron = 0.6F;
		
		@ZenProperty @ZenDocProperty
		public static final float copper = 1.0F;
		
		@ZenProperty @ZenDocProperty
		public static final float silver = 1.5F;
		
		@ZenProperty @ZenDocProperty
		public static final float gold = 2.0F;
		
		@ZenProperty @ZenDocProperty
		public static final float emerald = 2.5F;
		
		@ZenProperty @ZenDocProperty
		public static final float diamond = 3.0F;
		
		@ZenProperty @ZenDocProperty
		public static final float graphene = 5.0F;
	}

}
