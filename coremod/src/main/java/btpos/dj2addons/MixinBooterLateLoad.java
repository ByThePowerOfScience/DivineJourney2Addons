package btpos.dj2addons;

import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class MixinBooterLateLoad implements ILateMixinLoader {
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
}
