package btpos.dj2addons.common.modrefs;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.block.tile.entity.TileEntityPlacedItem;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Map;
import java.util.function.Predicate;

public class CBewitchment {
	public static class DummyPlacedItem extends TileEntityPlacedItem {
		private final ItemStackHandler ish;
		public DummyPlacedItem(ItemStack is) {
			this.ish = new ItemStackHandler(1);
			ish.insertItem(0, is, false);
		}
		
		@Override
		public ItemStackHandler[] getInventories() {
			return new ItemStackHandler[] {ish};
		}
	}
	
	public static Map<Predicate<BlockWorldState>, ?> getAltarUpgrades() {
		return BewitchmentAPI.ALTAR_UPGRADES;
	}
	
	@SuppressWarnings("ConstantConditions")
	public static class DummyPlacedItemWorldState extends BlockWorldState {
		private final ItemStack is;
		
		public DummyPlacedItemWorldState(ItemStack is) {
			super(null, null, false);
			this.is = is;
		}
		
		@Override
		public BlockPos getPos() {
			throw new IllegalStateException();
		}
		
		@Override
		public IBlockState getBlockState() {
			return ModObjects.placed_item.getDefaultState();
		}
		
		@Override
		public TileEntity getTileEntity() {
			return new DummyPlacedItem(is);
		}
	}
	
	@SuppressWarnings("ConstantConditions")
	public static class DummyBlockWorldState extends BlockWorldState {
		private final IBlockState state;
		
		public DummyBlockWorldState(IBlockState state) {
			super(null, null, false);
			this.state = state;
		}
		
		@Override
		public BlockPos getPos() {
			throw new IllegalStateException();
		}
		
		@Override
		public IBlockState getBlockState() {
			return state;
		}
		
		@Override
		public TileEntity getTileEntity() {
			return null;
		}
	}
}
