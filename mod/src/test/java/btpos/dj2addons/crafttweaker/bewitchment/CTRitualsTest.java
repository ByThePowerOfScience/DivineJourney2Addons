package btpos.dj2addons.crafttweaker.bewitchment;

import btpos.dj2addons.DJ2ATest.Asserter;
import btpos.dj2addons.Test;
import btpos.dj2addons.api.bewitchment.Rituals;
import com.bewitchment.common.ritual.RitualBiomeShift;

class CTRitualsTest {
	//TODO verify altar upgrades
	@Test
	void removeRitual(Asserter a) {
		a.assertTrue(!Rituals.getAllRituals().contains(new RitualBiomeShift()));
	}
}