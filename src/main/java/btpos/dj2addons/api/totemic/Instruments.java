package btpos.dj2addons.api.totemic;


import btpos.dj2addons.common.util.Util;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Holds the map for the instruments' new data
public final class Instruments {
	
	/**
	 * Changes the Base Output and/or the Maximum Music of a given Totemic instrument. Note that this method has different behavior depending on the type of number passed in.
	 * Example: <pre>{@code
	 * // Multiply the Eagle Bone Whistle's base output by 40 and add 20 to its maximum music.
	 * Instruments.modifyInstrumentMusicValues("totemic:eagle_bone_whistle", 40.0f, 20);
	 *
	 * // Set the Wind Chime's base music output to 12 and leave its maximum music unchanged.
	 * Instruments.modifyInstrumentMusicValues("totemic:windChime", 12, null);
	 * }</pre>
	 * @param instrumentName The namespaced name of the instrument to modify. e.g. {@code "totemic:eagle_bone_whistle"}
	 * @param baseOutput The scale factor or value of the output per "play" action. Use a float to multiply it by the value, an int to set the value, and null for "unchanged".
	 * @param musicMaximum The maximum amount of music that instrument can produce per ritual. Use a float to multiply it by the value, an int to set the value, and null for "unchanged".
	 * @see btpos.dj2addons.crafttweaker.totemic.CTInstruments#modifyMusicValues(String, Number, Number)  CraftTweaker API
	 */
	public static void modifyInstrumentMusicValues(String instrumentName, @Nullable Number baseOutput, @Nullable Number musicMaximum) {
		Internal.INSTRUMENT_MAP.computeIfAbsent(instrumentName, (k) -> new ArrayList<>(1)).add(Pair.of(baseOutput, musicMaximum));
	}
	
	
	
	public static final class Internal {
		private static final Map<String, List<Pair<? extends Number, ? extends Number>>> INSTRUMENT_MAP = new Object2ObjectOpenHashMap<>();
		
		public static Pair<Integer, Integer> getValuesForInstrument(String name, int baseOutput, int musicMaximum) {
			List<Pair<? extends Number, ? extends Number>> vals = INSTRUMENT_MAP.remove(name);
			if (vals != null) {
				for (Pair<? extends Number, ? extends Number> val : vals) {
					baseOutput = setOrMult(val.getLeft(), baseOutput);
					musicMaximum = setOrMult(val.getRight(), musicMaximum);
				}
			}
			return Pair.of(baseOutput, musicMaximum);
		}
		
		private static int setOrMult(Number val, int original) {
			if (val == null)
				return original;
			
			if (Util.Numbers.isDecimal(val))
				return Math.round(original * val.floatValue());
			
			return val.intValue();
		}
		
		private Internal(){}
	}
	
	private Instruments() {}
}
