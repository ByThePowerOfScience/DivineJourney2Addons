package btpos.dj2addons.api.bewitchment;

import com.bewitchment.Util;
import com.bewitchment.api.registry.Ritual;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

public class Rituals {
	public static void removeRitual(Ritual ritual) {
		Internal.ritualsToRemove.add(ritual);
	}
	
	public static List<Ritual> getAllRituals() {
		return ImmutableList.copyOf(com.bewitchment.registry.ModRecipes.ritualRecipes);
	}
	
	public static class Internal {
		
		private static final List<Ritual> ritualsToRemove = new ArrayList<>();
		
		public static List<Ritual> getRitualsToRemove() {
			return ritualsToRemove;
		}
		
		public static class DummyRitual extends Ritual {
			@Override
			public boolean isValid(World world, BlockPos altarPos, EntityPlayer caster, ItemStackHandler inventory) {
				return false;
			}
			
			public DummyRitual(String name) {
				super(Util.newResource(name), ImmutableList.of(), null, null, false, 0, 0, 0, 1, -1, -1);
			}
		}
	}
}
