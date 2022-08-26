package org.btpos.dj2addons.mixin.def.api.extremereactors;

import erogenousbeef.bigreactors.api.data.ReactorInteriorData;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ReactorInteriorData.class)
public class MReactorInteriorData {
	@Override
	public String toString() {
		ReactorInteriorData rid = (ReactorInteriorData)(Object)this;
		return "    §e- §eabsorption: §b" + rid.absorption + ",\n    §e- §eheatEfficiency: §b" + rid.heatEfficiency + ",\n    §e- §eheatConductivity: §b" + rid.heatConductivity + ",\n    §e- §emoderation: §b" + rid.moderation;
	}
}
