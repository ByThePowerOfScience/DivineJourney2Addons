package btpos.dj2addons.api.astralsorcery;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class AstralSorcery {
	public static void setStarmetalConversion(IBlockState blockState) {
		Internal.starmetalConversion = blockState;
	}
	
	public static void setCelestialCrystalGrowthScale(double celestialCrystalGrowthScale) {
		Internal.celestialCrystalGrowthScale = celestialCrystalGrowthScale;
	}
	
	public static class Internal {
		public static IBlockState starmetalConversion = Blocks.IRON_ORE.getDefaultState();
		public static double celestialCrystalGrowthScale = 1.0D;
	}
}
