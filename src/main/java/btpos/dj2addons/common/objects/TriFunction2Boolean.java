package btpos.dj2addons.common.objects;

import stanhebben.zenscript.annotations.ZenClass;

@FunctionalInterface @ZenClass("dj2addons.util.TriFunction2Boolean")
public interface TriFunction2Boolean<T, U, V> {
	boolean call(T t, U u, V v);
}
