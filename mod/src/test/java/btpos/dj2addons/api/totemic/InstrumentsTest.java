package btpos.dj2addons.api.totemic;


import btpos.dj2addons.DJ2ATest;
import btpos.dj2addons.DJ2ATest.Asserter;
import btpos.dj2addons.Test;
import net.minecraft.util.ResourceLocation;
import pokefenn.totemic.api.TotemicRegistries;
import pokefenn.totemic.api.music.MusicInstrument;


class InstrumentsTest {
	
	@Test
	void putInstrumentModifications(Asserter a) {
		MusicInstrument value = TotemicRegistries.instruments()
		                                         .getValue(new ResourceLocation("totemic", "eagle_bone_whistle"));
		a.assertTrue(value.getBaseOutput() == 200);
		a.assertTrue(value.getMusicMaximum() == 200);
	}
}