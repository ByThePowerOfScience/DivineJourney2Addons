package btpos.dj2addons.common.util;

import btpos.dj2addons.DJ2Addons;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Function;

public final class Util {
	public static final class Collections {
		
		/**
		 * Same as {@link java.util.Map#getOrDefault} but with a functional generator like in Java 9+.
		 * Saves some memory by only constructing the objects if it's actually needed.
		 */
		public static <K, V> V getOrDefault(Map<K, V> map, K key, Function<K, V> defaultGenerator) {
			V v;
			return ((v = map.get(key)) != null || map.containsKey(key)) ? v : defaultGenerator.apply(key);
		}
		
		/**
		 * Because I hate instantiating a Stream just for a single map operation.
		 */
		public static <T, U> U[] mapArray(T[] oldArr, Class<U> clazz, Function<T, U> converter) {
			@SuppressWarnings("unchecked")
			U[] newArr = (U[]) Array.newInstance(clazz, oldArr.length);
			for (int i = 0; i < oldArr.length; i++) {
				newArr[i] = converter.apply(oldArr[i]);
			}
			return newArr;
		}
	}
	
	public static class Numbers {
		public static boolean isDecimal_Fast(Number n) {
			return (n.floatValue() - n.intValue() != 0.0f);
		}
		
		public static boolean isDecimal(Number n) {
			return (n instanceof Float || n instanceof Double);
		}
	}
	
	public static class DevTools {
		@SuppressWarnings("unchecked")
		public static Map<String, Capability<?>> getAllCapabilities() {
			try {
				Field f = CapabilityManager.class.getDeclaredField("providers");
				f.setAccessible(true);
				return (Map<String, Capability<?>>) f.get(CapabilityManager.INSTANCE);
			} catch (IllegalAccessException | NoSuchFieldException e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	public static class Format {
		public static String capitalizeFirstLetter(String input) {
			return input.substring(0, 1).toLowerCase() + input.substring(1);
		}
		
		public static String uncapitalizeFirstLetter(String input) {
			return input.substring(0, 1).toLowerCase() + input.substring(1);
		}
		
		@NotNull
		public static String formatPos(BlockPos blockPos) {
			return "[" + blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ() + "]";
		}
	}
	
	
	public static ResourceLocation loc(String unitName) {
		return new ResourceLocation(DJ2Addons.MOD_ID, unitName);
	}
}
