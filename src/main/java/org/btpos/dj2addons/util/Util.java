package org.btpos.dj2addons.util;

public class Util {
	public static class Numbers {
		public static boolean isDecimal_Fast(Number n) {
			return (n.doubleValue() - n.intValue() != 0.0F);
		}
		
		public static boolean isDecimal(Number n) {
			return (n instanceof Double || n instanceof Float);
		}
	}
}
