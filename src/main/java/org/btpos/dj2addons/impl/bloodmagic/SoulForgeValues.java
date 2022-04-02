package org.btpos.dj2addons.impl.bloodmagic;

public class SoulForgeValues {
	private static int ticksRequired = 100;
	
	public static void setTicksRequired(int ticks) {
		ticksRequired = ticks;
	}
	
	public static int getTicksRequired() {
		return ticksRequired;
	}
	
	
	public static double getWorldWillTransferRate() {
		return worldWillTransferRate;
	}
	
	public static void setWorldWillTransferRate(double worldWillTransferRate) {
		SoulForgeValues.worldWillTransferRate = worldWillTransferRate;
	}
	
	private static double worldWillTransferRate = 1;
	
	
	private static boolean craftWithAllWillTypes = false;
	
	public static boolean shouldCraftWithAllWillTypes() {
		return craftWithAllWillTypes;
	}
	
	public static void setCraftWithAllWillTypes(boolean craftWithAllWillTypes) {
		SoulForgeValues.craftWithAllWillTypes = craftWithAllWillTypes;
	}
}
