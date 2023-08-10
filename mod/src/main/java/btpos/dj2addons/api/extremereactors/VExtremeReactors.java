package btpos.dj2addons.api.extremereactors;

import btpos.dj2addons.DJ2Addons;

public class VExtremeReactors {
	
	public static void setMaxEnergyStored(long l) {
		if (Internal.maxEnergyStored != null) {
			DJ2Addons.LOGGER.warn("Reactors.maxEnergyStored is already set to {}! Changing to {}.", Internal.maxEnergyStored, l);
		}
		Internal.maxEnergyStored = l;
	}
	
	public static class Internal {
		public static Long maxEnergyStored = null;
	}
}
