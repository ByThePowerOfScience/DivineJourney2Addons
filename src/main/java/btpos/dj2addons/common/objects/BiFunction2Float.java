package btpos.dj2addons.common.objects;

import stanhebben.zenscript.annotations.ZenClass;

@FunctionalInterface @ZenClass("dj2addons.util.BiFunction2Float")
public interface BiFunction2Float<T, U> {
	float call(T t, U u);
}
