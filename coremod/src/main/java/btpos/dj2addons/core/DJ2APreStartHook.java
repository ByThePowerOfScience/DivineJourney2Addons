package btpos.dj2addons.core;

import btpos.dj2addons.asm.api.thaumcraft.infusionstabilizers.JankConfig;
import net.minecraftforge.fml.relauncher.IFMLCallHook;

import java.util.Map;

public class DJ2APreStartHook implements IFMLCallHook {
	@Override
	public void injectData(Map<String, Object> data) {}
	
	@Override
	public Void call() {
		JankConfig.readInfusionStabilizers();
		return null;
	}
}
