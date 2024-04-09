package btpos.dj2addons.api.minecraft;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.block.state.IBlockState;

import java.util.Set;

public final class ItemFrames {
	public static void disallowPlaceOn(IBlockState b) {
		Internal.set.add(b);
	}
	
	public static class Internal {
		private static final Set<IBlockState> set = new ObjectOpenHashSet<>();
		
		public static Set<IBlockState> getDisallowed() {
			return set;
		}
	}
}
