package org.btpos.dj2addons.mixin.def.industrialforegoing;

import com.buuz135.industrial.tile.agriculture.PlantInteractorTile;
import com.infinityraider.agricraft.api.v1.misc.IAgriHarvestable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
@Mixin(PlantInteractorTile.class)
public class MPlantInteractor {
	@Shadow
	private ItemStackHandler outItems;
	
	
	@Shadow @Final
	public static List WORKING_TILES;
	
	private static final IBlockState DUMMY = Blocks.AIR.getDefaultState();
	
	@Redirect(method="work()F",  at=@At(value="INVOKE", target="Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;"))
	private IBlockState handleAgriCraftCrops(World world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof IAgriHarvestable) {
			WORKING_TILES.add(this);
			((IAgriHarvestable)te).onHarvest(stack -> ItemHandlerHelper.insertItem(outItems, stack, false), null);
			WORKING_TILES.remove(this);
			return DUMMY;
		} else {
			return world.getBlockState(pos);
		}
	}
}
