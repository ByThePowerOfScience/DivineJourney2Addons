package btpos.dj2addons.util.fastutilutils;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;

/**
 * Collectors that generate fastutil Collections instead of the awful Java ones.
 */
public final class FastUtilCollectors {
	
	public static <T> Collector<T, ?, Set<T>> toObjectOpenHashSet() {
		return Collector.<T, ObjectOpenHashSet<T>, Set<T>>of(
				ObjectOpenHashSet::new,
				Set::add,
				(sup, sub) -> { sup.addAll(sub); return sup; },
				(set) -> { set.trim(); return set; },
				Characteristics.UNORDERED);
	}
	
	public static <T> Collector<T, ?, Set<T>> toImmutableOpenHashSet() {
		return Collector.<T, ObjectOpenHashSet<T>, Set<T>>of(
				ObjectOpenHashSet::new,
				Set::add,
				(sup, sub) -> { sup.addAll(sub); return sup; },
				(set) -> { set.trim(); return Collections.unmodifiableSet(set); },
				Characteristics.UNORDERED);
	}
	
	
	
	private FastUtilCollectors() {}
}
