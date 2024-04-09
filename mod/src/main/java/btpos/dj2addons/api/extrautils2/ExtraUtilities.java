package btpos.dj2addons.api.extrautils2;

import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.util.ResourceLocation;

import java.util.Map;
import java.util.Set;

public final class ExtraUtilities {
	/**
	 * Sets the percent of its base power the mill should produce as a player's GP reaches a certain threshold.
	 *
	 * @param millName The name of the mill. (e.g. "DRAGON_EGG")
	 * @param values Map of [Grid Power threshold : production percentage].
	 * @see btpos.dj2addons.crafttweaker.extrautils2.CTMills#setScaling(String, java.util.Map) CraftTweaker API
	 */
	public static void setScaling(String millName, Map<Float, Float> values) {
		
		final float[] arr = values.entrySet()
		                          .stream()
		                          .sorted((entry, entry2) -> { // sort it lowest-to-greatest
			                          float diff = entry.getKey() - entry2.getKey();
			                          if (diff < 0)
										  return -1;
									  else if (diff == 0)
										  return 0;
									  else
										  return 1;
		                          })
		                          .reduce(new FloatArrayList(values.size() * 2),
		                                  (list1, entry) -> {
			                                  list1.add(entry.getKey());
			                                  list1.add(entry.getValue());
			                                  return list1;
		                                  },
		                                  (list1, list2) -> {
			                                  list1.addAll(list2);
			                                  return list1;
		                                  })
		                          .toFloatArray();
		
		millName = millName.toUpperCase()
		                   .replace(" ", "_")
		                   .trim();
		
		Internal.scalingMap.put(millName, arr);
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
