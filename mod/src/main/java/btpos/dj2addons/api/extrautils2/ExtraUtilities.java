package btpos.dj2addons.api.extrautils2;

import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @see btpos.dj2addons.crafttweaker.extrautils2.CTMills CraftTweaker Mills API
 * @see btpos.dj2addons.crafttweaker.extrautils2.CTBiomeMarker CraftTweaker Biome Marker API
 */
public final class ExtraUtilities {
	/**
	 * Sets the percent of its base power the mill should produce as a player's GP reaches a certain threshold.
	 *
	 * @param millName The name of the mill. (e.g. "DRAGON_EGG")
	 * @param values Map of [Grid Power threshold : production percentage].
	 * @see btpos.dj2addons.crafttweaker.extrautils2.CTMills#setScaling(String, java.util.Map) CraftTweaker API
	 * @implNote This only modifies the standard Extra Utilities mills. Third-party mills must be manually targeted via Mixin.
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
	
	/**
	 * Sets the base power the mill should produce. This is the power it will give a player with 0 current GP.
	 *
	 * @param millName The name of the mill. (e.g. "DRAGON_EGG")
	 * @param power The base power the mill should have.
	 * @see btpos.dj2addons.crafttweaker.extrautils2.CTMills#setBaseValue(String, Float) CraftTweaker API
	 * @implNote This only modifies the standard Extra Utilities mills. Third-party mills must be manually targeted via Mixin.
	 */
	public static void setBasePower(String millName, float power) {
		Internal.basePowerMap.put(millName.toUpperCase().replace(" ", "_").trim(), power);
	}
	
	/**
	 * Prevent a biome from being used in the Quantum Quarry by stopping it from configuring a Biome Marker on right-click.
	 * @param rl The namespaced identifier of the biome, e.g. {@code new ResourceLocation("minecraft", "plains")}
	 * @see btpos.dj2addons.crafttweaker.extrautils2.CTBiomeMarker#excludeBiome(crafttweaker.api.world.IBiome) CraftTweaker API
	 */
	public static void addExcludedBiome(ResourceLocation rl) {
		Internal.biomeMarker_excludedBiomes.add(rl);
	}
	
	
	/**
	 * Internal implementation items. Not for external use.
	 */
	public static final class Internal {
		private static final Object2FloatOpenHashMap<String> basePowerMap;
		private static final Map<String, float[]> scalingMap = new Object2ObjectOpenHashMap<>();
		private static final Set<ResourceLocation> biomeMarker_excludedBiomes = new ObjectOpenHashSet<>(7);
		
		static {
			basePowerMap = new Object2FloatOpenHashMap<>();
			basePowerMap.defaultReturnValue(Float.MIN_VALUE);
		}
		
		public static float[] getScaling(String s, float[] original) {
			return scalingMap.getOrDefault(s, original);
		}
		
		public static float getBasePower(String millName, float original) {
			float fromMap = basePowerMap.getFloat(millName);
			if (fromMap == Float.MIN_VALUE)
				return original;
			return fromMap;
		}
		
		public static boolean isExcludedBiome(ResourceLocation rl) {
			return biomeMarker_excludedBiomes.contains(rl);
		}
		
		/**
		 * @return A read-only view of the scaling map.
		 */
		public static Map<String, float[]> getScalingMap() {
			return Collections.unmodifiableMap(scalingMap);
		}
		
		/**
		 * @return A read-only view of the base power map.
		 */
		public static Map<String, Float> getBasePowerMap() {
			return Collections.unmodifiableMap(basePowerMap);
		}
		
		/**
		 * @return A read-only view of the excluded biomes.
		 */
		public static Set<ResourceLocation> getExcludedBiomes() {
			return Collections.unmodifiableSet(biomeMarker_excludedBiomes);
		}
	}
}
