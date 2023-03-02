package org.btpos.dj2addons.util;

import net.minecraft.util.ResourceLocation;
import org.btpos.dj2addons.DJ2Addons;

public class Util {
	public static class Numbers {
		public static boolean isDecimal_Fast(Number n) {
			return (n.doubleValue() - n.intValue() != 0.0D);
		}
		
		public static boolean isDecimal(Number n) {
			return (n instanceof Double || n instanceof Float);
		}
	}
	
	public static ResourceLocation loc(String unitName) {
		return new ResourceLocation(DJ2Addons.MOD_ID, unitName);
	}
}
