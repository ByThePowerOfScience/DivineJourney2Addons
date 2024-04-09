package btpos.dj2addons.api.extremereactors;

import btpos.dj2addons.DJ2Addons;

public final class ExtremeReactors {
	
	public static void setMaxEnergyStored(long l) {
		if (Internal.maxEnergyStored != -1L) {
			DJ2Addons.LOGGER.warn("Reactors.maxEnergyStored is already set to {}! Changing to {}.", Internal.maxEnergyStored, l);
		}
		Internal.maxEnergyStored = l;
	}
	
	public static final class Internal {
		public static long maxEnergyStored = -1L;
	}
}
