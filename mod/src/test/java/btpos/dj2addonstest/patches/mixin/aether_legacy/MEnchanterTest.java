package btpos.dj2addonstest.patches.mixin.aether_legacy;

import com.gildedgames.the_aether.tile_entities.TileEntityEnchanter;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

//@RunWith(BarometerTester.class)
public class MEnchanterTest {
//	@Test
	public void noCheckContainerItem() {
		TileEntityEnchanter enchanter = new TileEntityEnchanter();
		IItemHandler itemHandler = enchanter.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
	}
}