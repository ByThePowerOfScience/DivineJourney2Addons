package org.btpos.dj2addons.impl.totemic;


import org.apache.commons.lang3.tuple.Pair;
import org.btpos.dj2addons.util.Util;

import java.util.HashMap;
import java.util.Map;

// Holds the map for the instruments' new data
public class VInstruments {
	private static final Map<String, Pair<? extends Number, ? extends Number>> INSTRUMENT_MAP = new HashMap<>();
	
	public static void putInstrumentModifications(String instrumentName, Number baseOutput, Number musicMaximum) {
		INSTRUMENT_MAP.put(instrumentName, Pair.of(baseOutput, musicMaximum));
	}

	public static Pair<Integer, Integer> getValuesForInstrument(String name, int baseOutput, int musicMaximum) {
		Pair<? extends Number, ? extends Number> vals = VInstruments.INSTRUMENT_MAP.get(name);
		if (vals != null) {
			baseOutput = setOrMult(vals.getLeft(), baseOutput);
			musicMaximum = setOrMult(vals.getRight(), musicMaximum);
		}
		return Pair.of(baseOutput, musicMaximum);
	}

	private static int setOrMult(Number val, int original) {
		try {
			if (Util.Numbers.isDecimal(val))
				return Math.round(original * val.floatValue());
			else
				return val.intValue();
		} catch (NullPointerException e) {
			e.printStackTrace();
			return original;
		}
	}

}
