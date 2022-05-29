package org.btpos.dj2addons.mixin;

import net.minecraft.launchwrapper.Launch;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

import static org.btpos.dj2addons.DJ2Addons.LOGGER;

public class DJ2AMixinConfig implements IMixinConfigPlugin {
	@Override
	public void onLoad(String mixinPackage) {
		LOGGER.info("Loading mixins from " + mixinPackage);
	}
	
	@Override
	public String getRefMapperConfig() {
		return Launch.blackboard.get("fml.deobfuscatedEnvironment") == Boolean.TRUE ? null : "mixins.dj2addons.refmap.json";
	}
	
	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
//		try {
//			Class<?> clz = Class.forName(targetClassName);
//			 //Make sure class is loaded
//			logger.info("Should initialize: {}", clz.getName());
//			return true;
//		} catch (ClassNotFoundException e) {
//			logger.debug("Skipped {}", mixinClassName);
//			return false;
//		}
		return true;
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
//		LOGGER.debug("Attempting to apply mixin {} to target class {}", mixinClassName, targetClassName);
	}
	
	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}
