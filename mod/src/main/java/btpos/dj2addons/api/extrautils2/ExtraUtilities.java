package btpos.dj2addons.api.extrautils2;

import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.util.ResourceLocation;

import java.util.Map;
import java.util.Set;

public final class ExtraUtilities {
	public static void setScaling(String millName, float[] values) {
		Internal.scalingMap.put(millName, values);
	}
	
	public static void addExcludedBiome(ResourceLocation rl) {
		Internal.biomeMarker_excludedBiomes.add(rl);
	}
	
	public static final class Internal {
		public static final Map<String, Float> basePowerMap = new Object2FloatOpenHashMap<>();
		private static final Map<String, float[]> scalingMap = new Object2ObjectOpenHashMap<>();
		
		private static final Set<ResourceLocation> biomeMarker_excludedBiomes = new ObjectOpenHashSet<>(7);
		
		public static float[] getScalingOrReturnOriginal(String s, float[] original) {
			float[] arr = scalingMap.get(s);
			if (arr != null)
				return arr;
			scalingMap.put(s, original);
			return original;
		}
		
		public static Map<String, float[]> getCurrentScaling() {
			return scalingMap;
		}
		
		public static Set<ResourceLocation> getExcludedBiomes() {
			return biomeMarker_excludedBiomes;
		}
	}
}
