package org.btpos.dj2addons.bootstrapper.mixin;

import net.minecraft.launchwrapper.Launch;
import org.apache.logging.log4j.Level;
import org.btpos.dj2addons.bootstrapper.core.DJ2ALoadingPlugin;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.transformer.meta.MixinProxy;

import java.util.List;
import java.util.Set;

public class DJ2AMixinConfig implements IMixinConfigPlugin {
	@Override
	public void onLoad(String mixinPackage) {
		
	}
	
	@Override
	public String getRefMapperConfig() {
		return Launch.blackboard.get("fml.deobfuscatedEnvironment") == Boolean.TRUE ? null : "mixins.dj2addons.refmap.json";
	}
	
	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
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
		
	}
	
	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
		DJ2ALoadingPlugin.logger.log(Level.INFO, "Applied " + mixinClassName + " from " + mixinInfo.getConfig().getMixinPackage() + " to " + targetClassName);
	}
}
