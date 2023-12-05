package btpos.dj2addonscore;

import btpos.dj2addonscore.asm.api.thaumcraft.infusionstabilizers.InfusionStabilizerClassTransformer;
import btpos.dj2addonscore.asm.api.thaumcraft.infusionstabilizers.JankConfig;
import btpos.dj2addonscore.common.CoreInfo;
import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.MixinEnvironment.Phase;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
@SuppressWarnings({"ResultOfMethodCallIgnored"})
public class DJ2ALoadingPlugin implements IFMLLoadingPlugin {
	static { // Loads classes in the right order to avoid a circularity error.
		CoreInfo.class.getName();
		JankConfig.class.getName();
		DJ2APreStartHook.class.getName();
		InfusionStabilizerClassTransformer.class.getName();
	}
	public DJ2ALoadingPlugin() {
		MixinBootstrap.init();
		MixinExtrasBootstrap.init();
		registerInitMixins();
	}

	private void registerInitMixins() {
		try {
			final MixinEnvironment initEnv = MixinEnvironment.getEnvironment(Phase.INIT);
			final Class<?> mixins = Class.forName("org.spongepowered.asm.mixin.Mixins");
			final Method createConfiguration = mixins.getDeclaredMethod("createConfiguration", String.class, MixinEnvironment.class);
			createConfiguration.setAccessible(true);
			createConfiguration.invoke(null, "mixins.dj2addons.bootstrap.json", initEnv);
		} catch (ReflectiveOperationException e) {
			CoreInfo.failedToLoadCore(e); // throws MixinError
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
	
	@Override
	public String getSetupClass() {
		return "btpos.dj2addonscore.DJ2APreStartHook";
	}
	
	@Override
	public void injectData(Map<String, Object> data) {
		Field nameField = getNameField();
		if (nameField == null) {
			return;
		}
		
		try {
			for (Object coremod : ((List<?>) data.get("coremodList"))) {
				if ("tickprofiler".equals(nameField.get(coremod))) {
					CoreInfo.doesTickProfilerExist = true;
					break;
				}
			}
		} catch (IllegalAccessException ignored) {}
	}
	
	/**
	 * Because for some reason they give us a list of private objects...
	 * @return The Field corresponding to `String name`.
	 */
	private Field getNameField() {
		try {
			final Class<?> cPluginWrapper = Class.forName("net.minecraftforge.fml.relauncher.CoreModManager.FMLPluginWrapper");
			return cPluginWrapper.getField("name");
		} catch (ReflectiveOperationException e) {
			return null;
		}
	}
	
	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}
