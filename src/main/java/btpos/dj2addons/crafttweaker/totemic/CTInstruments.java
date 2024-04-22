package btpos.dj2addons.crafttweaker.totemic;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import btpos.dj2addons.common.util.zendoc.ZenDocAppend;
import btpos.dj2addons.common.util.zendoc.ZenDocArg;
import btpos.dj2addons.common.util.zendoc.ZenDocClass;
import btpos.dj2addons.common.util.zendoc.ZenDocMethod;
import btpos.dj2addons.api.totemic.Instruments;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("dj2addons.totemic.Instruments") @ModOnly("totemic")
@ZenDocClass(value="dj2addons.totemic.Instruments", description = {
		"Handles musical instruments in Totemic.",
		"Must be run with the preinit loader, specified with `#loader preinit` at the top of the ZS file."
}) @ZenDocAppend("docs/include/instruments.example.md")
public class CTInstruments {
	@ZenDocMethod(order=1, description = {
			"Changes the stats of the base Totemic musical instruments.",
			"Using integers (e.g. 1, 2) will overwrite the original values, but using decimals (e.g. 1.2, 3.0) will multiply the original values by that amount."
	}, args = {
			@ZenDocArg(value ="instrumentName", info="The resource name of the instrument, e.g. \"totemic:flute\"."),
			@ZenDocArg(value ="baseOutput", info="Sets or scales the base output music of the instrument."),
			@ZenDocArg(value ="musicMaximum", info="Sets or scales the cap on the total music this instrument type can produce.")
	})
	public static void modifyMusicValues(String instrumentName, Number baseOutput, Number musicMaximum) {
		Instruments.modifyInstrumentMusicValues(instrumentName, baseOutput, musicMaximum);
	}

	@ZenMethod
	public static void modifyMusicValues(String instrumentName, int baseOutput, int musicMaximum) {
		Instruments.modifyInstrumentMusicValues(instrumentName, baseOutput, musicMaximum);
	}

	@ZenMethod
	public static void modifyMusicValues(String instrumentName, double baseOutput, int musicMaximum) {
		Instruments.modifyInstrumentMusicValues(instrumentName, (float) baseOutput, musicMaximum);
	}

	@ZenMethod
	public static void modifyMusicValues(String instrumentName, int baseOutput, double musicMaximum) {
		Instruments.modifyInstrumentMusicValues(instrumentName, baseOutput, (float) musicMaximum);
	}

	@ZenMethod
	public static void modifyMusicValues(String instrumentName, double baseOutput, double musicMaximum) {
		Instruments.modifyInstrumentMusicValues(instrumentName, (float) baseOutput, (float) musicMaximum);
	}

}
