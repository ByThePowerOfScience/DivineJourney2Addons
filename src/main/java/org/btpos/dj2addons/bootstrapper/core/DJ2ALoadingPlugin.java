package org.btpos.dj2addons.bootstrapper.core;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import java.util.Map;

public class DJ2ALoadingPlugin implements IFMLLoadingPlugin {
	public DJ2ALoadingPlugin() {
		Mixins.addConfiguration("mixins.dj2addons.loader.json");
		Mixins.addConfiguration("mixins.dj2addons.preinit.json");
		MixinBootstrap.init();
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
