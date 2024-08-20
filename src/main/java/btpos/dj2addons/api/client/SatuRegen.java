package btpos.dj2addons.api.client;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.entity.player.EntityPlayer;

import java.util.function.Predicate;

public class SatuRegen {
	public static void addHungerShankWaveActivator(Predicate<EntityPlayer> checker) {
		if (Internal.SINGLETON == null) { // if nothing has been registered, use the singleton
			Internal.SINGLETON = checker;
		} else { // use the list if we've got more than one thing
			if (Internal.SHOULD_ACTIVATE_REGEN_EFFECT == null) {
				Internal.SHOULD_ACTIVATE_REGEN_EFFECT = new ObjectOpenHashSet<>();
				Internal.SHOULD_ACTIVATE_REGEN_EFFECT.add(Internal.SINGLETON);
			}
			Internal.SHOULD_ACTIVATE_REGEN_EFFECT.add(checker);
		}
	}
	
	public static final class Internal {
		private static ObjectOpenHashSet<Predicate<EntityPlayer>> SHOULD_ACTIVATE_REGEN_EFFECT = null;
		private static Predicate<EntityPlayer> SINGLETON = null;
		
		public static boolean shouldActivateHungerShankWave(EntityPlayer player) {
			// just to avoid having to create Iterators for a single element cause idk if that's optimized by the JVM
			if (SHOULD_ACTIVATE_REGEN_EFFECT == null)
				return SINGLETON != null && SINGLETON.test(player);
			else {
				for (Predicate<EntityPlayer> entityPlayerPredicate : SHOULD_ACTIVATE_REGEN_EFFECT) {
					if (entityPlayerPredicate.test(player))
						return true;
				}
				return false;
			}
		}
	}
}
