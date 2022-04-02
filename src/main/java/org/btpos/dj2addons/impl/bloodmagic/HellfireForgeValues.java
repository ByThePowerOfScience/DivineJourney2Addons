package org.btpos.dj2addons.impl.bloodmagic;

public class HellfireForgeValues {
	private static int ticksRequired = 40;
	
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
		HellfireForgeValues.worldWillTransferRate = worldWillTransferRate;
	}
	
	private static double worldWillTransferRate = 1;
	
	
}
