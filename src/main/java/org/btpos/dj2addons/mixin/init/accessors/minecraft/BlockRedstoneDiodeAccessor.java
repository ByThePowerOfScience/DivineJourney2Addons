package org.btpos.dj2addons.mixin.init.accessors.minecraft;

import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockRedstoneDiode.class)
public interface BlockRedstoneDiodeAccessor {
	@Invoker
	int callCalculateInputStrength(World worldIn, BlockPos pos, IBlockState state);
}
