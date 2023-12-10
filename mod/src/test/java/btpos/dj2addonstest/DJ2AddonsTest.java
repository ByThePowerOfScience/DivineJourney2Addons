package btpos.dj2addonstest;

import btpos.dj2addonscore.DJ2AMixinConfig;
import btpos.dj2addonstest.optimizations.mixin.industrialforegoing.MPlantInteractorTest;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.spongepowered.asm.mixin.Mixins;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

//@Mod(modid="dj2addonstest", name="dj2addonstest", version="1.0", dependencies="after:agricraft")
public class DJ2AddonsTest implements IFMLLoadingPlugin {
	
	public static final Logger LOGGER = LogManager.getLogger("DJ2AddonsTest");
	
	public DJ2AddonsTest() {
		Mixins.addConfiguration("mixins.dj2addonstest.json");
		Mixins.addConfigurations(DJ2AMixinConfig.defConfigs);
		Mixins.addConfiguration("mixins.dj2addons.init.json");
	}
	
	
	//	@Mod.EventHandler
//	public void postInit(FMLPostInitializationEvent event) {
//		LOGGER.info("TESTING MOD LOADED");
//	}
	
//	@Mod.EventHandler
//	public void preInit(FMLPreInitializationEvent event) {
//		runTests();
//	}
//
	
	static List<String> checkLoadedConfigs() {
		List<String> list = new ArrayList<String>(Arrays.asList(DJ2AMixinConfig.defConfigs));
		list.add("mixins.dj2addons.bootstrap.json");
		list.add("mixins.dj2addons.init.json");
		Map<String, Boolean> haveVisited = list.stream().collect(Collectors.toMap(Function.identity(), s -> false));
		
		Set<String> configs;
		try {
			Field registeredConfigs = Mixins.class.getDeclaredField("registeredConfigs");
			registeredConfigs.setAccessible(true);
			configs = (Set<String>) registeredConfigs.get(null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		configs.forEach(config -> {
			if (config.contains("dj2addons")) {
				haveVisited.put(config, true);
			}
		});
		return haveVisited.entrySet().stream().map((entry -> {
			if (entry.getValue())
				return '-' + entry.getKey();
			else
				return '-' + entry.getKey() + ": FAILED";
		})).collect(Collectors.toList());
	}
	
	public static void runTests() {
		LOGGER.info("STARTING TESTS");
		
		System.gc();
		
		MPlantInteractorTest.dotestmanually();
		
		List<String> configStrings = checkLoadedConfigs();
		final LauncherDiscoveryRequest request =
				LauncherDiscoveryRequestBuilder.request()
				                               .selectors(DiscoverySelectors.selectPackage("btpos.dj2addonstest"))
				                               .build();
		
		final Launcher launcher = LauncherFactory.create();
		final SummaryGeneratingListener listener = new SummaryGeneratingListener();
		
		launcher.registerTestExecutionListeners(listener);
		launcher.execute(request);
		
		TestExecutionSummary summary = listener.getSummary();
		LOGGER.info("Mixin configs:");
		configStrings.forEach(LOGGER::info);
		LOGGER.info("Tests found: " + summary.getTestsFoundCount());
		LOGGER.info("Tests succeeded: " + summary.getTestsSucceededCount());
		summary.printFailuresTo(new PrintWriter(System.out));
		summary.getFailures().forEach(failure -> failure.getException().printStackTrace(System.out));
		
		FMLCommonHandler.instance().exitJava(0, false);
	}
	
	@Override
	public String[] getASMTransformerClass() {
		return new String[0];
	}
	
	@Override
	public String getModContainerClass() {
		return null;
	}
	
	@Override
	public String getSetupClass() {
		return null;
	}
	
	@Override
	public void injectData(Map<String, Object> data) {}
	
	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}
