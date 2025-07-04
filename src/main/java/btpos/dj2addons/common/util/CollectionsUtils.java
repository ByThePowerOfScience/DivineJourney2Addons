package btpos.dj2addons.common.util;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import java.lang.reflect.Array;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;

public final class CollectionsUtils {
	
	public static <T> boolean anyMatch(T[] arr, Predicate<T> toCheck) {
		for (int i = 0; i < arr.length; i++) {
			if (toCheck.test(arr[i]))
				return true;
		}
		return false;
	}
	
	public static <T> boolean anyMatch(T[] arr, T toCheck) {
		for (int i = 0; i < arr.length; i++) {
			if (Objects.equals(arr[i], toCheck))
				return true;
		}
		return false;
	}
	
	public static String map_toString(Map<?, ?> map) {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		map.forEach((x, y) -> sb.append("\t").append(x.toString()).append(" : ").append(y.toString()).append("\n"));
		sb.append("}");
		return sb.toString();
	}
	
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
	
	/**
	 * Collectors that generate fastutil Collections instead of the Java ones.
	 */
	public static final class FastUtilCollectors {
		public static <T> Collector<T, ?, Set<T>> toObjectOpenHashSet() {
			return Collector.<T, ObjectOpenHashSet<T>, Set<T>>of(
					ObjectOpenHashSet::new,
					Set::add,
					(sup, sub) -> {
						sup.addAll(sub);
						return sup;
					},
					(set) -> {
						set.trim();
						return set;
					},
					Characteristics.UNORDERED);
		}
		
		public static <T> Collector<T, ?, Set<T>> toImmutableOpenHashSet() {
			return Collector.<T, ObjectOpenHashSet<T>, Set<T>>of(
					ObjectOpenHashSet::new,
					Set::add,
					(sup, sub) -> {
						sup.addAll(sub);
						return sup;
					},
					(set) -> {
						set.trim();
						return java.util.Collections.unmodifiableSet(set);
					},
					Characteristics.UNORDERED);
		}
		
		
		private FastUtilCollectors() {}
	}
}
