package btpos.dj2addonstest.patches.mixin.bloodmagic;

import WayofTime.bloodmagic.tile.TileSoulForge;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MSoulForgeTest {
	@Test
	void isItemValidHandler() {
		TileEntity te = new TileSoulForge();
		IItemHandler itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
		                                            null);
		assertNotNull(itemHandler);
		
		itemHandler.insertItem(TileSoulForge.outputSlot, new ItemStack(Blocks.DIRT), false);
		
		assertTrue(itemHandler.getStackInSlot(TileSoulForge.outputSlot).isEmpty());
	}
}