package btpos.dj2addons.api.minecraft;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.block.state.IBlockState;

import java.util.Collections;
import java.util.Set;

public final class ItemFrames {
	/**
	 * Prevent item frames from being placed on the provided block.
	 */
	public static void disallowPlaceOn(IBlockState block) {
		Internal.set.add(block);
	}
	
	/**
	 * Internal backend implementation stuff.
	 */
	public static final class Internal {
		private static final Set<IBlockState> set = new ObjectOpenHashSet<>();
		
		public static boolean isDisallowed(IBlockState state) {
			return set.contains(state);
		}
		
		public static Set<IBlockState> getDisallowed() {
			return Collections.unmodifiableSet(set);
		}
		
		private Internal() {}
	}
	
	private ItemFrames() {}
}
