package org.btpos.dj2addons.impl.api;

import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.util.Set;

public class Global {
	private static final Reflections reflections = new Reflections(
			new ConfigurationBuilder().addScanners()
	);
	public static <T> Set<Class<? extends T>> getAllOfType(Class<T> superType) {
			return (reflections.getSubTypesOf(superType));
	}
}
