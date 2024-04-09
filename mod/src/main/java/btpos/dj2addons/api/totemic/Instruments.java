package btpos.dj2addons.api.totemic;


import btpos.dj2addons.DJ2Addons;
import btpos.dj2addons.common.util.Util;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

// Holds the map for the instruments' new data
public final class Instruments {
	
	public static void putInstrumentModifications(String instrumentName, Number baseOutput, Number musicMaximum) {
		Internal.INSTRUMENT_MAP.put(instrumentName, Pair.of(baseOutput, musicMaximum));
	}
	
	private static int setOrMult(Number val, int original) {
		try {
			if (Util.Numbers.isDecimal(val))
				return Math.round(original * val.floatValue());
			else
				return val.intValue();
		} catch (NullPointerException e) {
			DJ2Addons.LOGGER.catching(e);
			return original;
		}
	}
	
	public static final class Internal {
		private static final Map<String, Pair<? extends Number, ? extends Number>> INSTRUMENT_MAP = new HashMap<>();
		
		public static Pair<Integer, Integer> getValuesForInstrument(String name, int baseOutput, int musicMaximum) {
			Pair<? extends Number, ? extends Number> vals = INSTRUMENT_MAP.get(name);
			if (vals != null) {
				baseOutput = setOrMult(vals.getLeft(), baseOutput);
				musicMaximum = setOrMult(vals.getRight(), musicMaximum);
			}
			return Pair.of(baseOutput, musicMaximum);
		}
		
		private Internal(){}
	}
	
	private Instruments() {}
}
