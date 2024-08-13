package btpos.dj2addons;

import btpos.dj2addons.core.DJ2APreStartHook;
import btpos.dj2addons.core.asm.api.thaumcraft.infusionstabilizers.InfusionStabilizerClassTransformer;
import btpos.dj2addons.core.asm.api.thaumcraft.infusionstabilizers.JankConfig;
import btpos.dj2addons.common.CoreInfo;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.Name;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.SortingIndex;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import zone.rong.mixinbooter.IEarlyMixinLoader;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ResultOfMethodCallIgnored")

@Name("DJ2AddonsCore")
@SortingIndex(10)
@MCVersion("1.12.2")
@TransformerExclusions({"btpos.dj2addons.core"})
public class DJ2ACoremod implements IFMLLoadingPlugin, IEarlyMixinLoader {
	static {
		// Load classes in the right order to avoid a circularity error.
		CoreInfo.class.getName();
		JankConfig.class.getName();
		DJ2APreStartHook.class.getName();
		InfusionStabilizerClassTransformer.class.getName();
	}
	
	public DJ2ACoremod() {
		CoreInfo.LOGGER.info("DJ2Addons Coremod started!");
//		MixinBootstrap.init();
		
//		MixinExtrasBootstrap.init();
		
//		registerInitMixins();
	}
	
//	private void registerInitMixins() {
//		try {
//			MixinEnvironment initEnv = MixinEnvironment.getEnvironment(Phase.INIT);
//			Class<?> mixins = Class.forName("org.spongepowered.asm.mixin.Mixins");
//			Method createConfiguration = mixins.getDeclaredMethod("createConfiguration", String.class, MixinEnvironment.class);
//			createConfiguration.setAccessible(true);
//			createConfiguration.invoke(null, "mixins.dj2addons.bootstrap.json.bak", initEnv);
//		} catch (ReflectiveOperationException e) {
//			throw new NoClassDefFoundError("DJ2Addons Mixins Not Loaded! " + e.getMessage());
//		}
//	}
	
	@Override
	public List<String> getMixinConfigs() {
		return Collections.singletonList("mixins.dj2addons.init.json");
	}

	@Override
	public void onMixinConfigQueued(String mixinConfig) {
		CoreInfo.LOGGER.info("[MIXIN] Init phase config loaded: {}", mixinConfig);
	}
	
	@Override
	public String[] getASMTransformerClass() {
		return new String[] {
				"btpos.dj2addons.core.asm.api.thaumcraft.infusionstabilizers.InfusionStabilizerClassTransformer"
		};
	}
	
	@Override
	public String getModContainerClass() {
		return null;
	}
	
	@Override
	public String getSetupClass() {
		return "btpos.dj2addons.core.DJ2APreStartHook";
	}
	
	@Override
	public void injectData(Map<String, Object> data) {}
	
	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}
