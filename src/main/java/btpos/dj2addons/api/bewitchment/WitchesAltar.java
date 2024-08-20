package btpos.dj2addons.api.bewitchment;

import btpos.dj2addons.api.bewitchment.WitchesAltar.Internal.DummyBlockWorldState;
import btpos.dj2addons.api.bewitchment.WitchesAltar.Internal.DummyPlacedItemWorldState;
import btpos.dj2addons.common.modrefs.CBewitchment;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.registry.AltarUpgrade;
import com.bewitchment.common.block.tile.entity.TileEntityPlacedItem;
import com.bewitchment.registry.ModObjects;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Set;

/**
 * Handles things relating to the Witches' Altar, such as upgrades.
 * @see btpos.dj2addons.crafttweaker.bewitchment.CTWitchesAltar CraftTweaker API
 */
public class WitchesAltar {
	
	/**
	 * Registers a Block as a valid upgrade to the Witches' Altar..
	 * @param block The block to add.
	 * @param upgrade The AltarUpgrade that {@code block} should have when placed on a Witches' Altar.
	 */
	public static void addAltarUpgradeBlock(IBlockState block, AltarUpgrade upgrade) {
		BewitchmentAPI.ALTAR_UPGRADES.put(blockWorldState -> blockWorldState.getBlockState() == block, upgrade);
	}
	
	/**
	 * Makes all items corresponding to the given OreDictionary tag no longer have an effect when placed on a Witches' Altar.
	 * @param oreDict The OreDictionary tag containing the items to invalidate as upgrades.
	 */
	public static void removeUpgrade(String oreDict) {
		OreDictionary.getOres(oreDict)
		             .forEach(WitchesAltar::removeUpgrade);
	}
	
	/**
	 * Makes an item no longer have an effect when placed on a Witches' Altar.
	 * @param item The item to invalidate as an upgrade.
	 */
	public static void removeUpgrade(Item item) {
		removeUpgrade(new ItemStack(item, 1, item.isDamageable() ? Short.MAX_VALUE : 0));
	}
	
	/**
	 * Makes a block no longer have an effect when placed on a Witches' Altar.
	 * @param block The block to invalidate as an upgrade.
	 */
	public static void removeUpgrade(IBlockState block) {
		Internal.toRemove.add(new DummyBlockWorldState(block));
	}
	
	/**
	 * Makes an item no longer have an effect when placed on a Witches' Altar.
	 * @param is The itemstack containing the item to invalidate as an upgrade.
	 */
	public static void removeUpgrade(ItemStack is) {
		Internal.toRemove.add(new DummyPlacedItemWorldState(is));
	}
	
	/**
	 * Internal implementation for this API.
	 */
	public static class Internal {
		private static Set<BlockWorldState> toRemove = new ObjectOpenHashSet<>();
		
		/**
		 * Nullifies the set after being called to prevent a memory leak, so DON'T CALL THIS.
		 */
		public static void executeUpgradeRemoval() {
			toRemove.forEach(bws -> CBewitchment.getAltarUpgrades().keySet().removeIf(p -> p.test(bws)));
			toRemove = null;
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
		
		public static class DummyPlacedItem extends TileEntityPlacedItem {
			private final ItemStackHandler handler;
			public DummyPlacedItem(ItemStack is) {
				this.handler = new ItemStackHandler(1);
				handler.insertItem(0, is, false);
			}
			
			@Override
			public ItemStackHandler[] getInventories() {
				return new ItemStackHandler[] {handler};
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
	
}
