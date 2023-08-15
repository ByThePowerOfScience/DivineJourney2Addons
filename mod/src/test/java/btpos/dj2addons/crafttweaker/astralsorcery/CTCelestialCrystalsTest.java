package btpos.dj2addons.crafttweaker.astralsorcery;

import btpos.dj2addons.DJ2ATest;
import btpos.dj2addons.Test;
import hellfirepvp.astralsorcery.common.lib.BlocksAS;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

class CTCelestialCrystalsTest {
	private static final BlockPos starmetalPos = new BlockPos(8,68,-10);
	private static final BlockPos crystalPos = new BlockPos(8,68,-8);
	
	@Test
	void setStarmetalConversion() {
		DJ2ATest.assertBlock(starmetalPos, BlocksAS.customOre);
	}
	
	@Test
	void scaleGrowthTime() {
		IBlockState state = DJ2ATest.blockAt(crystalPos);
		DJ2ATest.assertTrue(state.getBlock().getMetaFromState(state) > 0);
	}
}