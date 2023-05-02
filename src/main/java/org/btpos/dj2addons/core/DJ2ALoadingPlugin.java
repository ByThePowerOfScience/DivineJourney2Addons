package org.btpos.dj2addons.core;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.btpos.dj2addons.asm.thaumcraft.InfusionStabilizerClassTransformer;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
@SuppressWarnings({"ResultOfMethodCallIgnored"})
public class DJ2ALoadingPlugin implements IFMLLoadingPlugin {
	static { // Loads classes in the right order to avoid a circularity error.
		DJ2AddonsCore.class.getName();
		JankConfig.class.getName();
		DJ2ASMPreStartHook.class.getName();
		InfusionStabilizerClassTransformer.class.getName();
	}
//	public DJ2ALoadingPlugin() {
//		registerMixins();
//
//		MixinBootstrap.init();
//	}

//	private void registerMixins() {
//		try {
//			Class<?> mixins = Class.forName("org.spongepowered.asm.mixin.Mixins");
//			Method createConfiguration = mixins.getDeclaredMethod("createConfiguration", String.class, MixinEnvironment.class);
//			createConfiguration.setAccessible(true);
////			createConfiguration.invoke(null, "mixins.dj2addons.loader.json", MixinEnvironment.getEnvironment(MixinEnvironment.Phase.INIT));
//			createConfiguration.invoke(null, "mixins.dj2addons.init.json", MixinEnvironment.getEnvironment(MixinEnvironment.Phase.INIT));
//		} catch (ReflectiveOperationException e) {
//			throw new NoClassDefFoundError("DJ2Addons Mixins Not Loaded! " + e.getMessage());
//		}
//	}
	
	@Override
	public String[] getASMTransformerClass() {
		return new String[] {
				"org.btpos.dj2addons.asm.thaumcraft.InfusionStabilizerClassTransformer"
		};
	}
	
	@Override
	public String getModContainerClass() {
		return null;
	}
	
	@Nullable
	@Override
	public String getSetupClass() {
		return "org.btpos.dj2addons.core.DJ2ASMPreStartHook";
	}
	
	@Override
	public void injectData(Map<String, Object> data) {}
	
	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}
