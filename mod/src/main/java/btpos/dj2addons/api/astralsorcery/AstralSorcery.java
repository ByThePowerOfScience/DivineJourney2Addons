package btpos.dj2addons.api.astralsorcery;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

/**
 * Additional APIs for Astral Sorcery.
 * @see btpos.dj2addons.crafttweaker.astralsorcery.CTCelestialCrystals CraftTweaker hooks
 */
public final class AstralSorcery {
	/**
	 * Sets the block that Starmetal Ore should be converted to when a Celestial Crystal grows on top of it.
	 */
	public static void setStarmetalConversion(IBlockState blockState) {
		Internal.starmetalConversion = blockState;
	}
	
	
	/**
	 * Sets the rate celestial crystals should grow at. Default is {@code 1.0}.
	 */
	public static void setCelestialCrystalGrowthScale(double celestialCrystalGrowthScale) {
		Internal.celestialCrystalGrowthScale = celestialCrystalGrowthScale;
	}
	
	/**
	 * Internal values for use by Mixins only.
	 */
	public static final class Internal {
		private static IBlockState starmetalConversion = Blocks.IRON_ORE.getDefaultState();
		
		private static double celestialCrystalGrowthScale = 1.0D;
		
		/**
		 * For use by Mixins to retrieve value. {@link #setStarmetalConversion}
		 */
		public static IBlockState getStarmetalConversion() {
			return starmetalConversion;
		}
		
		/**
		 * DO NOT CHANGE MANUALLY. Use {@link #setCelestialCrystalGrowthScale}
		 */
		public static double getCelestialCrystalGrowthScale() {
			return celestialCrystalGrowthScale;
		}
	}
}
