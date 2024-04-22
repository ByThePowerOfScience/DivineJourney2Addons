package btpos.dj2addons.patches.mixin.bloodmagic;

import WayofTime.bloodmagic.tile.TileSoulForge;
import btpos.dj2addons.DJ2ATest;
import btpos.dj2addons.DJ2ATest.Asserter;
import btpos.dj2addons.Test;
import btpos.dj2addons.crafttweaker.bloodmagic.CTSoulForgeTest;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.IItemHandler;

import java.util.Objects;


class MSoulForgeTest {
	static BlockPos pos = new BlockPos(8, 68, -4);
	
	@Test
	void isItemValidHandler(Asserter a) {
		IItemHandler iItemHandler = CTSoulForgeTest.getiItemHandler(Objects.requireNonNull(a.world.getTileEntity(pos)));
		
		a.assertTrue(iItemHandler.getStackInSlot(TileSoulForge.outputSlot) == ItemStack.EMPTY);
	}
}