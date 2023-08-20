package btpos.dj2addons.crafttweaker.bloodmagic;


import WayofTime.bloodmagic.core.RegistrarBloodMagicItems;
import WayofTime.bloodmagic.tile.TileSoulForge;
import btpos.dj2addons.DJ2ATest;
import btpos.dj2addons.Test;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class CTSoulForgeTest {
	
	static final BlockPos willTypeForge = new BlockPos(8, 68, -6);
	static final BlockPos transferRateForge = new BlockPos(8,68,-2);
	
	@Test
	void setCraftingTicksRequired() {
		TileEntity te = DJ2ATest.world.getTileEntity(willTypeForge);
		IItemHandler ish = getiItemHandler(Objects.requireNonNull(te));
		DJ2ATest.assertTrue(ish.getStackInSlot(TileSoulForge.outputSlot).getItem() == RegistrarBloodMagicItems.SOUL_GEM);
	}
	
	@NotNull
	public static IItemHandler getiItemHandler(TileEntity te) {
		DJ2ATest.assertTrue(te instanceof TileSoulForge);
		IItemHandler ish = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
		assert(ish != null);
		return ish;
	}
	
	@Test
	void setWorldWillTransferRate() {
		IItemHandler ish = getiItemHandler(Objects.requireNonNull(DJ2ATest.world.getTileEntity(transferRateForge)));
		DJ2ATest.assertTrue(Objects.requireNonNull(ish.getStackInSlot(TileSoulForge.soulSlot).getTagCompound()).getDouble("souls") == 16384);
	}
	
	@Test
	void setCraftWithAllWillTypes() {
		TileEntity te = DJ2ATest.world.getTileEntity(willTypeForge);
		IItemHandler ish = getiItemHandler(Objects.requireNonNull(te));
		DJ2ATest.assertTrue(ish.getStackInSlot(TileSoulForge.outputSlot).getItem() == RegistrarBloodMagicItems.SOUL_GEM);
	}
}