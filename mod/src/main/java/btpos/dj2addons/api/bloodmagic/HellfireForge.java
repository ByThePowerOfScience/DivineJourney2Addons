package btpos.dj2addons.api.bloodmagic;

import btpos.dj2addons.api.mixin.bloodmagic.TileSoulForgeAccessor;

public final class HellfireForge {
	
	/**
	 * Sets the number of ticks taken to craft an item with the Hellfire Forge.
	 */
	public static void setTicksRequired(int ticks) {
		TileSoulForgeAccessor.setTicksRequired(ticks);
	}
	
	/**
	 * Sets the rate that the Hellfire Forge should consume souls from the chunk.
	 */
	public static void setWorldWillTransferRate(double worldWillTransferRate) {
		TileSoulForgeAccessor.setWorldWillTransferRate(worldWillTransferRate);
	}
	
	
	public static void setCraftWithAllWillTypes(boolean craftWithAllWillTypes) {
		Internal.craftWithAllWillTypes = craftWithAllWillTypes;
	}
	
	
	/**
	 * For backend implementation of this API.
	 * @see btpos.dj2addons.api.mixin.bloodmagic.TileSoulForgeAccessor#setWorldWillTransferRate(double) setWorldWillTransferRate impl
	 * @see btpos.dj2addons.api.mixin.bloodmagic.TileSoulForgeAccessor#setTicksRequired(int) setTicksRequired impl
	 */
	public static final class Internal {
		
		private static boolean craftWithAllWillTypes = false;
		
		
		/**
		 * @see btpos.dj2addons.api.mixin.bloodmagic.MTileSoulForge#modifyDemonWillTypeUsed
		 */
		public static boolean shouldCraftWithAllWillTypes() {
			return craftWithAllWillTypes;
		}
	}
}
