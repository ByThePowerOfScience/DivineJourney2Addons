package org.btpos.dj2addons.impl.extrautilities;

import java.util.HashMap;
import java.util.Map;

public class VExtraUtilities {
	private static final Map<String, float[]> scalingMap = new HashMap<>();
	
	public static void setScaling(String millName, float[] values) {
		scalingMap.put(millName, values);
	}
	
	public static float[] getFromMapOrOriginal(String s, float[] original) {
		float[] arr = scalingMap.get(s);
		if (arr != null)
			return arr;
		scalingMap.put(s, original);
		return original;
	}
	
	public static Map<String, float[]> getCurrentScaling() {
		return scalingMap;
	}
}
