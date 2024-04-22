package btpos.dj2addons.api.client;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Set;
import java.util.function.Predicate;

public class SatuRegen {
	public static void addHungerShankWaveActivator(Predicate<EntityPlayer> checker) {
		Internal.SHOULD_ACTIVATE_REGEN_EFFECT.add(checker);
		if (Internal.SHOULD_ACTIVATE_REGEN_EFFECT.size() == 1)
			Internal.SINGLETON = checker;
	}
	
	public static final class Internal {
		private static final ObjectOpenHashSet<Predicate<EntityPlayer>> SHOULD_ACTIVATE_REGEN_EFFECT = new ObjectOpenHashSet<>(1, 1.0f);
		private static Predicate<EntityPlayer> SINGLETON = null;
		
		public static boolean shouldActivateHungerShankWave(EntityPlayer player) {
			// just to avoid having to create Iterators for a single element
			if (SHOULD_ACTIVATE_REGEN_EFFECT.size() == 1)
				return SINGLETON.test(player);
			
			for (Predicate<EntityPlayer> entityPlayerPredicate : SHOULD_ACTIVATE_REGEN_EFFECT) {
				if (entityPlayerPredicate.test(player))
					return true;
			}
			return false;
		}
	}
}
