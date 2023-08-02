package btpos.dj2addonscore;

import btpos.dj2addonscore.asm.api.thaumcraft.infusionstabilizers.InfusionStabilizerClassTransformer;
import btpos.dj2addonscore.common.CoreInfo;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.MixinEnvironment.Phase;

import java.lang.reflect.Method;
import java.util.Map;
@SuppressWarnings({"ResultOfMethodCallIgnored"})
public class DJ2ALoadingPlugin implements IFMLLoadingPlugin {
	static { // Loads classes in the right order to avoid a circularity error.
		CoreInfo.class.getName();
		DJ2APreStartHook.class.getName();
		InfusionStabilizerClassTransformer.class.getName();
	}
	public DJ2ALoadingPlugin() {
		registerInitMixins();
		
		MixinBootstrap.init();
	}

	private void registerInitMixins() {
		try {
			MixinEnvironment initEnv = MixinEnvironment.getEnvironment(Phase.INIT);
			Class<?> mixins = Class.forName("org.spongepowered.asm.mixin.Mixins");
			Method createConfiguration = mixins.getDeclaredMethod("createConfiguration", String.class, MixinEnvironment.class);
			createConfiguration.setAccessible(true);
			for (String initConfig : DJ2AMixinConfig.initConfigs) {
				createConfiguration.invoke(null, initConfig, initEnv);
			}
		} catch (ReflectiveOperationException e) {
			throw new NoClassDefFoundError("DJ2Addons Mixins Not Loaded! " + e.getMessage());
		}
	}
	
	@Override
	public String[] getASMTransformerClass() {
		return new String[] {
				"btpos.dj2addonscore.asm.api.thaumcraft.infusionstabilizers.InfusionStabilizerClassTransformer"
		};
	}
	
	@Override
	public String getModContainerClass() {
		return null;
	}
	
	@Nullable
	@Override
	public String getSetupClass() {
		return "btpos.dj2addonscore.DJ2APreStartHook";
	}
	
	@Override
	public void injectData(Map<String, Object> data) {}
	
	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}
