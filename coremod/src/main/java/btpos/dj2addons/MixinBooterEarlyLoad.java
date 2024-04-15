package btpos.dj2addons;

import zone.rong.mixinbooter.IEarlyMixinLoader;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class MixinBooterEarlyLoad implements IEarlyMixinLoader {
	@Override
	public List<String> getMixinConfigs() {
		return Collections.singletonList("mixins.dj2addons.init.json");
	}
}
