package org.btpos.dj2addons.crafttweaker.impl.bloodmagic;

public class VSoulForge {
	private static int ticksRequired = 100;
	
	public static void setTicksRequired(int ticks) {
		ticksRequired = ticks;
	}
	
	public static int getTicksRequired() {
		return ticksRequired;
	}
	
	
	
	
	private static double worldWillTransferRate = 1;
	
	public static double getWorldWillTransferRate() {
		return worldWillTransferRate;
	}
	
	public static void setWorldWillTransferRate(double worldWillTransferRate) {
		VSoulForge.worldWillTransferRate = worldWillTransferRate;
	}
	
	
	
	
	private static boolean craftWithAllWillTypes = false;
	
	public static boolean shouldCraftWithAllWillTypes() {
		return craftWithAllWillTypes;
	}
	
	public static void setCraftWithAllWillTypes(boolean craftWithAllWillTypes) {
		VSoulForge.craftWithAllWillTypes = craftWithAllWillTypes;
	}
}
