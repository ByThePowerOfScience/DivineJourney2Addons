package btpos.dj2addons.api.bewitchment;

import com.bewitchment.Util;
import com.bewitchment.api.registry.Ritual;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

public final class Rituals {
	
	/**
	 * Disables a Bewitchment ritual by name.
	 * <p>Example: {@code removeRitual("biome_shift")}
	 * @param name The name of the ritual to remove. Will be prefixed with "bewitchment:".
	 * @apiNote This works by overriding the {@code Ritual#equals()} method to return true if the names match, so any Mixin that also implements that method for {@code Ritual} will break this API.
	 */
	public static void removeRitual(String name) {
		Internal.ritualsToRemove.add(new Internal.DummyRitual(Util.newResource(name)));
	}
	
	/**
	 * Disables a third-party ritual by namespaced name.
	 * <p>Example: {@code removeRitual("dj2addons:my_custom_ritual")}
	 * @param name The name of the ritual to remove.
	 * @param namespace The namespace the ritual is registered under.
	 * @apiNote This works by overriding the {@code Ritual#equals()} method to match names, so any Mixin that also implements that method for {@code Ritual} will break this API.
	 */
	public static void removeRitual(String namespace, String name) {
		Internal.ritualsToRemove.add(new Internal.DummyRitual(new ResourceLocation(namespace, name)));
	}
	
	
	/**
	 * Exposes {@link com.bewitchment.registry.ModRecipes#ritualRecipes bewitchment.ModRecipes#ritualRecipes} for the CraftTweaker command.
	 */
	public static List<Ritual> getAllRituals() {
		return ImmutableList.copyOf(com.bewitchment.registry.ModRecipes.ritualRecipes);
	}
	
	/**
	 * Internal members for use by the Mixin that implements this API.
	 */
	@Deprecated
	public static final class Internal {
		
		private static List<Ritual> ritualsToRemove = new ArrayList<>();
		
		/**
		 * For Mixin use only. Nullifies the list after being called to prevent a memory leak, so don't call this!!!
		 */
		public static List<Ritual> getRitualsToRemove() {
			List<Ritual> ret = ritualsToRemove;
			ritualsToRemove = null;
			return ret;
		}
		
		public static class DummyRitual extends Ritual {
			@Override
			public boolean isValid(World world, BlockPos altarPos, EntityPlayer caster, ItemStackHandler inventory) {
				return false;
			}
			
			public DummyRitual(ResourceLocation rl) {
				super(rl, ImmutableList.of(), null, null, false, 0, 0, 0, 1, -1, -1);
			}
		}
	}
}
