package org.btpos.dj2addons.impl.modrefs;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import pokefenn.totemic.api.music.MusicInstrument;

public class CTotemic {
	public static IForgeRegistry<?> getInstrumentRegistry() {
		return GameRegistry.findRegistry(MusicInstrument.class);
	}
	
	public static int getBaseOutput(Object i) {
		if (i instanceof MusicInstrument) {
			return ((MusicInstrument) i).getBaseOutput();
		}
		return -1;
	}
	
	public static int getMusicMaximum(Object i) {
		if (i instanceof MusicInstrument) {
			return ((MusicInstrument) i).getMusicMaximum();
		}
		return -1;
	}
}
