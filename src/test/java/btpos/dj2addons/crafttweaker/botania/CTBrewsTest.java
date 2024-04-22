package btpos.dj2addons.crafttweaker.botania;

import btpos.dj2addons.DJ2ATest.Asserter;
import btpos.dj2addons.Test;
import net.minecraft.block.state.IBlockState;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.state.BotaniaStateProps;
import vazkii.botania.common.block.ModBlocks;

class CTBrewsTest {
	final IBlockState poweredState = ModBlocks.brewery.getDefaultState().withProperty(BotaniaStateProps.POWERED, true);
	
	@Test
	void newBrew(Asserter a) {
		a.assertTrue(BotaniaAPI.getBrewFromKey("testingbrew") != null);
	}
	
	@Test
	void addStandardBrewRecipe(Asserter a) {
		a.assertTrue(a.blockAt(9, 73, -8) == poweredState);
	}
	
	@Test
	void addOutputRestrictedBrewRecipe(Asserter a) {
		a.assertTrue(a.blockAt(9, 73, -10) == poweredState);
		a.assertTrue(a.blockAt(9, 73, -9) != poweredState);
	}
	
	@Test
	void enableWarpWardPendant(Asserter a) {
		a.assertTrue(BotaniaAPI.brewMap.get("warpWard").canInfuseBloodPendant());
	}
}