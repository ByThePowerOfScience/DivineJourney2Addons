package btpos.dj2addons.api.extremereactors;

import btpos.dj2addons.DJ2Addons;

/**
 * APIs for ExtremeReactors by erogenousbeef
 */
public final class ExtremeReactors {
	
	/**
	 * Sets the maximum amount of energy the Legacy Reactor Output should be able to hold.
	 * @param max The new value.
	 */
	public static void setMaxEnergyStored(long max) {
		if (Internal.maxEnergyStored != -1L) {
			DJ2Addons.LOGGER.warn("Reactors.maxEnergyStored is already set to {}! Changing to {}.", Internal.maxEnergyStored, max);
		}
		Internal.maxEnergyStored = max;
	}
	
	public static final class Internal {
		private static long maxEnergyStored = -1L;
		
		public static long getMaxEnergyStored() {
			return maxEnergyStored;
		}
		
		private Internal() {}
	}
	
	private ExtremeReactors() {}
}
