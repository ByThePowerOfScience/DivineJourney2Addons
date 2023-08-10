package btpos.dj2addons.api.crafttweaker.astralsorcery;

import btpos.dj2addons.DJ2ATest;
import hellfirepvp.astralsorcery.common.lib.BlocksAS;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

class CTCelestialCrystalsTest {
	private static final BlockPos starmetalPos = new BlockPos(8,68,-10);
	private static final BlockPos crystalPos = new BlockPos(8,68,-8);
	
	void setStarmetalConversion() {
		DJ2ATest.assertBlock(starmetalPos, BlocksAS.customOre);
	}
	
	void scaleGrowthTime() {
		IBlockState state = DJ2ATest.blockAt(crystalPos);
		DJ2ATest.assertTrue(state.getBlock().getMetaFromState(state) > 0);
	}
}