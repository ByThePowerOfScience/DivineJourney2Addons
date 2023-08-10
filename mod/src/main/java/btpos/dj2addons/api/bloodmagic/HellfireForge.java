package btpos.dj2addons.api.bloodmagic;

public class HellfireForge {
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
		HellfireForge.worldWillTransferRate = worldWillTransferRate;
	}
	
	
	
	
	private static boolean craftWithAllWillTypes = false;
	
	public static boolean shouldCraftWithAllWillTypes() {
		return craftWithAllWillTypes;
	}
	
	public static void setCraftWithAllWillTypes(boolean craftWithAllWillTypes) {
		HellfireForge.craftWithAllWillTypes = craftWithAllWillTypes;
	}
}
