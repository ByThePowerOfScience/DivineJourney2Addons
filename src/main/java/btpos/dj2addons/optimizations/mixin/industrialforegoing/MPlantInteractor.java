package btpos.dj2addons.optimizations.mixin.industrialforegoing;

import btpos.dj2addons.common.modrefs.CAgricraft;
import btpos.dj2addons.common.modrefs.IsModLoaded;
import com.buuz135.industrial.IndustrialForegoing;
import com.buuz135.industrial.proxy.BlockRegistry;
import com.buuz135.industrial.proxy.FluidsRegistry;
import com.buuz135.industrial.tile.WorkingAreaElectricMachine;
import com.buuz135.industrial.tile.agriculture.PlantInteractorTile;
import com.buuz135.industrial.utils.BlockUtils;
import com.buuz135.industrial.utils.ItemStackUtils;
import com.buuz135.industrial.utils.WorkUtils;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Iterator;
import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
@Mixin(value = PlantInteractorTile.class, remap = false)
public class MPlantInteractor extends WorkingAreaElectricMachine {
	@Shadow(remap = false)
	private ItemStackHandler outItems;
	
	@Shadow(remap = false)
	@Final
	public static List WORKING_TILES;
	
	@Shadow(remap = false)
	private boolean hasWorked;
	
	@Unique
	private static final IBlockState dj2addons$DUMMY = Blocks.AIR.getDefaultState();
	
	@Shadow
	private IFluidTank sludge;
	
	@Shadow
	private int pointer;
	
	protected MPlantInteractor(int typeId) {
		super(typeId);
	}
	
	/**
	 * @author
	 * @reason
	 */
	@Overwrite
	public float work() {
		if (WorkUtils.isDisabled(this.getBlockType())) {
			return 0.0F;
		} else if (ItemStackUtils.isInventoryFull(this.outItems)) {
			return 0.0F;
		} else {
			this.hasWorked = false;
			List<BlockPos> blockPos = BlockUtils.getBlockPosInAABB(this.getWorkingArea());
			if (this.pointer >= blockPos.size() / (BlockRegistry.plantInteractorBlock.getHeight() + 1)) {
				this.pointer = 0;
			}
			
			if (this.pointer < blockPos.size()) {
				BlockPos pointerPos = blockPos.get(this.pointer);
				
				for (int i = 0; i < BlockRegistry.plantInteractorBlock.getHeight() + 1; ++i) {
					BlockPos tempPos = new BlockPos(pointerPos.getX(), pointerPos.getY() + i, pointerPos.getZ());
					TileEntity te = world.getTileEntity(tempPos);
					//noinspection PointlessNullCheck
					if (te != null && IsModLoaded.agricraft && CAgricraft.isAgriHarvestable(te)) {
						CAgricraft.callOnHarvest(te, this::insertStack);
						continue;
					}
					IBlockState tempState = this.world.getBlockState(tempPos);
					if (tempState.getBlock() instanceof IPlantable || tempState.getBlock() instanceof IGrowable) {
						if (!BlockUtils.canBlockBeBroken(this.world, tempPos)) {
							continue;
						}
						FakePlayer player = IndustrialForegoing.getFakePlayer(this.world, tempPos.up());
						player.inventory.clear();
						WORKING_TILES.add(this);
						tempState.getBlock()
						         .onBlockActivated(this.world,
						                           tempPos,
						                           tempState,
						                           player,
						                           EnumHand.MAIN_HAND,
						                           EnumFacing.UP,
						                           0.0F,
						                           0.0F,
						                           0.0F);
						ForgeHooks.onRightClickBlock(player,
						                             EnumHand.MAIN_HAND,
						                             tempPos,
						                             EnumFacing.UP,
						                             new Vec3d(0.0, 0.0, 0.0));
						Iterator var7 = player.inventory.mainInventory.iterator();
						
						while (var7.hasNext()) {
							ItemStack stack = (ItemStack) var7.next();
							if (!stack.isEmpty()) {
								insertStack(stack);
							}
						}
						
						player.inventory.clear();
						WORKING_TILES.remove(this);
					}
					
				}
			}
			
			++this.pointer;
			return this.hasWorked ? 1.0F : 0.1F;
		}
	}
	
	/**
	 * Allows Plant Interactors to access the drops from AgriCraft crops directly without having to drop them on the ground.
	 * <p>Removes lag from FakePlayer usage and item drops.</p>
	 */
	/*@Redirect(
			remap = false,
			method = "work()F",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;"
			)
	)
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
	}*/
	
	/*@Unique
	private BlockPos dj2addons$pos;
	
	@Redirect(
			remap = false,
			method = "work()F",
			at = @At(
					value = "INVOKE",
					target = "Lcom/buuz135/industrial/utils/BlockUtils;canBlockBeBroken(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Z"
			)
	)
	private boolean dj2addons$moveCanBlockBeBrokenCheck_remove(World world, BlockPos pos) {
		this.dj2addons$pos = pos;
		return true;
	}*/
	
	
	
	
	@Unique
	private void insertStack (ItemStack stack){
		ItemHandlerHelper.insertItem(this.outItems, stack, false);
		this.sludge.fill(new FluidStack(FluidsRegistry.SLUDGE, 10 * stack.getCount()),
		                 true);
		this.hasWorked = true;
	}
}