package org.btpos.dj2addons.impl.bewitchment;

import com.bewitchment.Util;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.registry.ModRecipes;
import com.google.common.collect.ImmutableList;

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
	
	public static List<Ritual> getAllRituals() {
		return ImmutableList.copyOf(ModRecipes.ritualRecipes);
	}
	
	public static class DummyRitual extends Ritual {
		public DummyRitual(String name) {
			super(Util.newResource(name), ImmutableList.of(), null, null, false, 0, 0, 0, 1, -1, -1);
		}
	}
}
