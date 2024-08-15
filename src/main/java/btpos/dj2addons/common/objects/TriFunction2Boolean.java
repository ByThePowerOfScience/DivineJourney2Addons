package btpos.dj2addons.common.objects;

@FunctionalInterface
public interface TriFunction2Boolean<T, U, V> {
	boolean call(T t, U u, V v);
}
