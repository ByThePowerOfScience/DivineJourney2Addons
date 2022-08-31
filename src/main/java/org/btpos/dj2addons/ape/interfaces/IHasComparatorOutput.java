package org.btpos.dj2addons.ape.interfaces;

/**
 * Blocks that want to be detected by {@link net.minecraft.block.BlockRedstoneComparator Comparators} should implement this class.
 */
public interface IHasComparatorOutput {
	/**
	 *
	 * @return The redstone signal strength that the comparator checking this block should emit.
	 */
	int getRedstoneSignalStrength();
}
