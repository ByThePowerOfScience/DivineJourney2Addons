package org.btpos.dj2addons.impl.api;

import com.google.gson.Gson;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class VCraftTweaker {
	public static final Set<Pair<Object, Function<Object, String>>> confirmations = new HashSet<>(); // (input) -> Success/Fail Message
	
	public static Set<String> validate() {
		return confirmations.stream()
		                    .map(pair -> pair.getRight().apply(pair.getLeft()))
		                    .collect(Collectors.toSet());
	}
}
