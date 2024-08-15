package btpos.dj2addons.common.objects;

@FunctionalInterface
public interface BiFunction2Boolean<T, U> {
	boolean call(T t, U u);
}
