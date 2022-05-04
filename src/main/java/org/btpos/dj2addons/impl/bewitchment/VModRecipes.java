package org.btpos.dj2addons.impl.bewitchment;

import com.bewitchment.Util;
import com.bewitchment.api.registry.Ritual;
import com.google.common.collect.ImmutableList;
import org.apache.logging.log4j.Level;
import org.btpos.dj2addons.DJ2Addons;

import java.util.ArrayList;
import java.util.List;

public class VModRecipes {
	private static final List<Ritual> ritualsToRemove = new ArrayList<>();
	
	public static void removeRitual(Ritual ritual) {
		ritualsToRemove.add(ritual);
	}
	
	public static List<Ritual> getRitualsToRemove() {
		return ritualsToRemove;
	}
	
	public static class DummyRitual extends Ritual {
		public DummyRitual(String name) {
			super(Util.newResource(name), ImmutableList.of(), null, null, false, 0, 0, 0, 1, -1, -1);
			DJ2Addons.LOGGER.log(Level.DEBUG, "Dummy ritual name: " + Util.newResource(name).toString());
		}
	}
}
