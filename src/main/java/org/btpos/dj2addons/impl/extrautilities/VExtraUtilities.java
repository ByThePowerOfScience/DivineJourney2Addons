package org.btpos.dj2addons.impl.extrautilities;

import java.util.HashMap;
import java.util.Map;

public class VExtraUtilities {
	private static final Map<String, float[]> scalingMap = new HashMap<>();
	
	public static void setScaling(String millName, float[] values) {
		scalingMap.put(millName, values);
	}
	
	public static float[] getFromMapOrOriginal(String s, float[] scaling) {
		float[] arr = scalingMap.get(s);
		if (arr != null)
			return arr;
		return scaling;
	}
	
	
	private static final Map<String, Float> basePowerMap = new HashMap<>();
}
