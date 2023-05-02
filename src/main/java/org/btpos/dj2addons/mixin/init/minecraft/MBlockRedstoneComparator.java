package org.btpos.dj2addons.mixin.init.minecraft;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.btpos.dj2addons.util.Containers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockRedstoneComparator.class)
public class MBlockRedstoneComparator extends MBlockRedstoneDiode {
	@Redirect(
			method="calculateInputStrength",
			at=@At(
					target="Lnet/minecraft/block/BlockRedstoneDiode;calculateInputStrength(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)I",
					value="INVOKE"
			)
	)
	private int dj2addons$getInputStrengthFromInventory(BlockRedstoneDiode x, World worldIn, BlockPos pos, IBlockState currentState) {
		int i = super.calculateInputStrength(worldIn, pos, currentState);
		BlockPos facingPos = pos.offset(currentState.getValue(BlockHorizontal.FACING));
		IBlockState facingState = worldIn.getBlockState(facingPos);
		if (facingState.hasComparatorInputOverride())
			return i;
		
		TileEntity te = worldIn.getTileEntity(facingPos);
		if (te != null) {
			IItemHandler inv = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			if (inv != null) {
				i = Containers.calcRedstoneFromInventory(inv);
			}
		}
		return i;
	}
}
