package btpos.dj2addons.api.totemic;



import btpos.dj2addons.DJ2ATest;
import btpos.dj2addons.Test;
import net.minecraft.util.ResourceLocation;
import pokefenn.totemic.api.TotemicRegistries;
import pokefenn.totemic.api.music.MusicInstrument;


class InstrumentsTest {
	
	@Test
	void putInstrumentModifications() {
		MusicInstrument value = TotemicRegistries.instruments()
		                                         .getValue(new ResourceLocation("totemic", "eagle_bone_whistle"));
		DJ2ATest.assertTrue(value.getBaseOutput() == 200);
		DJ2ATest.assertTrue(value.getMusicMaximum() == 200);
	}
}