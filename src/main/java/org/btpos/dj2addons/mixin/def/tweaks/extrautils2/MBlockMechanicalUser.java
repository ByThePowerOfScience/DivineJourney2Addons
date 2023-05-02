package org.btpos.dj2addons.mixin.def.tweaks.extrautils2;

import com.rwtema.extrautils2.blocks.BlockAdvInteractor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import org.btpos.dj2addons.util.Containers;
import org.spongepowered.asm.mixin.Mixin;

@SuppressWarnings("unused")
@Mixin(BlockAdvInteractor.Use.class)
public abstract class MBlockMechanicalUser extends BlockAdvInteractor {
	public MBlockMechanicalUser(String texture) {
		super(texture);
	}
	
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}
	
	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
	{
		TileEntity te = worldIn.getTileEntity(pos);
		if (te == null)
			return 0;
		
		return Containers.calcRedstoneFromInventory(te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null));
	}
}
