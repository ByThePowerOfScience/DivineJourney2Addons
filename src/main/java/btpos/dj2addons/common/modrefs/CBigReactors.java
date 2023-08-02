package btpos.dj2addons.common.modrefs;

import erogenousbeef.bigreactors.api.data.ReactorInteriorData;
import erogenousbeef.bigreactors.api.registry.ReactorInterior;

public class CBigReactors {
	public static ReactorInteriorDataWrapper getBlockData(String oreDict) {
		return new ReactorInteriorDataWrapper(ReactorInterior.getBlockData(oreDict));
	}
	
	public static ReactorInteriorDataWrapper getFluidData(String fluidName) {
		return new ReactorInteriorDataWrapper(ReactorInterior.getFluidData(fluidName));
	}
	
	public static class ReactorInteriorDataWrapper {
		private final ReactorInteriorData source;
		public ReactorInteriorDataWrapper(ReactorInteriorData rid) {
			this.source = rid;
		}
		
		public float absorption() {
			return source.absorption;
		}
		
		public float heatConductivity() {
			return this.source.heatConductivity;
		}
		
		public float moderation() {
			return source.moderation;
		}
		
		public float heatEfficiency() {
			return source.heatEfficiency;
		}
	}
}
