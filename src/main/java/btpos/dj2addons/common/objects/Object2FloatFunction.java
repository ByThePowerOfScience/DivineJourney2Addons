package btpos.dj2addons.common.objects;

import stanhebben.zenscript.annotations.ZenClass;

import java.util.function.Function;

@FunctionalInterface @ZenClass("dj2addons.util.Object2FloatFunction")
public interface Object2FloatFunction<T> extends Function<T, Float> {
	@Override @Deprecated
	default Float apply(T t) {
		return applyFloat(t);
	}
	
	float applyFloat(T t);
}
