package org.btpos.dj2addons.impl.aether;

import com.gildedgames.the_aether.AetherConfig;
import com.gildedgames.the_aether.blocks.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import org.jetbrains.annotations.Nullable;

public class AetherValues {
	public static boolean aetherIsLoaded = Loader.isModLoaded("aether_legacy");
	
	public static int getDimensionId() {
		return AetherConfig.dimension.aether_dimension_id;
	}
	public static Block getAerogelBlock() {
		return BlocksAether.aerogel;
	}
	
	public static void playFizzleSound(World world, @Nullable EntityPlayer player, BlockPos pos) {
		world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
	}
	
	public static void playFizzleSound(World world, BlockPos pos) {
		playFizzleSound(world, null, pos);
	}
}
