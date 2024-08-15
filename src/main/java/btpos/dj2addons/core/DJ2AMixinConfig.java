package btpos.dj2addons.core;

import btpos.dj2addons.DJ2AConfig;
import btpos.dj2addons.config.CfgOptimizations;
import btpos.dj2addons.config.CfgPatches;
import btpos.dj2addons.config.CfgTweaks;
import net.minecraftforge.fml.common.Loader;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class DJ2AMixinConfig implements IMixinConfigPlugin {
	public static final Logger LOGGER = CoreInfo.LOGGER;
	
	public static final String[] defConfigs = {
			"mixins.dj2addons.def.api.json",
			"mixins.dj2addons.def.custom.json",
			"mixins.dj2addons.def.optimizations.json",
			"mixins.dj2addons.def.patches.json",
			"mixins.dj2addons.def.tweaks.json"
	};
	
	@Override
	public void onLoad(String mixinPackage) {
		CoreInfo.onLoadCore();
		LOGGER.debug("Loading mixins from {}", mixinPackage);
	}
	
	@Override
	public String getRefMapperConfig() {
		return null;
	}
	
	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		String[] split = mixinClassName.split("\\.");
		String simplename = split[split.length - 1];
		
		switch (split[3]) {
			case "tweaks":
				return checkTweaksConfigs(simplename);
			case "patches":
				return checkPatches(simplename);
			case "optimizations":
				return checkOptimizations(simplename);
			case "initmixins":
				return checkInitMixins(simplename);
		}
		
		if (simplename.equals("MWorld")) {
			if (hasTickProfiler()) {
				LOGGER.fatal("TickProfiler is detected, but the Aerogel patch is enabled! Disable it in the config or it will cause crashes!");
				return false;
			}
		}
		return !simplename.contains("JEI") || Loader.isModLoaded("jei");
	}
	
	private boolean checkInitMixins(String name) {
		switch (name) {
			case "MItemBucket":
			case "MWorld":
				return !DJ2AConfig.disable_patches && DJ2AConfig.patches.aether_legacy.stopAerogelCrash;
			case "MStacktraceDeobfuscator":
				return !DJ2AConfig.disable_patches && DJ2AConfig.patches.vanillafix.fixStackTraceDeobfuscator;
		}
		return true;
	}
	
	private boolean checkOptimizations(String name) {
		if (DJ2AConfig.disable_optimizations)
			return false;
		
		CfgOptimizations config = DJ2AConfig.optimizations;
		
		switch (name) {
			case "MActuallyAdditions":
			case "MConnectionPair":
			case "MLaserRelayConnectionHandler":
			case "MTileEntityLaserRelay":
			case "MTileEntityLaserRelayEnergy":
			case "MTileEntityLaserRelayItem":
			case "MWorldData":
				return config.actually_additions.useOptimizedLaserNetwork;
			case "MAbilityAccessories":
			case "MInventoryAccessories":
				return config.aether_legacy.changeAccessoriesHandler;
			case "MAetherEventHandler":
				return config.aether_legacy.onBucketUsed_checkDimensionFirst;
			case "MTileEntityCrop":
				return config.agricraft.noOvertakeIfNotAggressive;
			case "MNullHelper":
				return config.enderio.optimizeNullHelper;
			case "MProf":
				return config.enderio.cacheProfilerRegex;
			case "MPlantInteractor":
				return config.industrial_foregoing.plantInteractor_takeFromCropSticksDirectly;
			case "MIOInventory":
				return config.modular_machinery.ioports_useLargerMaps;
		}
		
		return true;
	}
	
	private static boolean checkPatches(String name) {
		if (DJ2AConfig.disable_patches)
			return false;
		
		CfgPatches config = DJ2AConfig.patches;
		
		switch (name) {
			case "MAetherEventHandler":
				return config.aether_legacy.stopAerogelCrash;
			case "MEnchanter":
				return config.aether_legacy.enchanterBucketFix;
			case "MOvenRecipe":
				return config.bewitchment.witchesOven_fixJarOverflow;
			case "MPoppetHandler":
				return config.bewitchment.poppetHandler_addNullCheck;
			case "MSoulForge":
				return config.blood_magic.hellfireForge_preventInputtingIntoOutputSlot;
			case "MTileEntityMultiblockPart":
			case "MTileEntityMultiblockPartSubclasses":
				return config.immersive_engineering.client_stopMultiblockCrash;
			case "MRequirementLifeEssence":
				return config.modular_magic.fixBloodOrbCrash;
			case "MInputs":
				return config.moretweaker.useCraftTweakerRecipeApi;
			case "MMiscUtil":
				return config.packagedauto.forceCheckNBT;
			case "MNBTMatchingRecipe":
				return config.rftools.useNbtIngredients;
		}
		
		return true;
	}
	
	private static boolean checkTweaksConfigs(String name) {
		if (DJ2AConfig.disable_tweaks)
			return false;
		
		final CfgTweaks config = DJ2AConfig.tweaks;
		switch (name) {
			case "MBlockMechanicalUser":
				return config.extraUtils2.mechanicalUser.enableComparatorOutput;
			case "MTileUse":
				return config.extraUtils2.mechanicalUser.usePlacersThaumcraftResearch;
			case "MArcanePedestal":
				return config.thaumcraft.runicMatrix_enableStackOutput;
			case "MTileEssentiaOutput":
				return config.thaumcraft.transfuser_noDrainModularMagic;
		}
		return true;
	}
	
	private boolean hasTickProfiler() {
		try {
			// TickCentral always loads before Mixin, so this should be a reliable method to knowing if it exists.
			Class.forName("org.minimallycorrect.tickprofiler.minecraft.CoreMod");
			return true;
		} catch (ClassNotFoundException ignore) {
			return false;
		}
	}
	
	
	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}
	
	@Override
	public List<String> getMixins() {
		return null;
	}
	
	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
		//		LOGGER.debug("Attempting to apply mixin {} to target class {}", mixinClassName, targetClassName);
		
	}
	
	/**
	 * Add messages when a patch is applied. :D
	 */
	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
		String[] splitName = mixinClassName.split("\\.");
		String output;
		switch (splitName[splitName.length - 1]) {
			case "MItemBucket":
				output = "Sprinkling aerogel dust in lava buckets.";
				break;
			case "MEnchanter":
				output = "Disabling Skyroot bucket mitosis.";
				break;
			case "MTileCelestialCrystals":
				output = "De-ironing starmetal/crystal interactions.";
				break;
			case "MModRecipes":
				output = "This server is WAC-secured. [Witch Anti-Cheat]";
				break;
			case "MSoulForge":
				output = "Giving demons a pep rally.";
				break;
			case "MPowerSystem":
				output = "Embiggening reactor buffer.";
				break;
			case "MPlantInteractor":
				output = "Enabling interdimensional access of Crop Things™.";
				break;
			case "MOverlayHandler":
				output = "Teaching hunger shanks to do The Wave.";
				break;
			case "MInputs":
				output = "Making Bewitchment say \"It's Florbin' Time.\" and florb all over the place.";
				break;
			case "MTileEssentiaOutput":
				output = "Sealing those pesky leaks in the modular magical machineries.";
				break;
			case "MModContent":
				output = "Bass-boosting totemic instruments.";
				break;
			case "MArcanePedestal":
				output = "Making arcane pedestals bigger (on the inside).";
				break;
			case "MModBrews":
				output = "Turning rivers into bacon.";
				break;
			case "MActuallyAdditions":
				output = "Graphifying laser relays. (That sounds so COOL!)";
				break;
			// 			case "":
			//				output = "";
			//				break;
			default:
				return;
		}
		LOGGER.info(output);
	}
}
