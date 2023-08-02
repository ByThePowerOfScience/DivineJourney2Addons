package btpos.dj2addons.patches.mixin.industrialforegoing;

import com.buuz135.industrial.tile.agriculture.PlantInteractorTile;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import btpos.dj2addons.common.modrefs.IsModLoaded;
import btpos.dj2addons.common.modrefs.CAgricraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
@Mixin(PlantInteractorTile.class)
public class MPlantInteractor {
	@Shadow(remap=false)
	private ItemStackHandler outItems;
	
	@Shadow(remap=false) @Final
	public static List WORKING_TILES;
	
	private static final IBlockState DUMMY = Blocks.AIR.getDefaultState();
	
	/**
	 * Allows Plant Interactors to access the drops from AgriCraft crops directly without having to drop them on the ground.
	 * <p>Removes lag from FakePlayer usage and item drops.</p>
	 */
	@Redirect(remap=false, method="work()F", at=@At(value="INVOKE", target="Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;"))
	private IBlockState handleAgriCraftCrops(World world, BlockPos pos) {
		if (!IsModLoaded.agricraft)
			return world.getBlockState(pos);
		
		TileEntity te = world.getTileEntity(pos);
		if (CAgricraft.isAgriHarvestable(te)) {
			WORKING_TILES.add(this);
			(CAgricraft.asAgriHarvestable(te)).onHarvest(stack -> ItemHandlerHelper.insertItem(outItems, stack, false), null);
			WORKING_TILES.remove(this);
			return DUMMY;
		} else {
			return world.getBlockState(pos);
		}
	}
}
