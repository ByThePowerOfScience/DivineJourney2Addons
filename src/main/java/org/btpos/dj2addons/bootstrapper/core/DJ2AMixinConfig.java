package org.btpos.dj2addons.bootstrapper.core;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Loader;
import org.apache.logging.log4j.Level;
import org.btpos.dj2addons.bootstrapper.core.DJ2ALoadingPlugin;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.transformer.meta.MixinProxy;
import static org.btpos.dj2addons.bootstrapper.core.DJ2ALoadingPlugin.logger;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class DJ2AMixinConfig implements IMixinConfigPlugin {
	@Override
	public void onLoad(String mixinPackage) {
		logger.info("Loading mixins from " + mixinPackage);
	}
	
	@Override
	public String getRefMapperConfig() {
		return Launch.blackboard.get("fml.deobfuscatedEnvironment") == Boolean.TRUE ? null : "mixins.dj2addons.refmap.json";
	}
	
	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		try {
			Class<?> clz = Class.forName(targetClassName);
			 //Make sure class is loaded
			logger.info("Should initialize: {}", clz.getName());
			return true;
		} catch (ClassNotFoundException e) {
			logger.debug("Skipped {}", mixinClassName);
			return false;
		}
	}
	
	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
		
	}
	
	@Override
	public List<String> getMixins() {
		return null;
	}
	
	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
		logger.debug("Attempting to apply mixin {} to target class {}", mixinClassName, targetClassName);
	}
	
	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
		DJ2ALoadingPlugin.logger.info( "Applied " + mixinClassName + " to " + targetClassName);
	}
}
