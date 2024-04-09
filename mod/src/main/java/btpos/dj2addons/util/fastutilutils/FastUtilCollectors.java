package btpos.dj2addons.util.fastutilutils;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;

/**
 * Collectors that generate fastutil Collections instead of the awful Java ones.
 */
public final class FastUtilCollectors {
	public static final Set<Characteristics> CH_CONCURRENT_ID
			= Collections.unmodifiableSet(EnumSet.of(Characteristics.CONCURRENT,
			                                         Characteristics.UNORDERED,
			                                         Characteristics.IDENTITY_FINISH));
	
	public static final Set<Characteristics> CH_CONCURRENT_NOID
			= Collections.unmodifiableSet(EnumSet.of(Characteristics.CONCURRENT,
			                                         Characteristics.UNORDERED));
	
	public static final Set<Characteristics> CH_ID
			= Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
	
	public static final EnumSet<Characteristics> CH_UNORDERED_ID = EnumSet.of(Characteristics.UNORDERED, Characteristics.IDENTITY_FINISH);
	
	public static final Set<Characteristics> CH_NOID = Collections.emptySet();
	
	
	public static <T> Collector<T, ?, Set<T>> toObjectOpenHashSet() {
		return Collector.of(
				ObjectOpenHashSet::new,
				Set::add,
				(sup, sub) -> { sup.addAll(sub); return sup; },
				Function.identity(),
				CH_UNORDERED_ID.toArray(new Characteristics[0]));
	}
	
	
	
	private FastUtilCollectors() {}
	
}
