package btpos.dj2addons.crafttweaker.botania;

import btpos.dj2addons.DJ2ATest;
import btpos.dj2addons.Test;
import net.minecraft.block.state.IBlockState;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.state.BotaniaStateProps;
import vazkii.botania.common.block.ModBlocks;

class CTBrewsTest {
	final IBlockState poweredState = ModBlocks.brewery.getDefaultState().withProperty(BotaniaStateProps.POWERED, true);
	
	@Test
	void newBrew() {
		DJ2ATest.assertTrue(BotaniaAPI.getBrewFromKey("testingbrew") != null);
	}
	
	@Test
	void addStandardBrewRecipe() {
		DJ2ATest.assertTrue(DJ2ATest.blockAt(9, 73, -8) == poweredState);
	}
	
	@Test
	void addOutputRestrictedBrewRecipe() {
		DJ2ATest.assertTrue(DJ2ATest.blockAt(9, 73, -10) == poweredState);
		DJ2ATest.assertTrue(DJ2ATest.blockAt(9, 73, -9) != poweredState);
	}
	
	@Test
	void enableWarpWardPendant() {
		DJ2ATest.assertTrue(BotaniaAPI.brewMap.get("warpWard").canInfuseBloodPendant());
	}
}