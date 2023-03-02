package org.btpos.dj2addons.impl.api.extrautilities;

import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
	
	
	private static final Set<ResourceLocation> biomeMarker_excludedBiomes = new HashSet<>();
	
	public static void addExcludedBiome(ResourceLocation rl) {
		biomeMarker_excludedBiomes.add(rl);
	}
	
	public static Set<ResourceLocation> getExcludedBiomes() {
		return biomeMarker_excludedBiomes;
	}
}
