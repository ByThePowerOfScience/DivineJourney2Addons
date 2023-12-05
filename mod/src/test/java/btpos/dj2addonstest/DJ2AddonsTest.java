package btpos.dj2addonstest;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.junit.platform.launcher.listeners.TestExecutionSummary.Failure;

import java.util.List;

@Mod(modid="dj2addonstest", name="dj2addonstest", dependencies = "required-after:dj2addons", version="1.0")
public class DJ2AddonsTest {
	
	public static final Logger LOGGER = LogManager.getLogger("DJ2AddonsTest");
	
//	@Mod.EventHandler
//	public void preInit(FMLPreInitializationEvent event) {
//		LOGGER.info("TESTING MOD LOADED");
//	}
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		LOGGER.info("STARTING TESTS");
		final LauncherDiscoveryRequest request =
				LauncherDiscoveryRequestBuilder.request()
				                               .selectors(DiscoverySelectors.selectPackage("btpos.dj2addonstest.*"))
				                               .build();
		
		final Launcher launcher = LauncherFactory.create();
		final SummaryGeneratingListener listener = new SummaryGeneratingListener();
		
		launcher.registerTestExecutionListeners(listener);
		launcher.execute(request);
		
		TestExecutionSummary summary = listener.getSummary();
		LOGGER.info("Tests found: {}", summary.getTestsFoundCount());
		LOGGER.info("getTestsSucceededCount() - " + summary.getTestsSucceededCount());
		List<Failure> failures = summary.getFailures();
		failures.forEach(failure -> LOGGER.error("Test Failed: " + failure.getTestIdentifier(), failure.getException()));
		System.exit(0);
	}
}
