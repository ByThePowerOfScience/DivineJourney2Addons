package org.btpos.dj2addons.impl.api.bewitchment;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.block.tile.entity.TileEntityPlacedItem;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashSet;
import java.util.Set;

public class VAltarUpgrades {
	public static final Set<ItemStack> toRemove = new HashSet<>();
	
	public static void removeUpgrade(String oreDict) {
		OreDictionary.getOres(oreDict).forEach(VAltarUpgrades::removeUpgrade);
	}
	
	public static void removeUpgrade(Item item) {
		removeUpgrade(new ItemStack(item, 1, item.isDamageable() ? Short.MAX_VALUE : 0));
	}
	
	public static void removeUpgrade(ItemStack is) {
		toRemove.add(is);
	}
	
	public static void executeUpgradeRemoval() {
		toRemove.stream()
		        .map(DummyWorldState::new)
		        .forEach(dws -> BewitchmentAPI.ALTAR_UPGRADES.keySet().removeIf(p -> p.test(dws)));
	}
	
	@SuppressWarnings("all")
	private static class DummyWorldState extends BlockWorldState {
		private final ItemStack is;
		
		public DummyWorldState(ItemStack is) {
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
		
		private static class DummyPlacedItem extends TileEntityPlacedItem {
			private final ItemStackHandler ish;
			DummyPlacedItem(ItemStack is) {
				this.ish = new ItemStackHandler(1);
				ish.insertItem(0, is, false);
			}
			
			@Override
			public ItemStackHandler[] getInventories() {
				return new ItemStackHandler[] {ish};
			}
		}
	}
}
