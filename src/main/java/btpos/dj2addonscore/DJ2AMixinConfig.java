package btpos.dj2addonscore;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Loader;
import org.apache.logging.log4j.Logger;
import btpos.dj2addonscore.common.CoreInfo;
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
			"mixins.dj2addons.def.patches.json",
			"mixins.dj2addons.def.tweaks.json"
	};
	public static final String[] initConfigs = {
			"mixins.dj2addons.bootstrap.json",
			"mixins.dj2addons.init.custom.json",
			"mixins.dj2addons.init.patches.json"
	};
	
	@Override
	public void onLoad(String mixinPackage) {
		LOGGER.debug("Loading mixins from " + mixinPackage);
	}
	
	@Override
	public String getRefMapperConfig() {
		return Launch.blackboard.get("fml.deobfuscatedEnvironment") == Boolean.TRUE ? null : "mixins.dj2addons.refmap.json";
//		return "mixins.dj2addons.refmap.json";
	}
	
	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		String[] split = mixinClassName.split("\\.");
		String simplename = split[split.length - 1];
		if (simplename.equals("MWorld")) {
			if (hasTickProfiler()) {
				LOGGER.info("TickProfiler detected! Disabling Aerogel patch.");
				CoreInfo.onDisableAerogelPatch();
				return false;
			}
		}
		return !simplename.contains("JEI") || Loader.isModLoaded("jei");
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
				output = "It's Florbin' Time.";
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
// 			case "":
//				output = "";
//				break;
			default:
				return;
		}
		LOGGER.info(output);
		
	}
}
