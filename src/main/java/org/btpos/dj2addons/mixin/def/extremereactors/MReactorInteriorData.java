package org.btpos.dj2addons.mixin.def.extremereactors;

import erogenousbeef.bigreactors.api.data.ReactorInteriorData;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ReactorInteriorData.class)
public class MReactorInteriorData {
	@Override
	public String toString() {
		ReactorInteriorData rid = (ReactorInteriorData)(Object)this;
		return super.toString() + "[absorption: " + rid.absorption + ", heatEfficiency: " + rid.heatEfficiency + ", heatConductivity: " + rid.heatConductivity + ", moderation: " + rid.moderation + "]";
	}
}
