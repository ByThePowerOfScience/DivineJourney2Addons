package btpos.dj2addons;

import btpos.dj2addons.common.CoreInfo;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class LateMixinHolder implements ILateMixinLoader {
	@Override
	public List<String> getMixinConfigs() {
		return Arrays.asList(
				"mixins.dj2addons.def.api.json",
				"mixins.dj2addons.def.custom.json",
				"mixins.dj2addons.def.optimizations.json",
				"mixins.dj2addons.def.patches.json",
				"mixins.dj2addons.def.tweaks.json"
        );
	}
	
	@Override
	public void onMixinConfigQueued(String mixinConfig) {
		CoreInfo.LOGGER.info("[MIXIN] Default phase config loaded: {}", mixinConfig);
	}
}
