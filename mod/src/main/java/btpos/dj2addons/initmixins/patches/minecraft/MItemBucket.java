package btpos.dj2addons.initmixins.patches.minecraft;

import btpos.dj2addons.common.modrefs.CAetherLegacy;
import btpos.dj2addons.common.modrefs.IsModLoaded;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBucket;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemBucket.class)
abstract class MItemBucket {
	@Shadow(aliases="containedBlock")
	@Final
	private Block containedBlock;
	
	@WrapOperation(
			method = "tryPlaceContainedLiquid(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Z",
			at = @At(
					target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/SoundEvent;Lnet/minecraft/util/SoundCategory;FF)V",
					value = "INVOKE",
					ordinal = 1
			)
	)
	private void changeLavaSound(World instance, EntityPlayer player, BlockPos pos, SoundEvent soundIn, SoundCategory category, float volume, float pitch, Operation<Void> original) {
		if (Blocks.LAVA == this.containedBlock && IsModLoaded.aether_legacy && instance.provider.getDimension() == CAetherLegacy.getDimensionId()) {
			CAetherLegacy.playFizzleSound(instance, player, pos);
		} else {
			original.call(instance, player, pos, soundIn, SoundCategory.BLOCKS, volume, pitch);
		}
	}
	
	
	@WrapOperation(
			method = "tryPlaceContainedLiquid(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Z",
			at = @At(
					target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z",
					value = "INVOKE"
			)
	)
	private boolean changeLavaPlaceBehavior(World world, BlockPos blockSnapshot, IBlockState oldState, int oldLight, Operation<Boolean> original) {
		if (Blocks.LAVA == this.containedBlock && IsModLoaded.aether_legacy && world.provider.getDimension() == CAetherLegacy.getDimensionId()) {
			return original.call(world, blockSnapshot, CAetherLegacy.getAerogelBlock().getDefaultState(), 11);
		} else {
			return original.call(world, blockSnapshot, this.containedBlock.getDefaultState(), oldLight);
		}
	}
}
