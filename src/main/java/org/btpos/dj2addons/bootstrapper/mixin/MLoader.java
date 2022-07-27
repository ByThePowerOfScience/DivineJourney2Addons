package org.btpos.dj2addons.bootstrapper.mixin;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModClassLoader;
import net.minecraftforge.fml.common.ModContainer;
import org.btpos.dj2addons.bootstrapper.core.DJ2AddonsCore;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.transformer.Proxy;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.List;



/**
 * This code original licensed under the terms of the MIT License
 * <p>Authorship of the file is visible here: https://github.com/DimensionalDevelopment/JustEnoughIDs/commits/master/src/main/java/org/dimdev/jeid/mixin/init/MixinLoader.java
 **/
@Mixin(Loader.class)
public abstract class MLoader {
	@Shadow
	private List<ModContainer> mods;
	@Shadow
	private ModClassLoader modClassLoader;
	
	/**
	 * @reason Load all mods now and load mod support mixin configs. This can't be done later
	 * since constructing mods loads classes from them.
	 */
	@SuppressWarnings("ALL")
	@Inject(method = "loadMods", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/common/LoadController;transition(Lnet/minecraftforge/fml/common/LoaderState;Z)V", ordinal = 1), remap = false)
	private void beforeConstructingMods(List<String> injectedModContainers, CallbackInfo ci) {
		// Add all mods to class loader
		for (ModContainer mod : mods) {
			try {
				modClassLoader.addFile(mod.getSource());
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
		}
		
		// Add and reload mixin configs
		Mixins.addConfiguration("mixins.dj2addons.json");
		
		Proxy mixinProxy = (Proxy) Launch.classLoader.getTransformers().stream().filter(transformer -> transformer instanceof Proxy).findFirst().get();
		try {
			//This will very likely break on the next major mixin release.
			Class mixinTransformerClass = Class.forName("org.spongepowered.asm.mixin.transformer.MixinTransformer");
			
			Field transformerField = Proxy.class.getDeclaredField("transformer");
			transformerField.setAccessible(true);
			Object transformer = transformerField.get(mixinProxy);
			
			//Get MixinProcessor from MixinTransformer
			Field processorField = mixinTransformerClass.getDeclaredField("processor");
			processorField.setAccessible(true);
			Object processor = processorField.get(transformer);
			
			Class mixinProcessorClass = Class.forName("org.spongepowered.asm.mixin.transformer.MixinProcessor");
			
			Method selectConfigsMethod = mixinProcessorClass.getDeclaredMethod("selectConfigs", MixinEnvironment.class);
			selectConfigsMethod.setAccessible(true);
			selectConfigsMethod.invoke(processor, MixinEnvironment.getCurrentEnvironment());
			
			Method prepareConfigsMethod;
			try {
				prepareConfigsMethod = mixinProcessorClass.getDeclaredMethod("prepareConfigs", MixinEnvironment.class);
				prepareConfigsMethod.setAccessible(true);
				prepareConfigsMethod.invoke(processor, MixinEnvironment.getCurrentEnvironment());
			} catch (NoSuchMethodException e) {
				DJ2AddonsCore.LOGGER.warn("Mixin version 0.7+ detected. Other mods using this type of Mixin loader may break.");
				
				prepareConfigsMethod = mixinProcessorClass.getDeclaredMethod("prepareConfigs", MixinEnvironment.class, Extensions.class);
				prepareConfigsMethod.setAccessible(true);
				
				Field extensionsField = mixinProcessorClass.getDeclaredField("extensions");
				
				extensionsField.setAccessible(true);
				
				Extensions extensions = (Extensions) extensionsField.get(processor);
				prepareConfigsMethod.invoke(processor, MixinEnvironment.getCurrentEnvironment(), extensions);
				
			}
			
			
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
		DJ2AddonsCore.onLoadCore();
	}
}
