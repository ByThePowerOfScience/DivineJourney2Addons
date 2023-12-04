package btpos.dj2addons.optimizations.mixin.industrialforegoing;

import btpos.dj2addons.common.modrefs.CAgricraft;
import btpos.dj2addons.common.modrefs.IsModLoaded;
import com.buuz135.industrial.proxy.FluidsRegistry;
import com.buuz135.industrial.tile.agriculture.PlantInteractorTile;
import com.buuz135.industrial.utils.BlockUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
@Mixin(value=PlantInteractorTile.class, remap=false)
public class MPlantInteractor {
	@Shadow(remap=false)
	private ItemStackHandler outItems;
	
	@Shadow(remap=false) @Final
	public static List WORKING_TILES;
	
	@Shadow(remap=false) private boolean hasWorked;
	
	@Unique
	private static final IBlockState dj2addons$DUMMY = Blocks.AIR.getDefaultState();
	
	@Shadow private IFluidTank sludge;
	
	/**
	 * Allows Plant Interactors to access the drops from AgriCraft crops directly without having to drop them on the ground.
	 * <p>Removes lag from FakePlayer usage and item drops.</p>
	 */
	@Redirect(
			remap=false,
			method="work()F",
			at=@At(
					value="INVOKE",
					target="Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;"))
	private IBlockState handleAgriCraftCrops(World world, BlockPos pos) {
		if (!IsModLoaded.agricraft)
			return world.getBlockState(pos);
		
		TileEntity te = world.getTileEntity(pos);
		if (CAgricraft.isAgriHarvestable(te)) {
			WORKING_TILES.add(this);
			CAgricraft.callOnHarvest(te, stack -> {
				ItemHandlerHelper.insertItem(outItems, stack, false);
				this.sludge.fill(new FluidStack(FluidsRegistry.SLUDGE, 10 * stack.getCount()), true);
				this.hasWorked = true;
			});
			WORKING_TILES.remove(this);
			return dj2addons$DUMMY;
		} else {
			return world.getBlockState(pos);
		}
	}
	
	@Unique private BlockPos dj2addons$pos;
	
	/**
	 * Moves the canBlockBeBroken check to after we determine it's a valid block so we aren't checking every single block.
	 */
	@Redirect(
			remap=false,
			method="work()F",
			at=@At(
					value="INVOKE",
					target="Lcom/buuz135/industrial/utils/BlockUtils;canBlockBeBroken(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Z"))
	private boolean dj2addons$moveCanBlockBeBrokenCheck_remove(World world, BlockPos pos) {
		this.dj2addons$pos = pos;
		return true;
	}
	
	/**
	 * Moves the canBlockBeBroken check to after we determine it's a valid block so we aren't checking every single block.
	 */
	@WrapOperation(
			remap=false,
			method="work()F",
			constant={@Constant(classValue=IGrowable.class), @Constant(classValue=IPlantable.class)}
	)
	private boolean dj2addons$moveCanBlockBeBrokenCheck_insert(Object o, Operation<Boolean> operation) {
			return operation.call(o) && BlockUtils.canBlockBeBroken(((PlantInteractorTile)(Object)this).getWorld(), dj2addons$pos);
	}
	
}
