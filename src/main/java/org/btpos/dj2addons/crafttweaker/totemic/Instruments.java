package org.btpos.dj2addons.crafttweaker.totemic;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import epicsquid.roots.util.zen.ZenDocAppend;
import epicsquid.roots.util.zen.ZenDocArg;
import epicsquid.roots.util.zen.ZenDocClass;
import epicsquid.roots.util.zen.ZenDocMethod;
import org.btpos.dj2addons.impl.totemic.VInstruments;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("dj2addons.totemic.Instruments") @ModOnly("totemic")
@ZenDocClass(value="dj2addons.totemic.Instruments", description = {
		"Handles musical instruments in Totemic.",
		"Must be run with the DJ2Addons loader, specified with `#loader dj2addons` at the top of the ZS file."
}) @ZenDocAppend("docs/include/instruments.example.md")
public class Instruments {
	@ZenDocMethod(order=1, description = {
			"Changes the stats of the base Totemic musical instruments.",
			"Using integers will overwrite the original values, but using decimals (e.g. 1.2, 3.0) will multiply the original values by that amount."
	}, args = {
			@ZenDocArg(arg="instrumentName", info="The resource name of the instrument, e.g. \"totemic:flute\"."),
			@ZenDocArg(arg="baseOutput", info="Sets or scales the base output music of the instrument."),
			@ZenDocArg(arg="musicMaximum", info="Sets or scales the cap on the total music this instrument type can produce.")
	})
	public static void modifyMusicValues(String instrumentName, Number baseOutput, Number musicMaximum) {
		VInstruments.putInstrumentModifications(instrumentName, baseOutput, musicMaximum);
	}

	@ZenMethod
	public static void modifyMusicValues(String instrumentName, int baseOutput, int musicMaximum) {
		VInstruments.putInstrumentModifications(instrumentName, baseOutput, musicMaximum);
	}

	@ZenMethod
	public static void modifyMusicValues(String instrumentName, double baseOutput, int musicMaximum) {
		VInstruments.putInstrumentModifications(instrumentName, baseOutput, musicMaximum);
	}

	@ZenMethod
	public static void modifyMusicValues(String instrumentName, int baseOutput, double musicMaximum) {
		VInstruments.putInstrumentModifications(instrumentName, baseOutput, musicMaximum);
	}

	@ZenMethod
	public static void modifyMusicValues(String instrumentName, double baseOutput, double musicMaximum) {
		VInstruments.putInstrumentModifications(instrumentName, baseOutput, musicMaximum);
	}

}
