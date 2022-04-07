package org.btpos.dj2addons.bootstrapper.core;


import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import javax.annotation.Nullable;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
@IFMLLoadingPlugin.SortingIndex(-8000)
@IFMLLoadingPlugin.Name("Divine Journey 2 Addons Mixin Bootstrapper")
public class DJ2ALoadingPlugin implements IFMLLoadingPlugin {
	public static Logger logger = LogManager.getLogger("DJ2Addons Core");
	
	public DJ2ALoadingPlugin() {
		logger.info("Bootstrapping mixins.");
		MixinBootstrap.init();
		Mixins.addConfiguration("mixins.dj2addons.loader.json");
	}
	
	@Override
	public String[] getASMTransformerClass() {
		return new String[0];
	}
	
	@Override
	public String getModContainerClass() {
		return null;
	}
	
	@Nullable
	@Override
	public String getSetupClass() {
		return null;
	}
	
	@Override
	public void injectData(Map<String, Object> data) {
		
	}
	
	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}
