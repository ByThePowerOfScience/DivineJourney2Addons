package btpos.dj2addons.api.minecraft;

import net.minecraft.block.state.IBlockState;

import java.util.HashSet;
import java.util.Set;

public class ItemFrames {
	public static void disallowPlaceOn(IBlockState b) {
		Internal.set.add(b);
	}
	
	public static class Internal {
		private static final Set<IBlockState> set = new HashSet<>(2);
		
		public static Set<IBlockState> getDisallowed() {
			return set;
		}
	}
}
