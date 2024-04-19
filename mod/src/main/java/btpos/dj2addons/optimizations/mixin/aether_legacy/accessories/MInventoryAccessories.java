package btpos.dj2addons.optimizations.mixin.aether_legacy.accessories;

import btpos.dj2addons.optimizations.mixinducks.aether_legacy.Duck_Accessories;
import com.gildedgames.the_aether.containers.inventory.InventoryAccessories;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("AddedMixinMembersNamePattern")
@Mixin(InventoryAccessories.class)
abstract class MInventoryAccessories implements IInventory, Duck_Accessories {
	
	@Shadow public abstract ItemStack getStackInSlot(int slotID);
	
	@Shadow public abstract ItemStack getStackFromItem(Item item);
	
	@Shadow public EntityPlayer player;
	
	@Shadow public abstract int breakItem(Item item);
	
	@Shadow public NonNullList<ItemStack> stacks;
	
	
	@Shadow public abstract int getSizeInventory();
	
	@Override
	public boolean wearingAccessory(Item item) {
		for(int index = 0, len = this.getSizeInventory(); index < len; ++index) {
			if (this.getStackInSlot(index).getItem() == item) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void damageWornStack(int damage, Item item) {
		ItemStack currentAccessory = this.getStackFromItem(item);
		if (currentAccessory != ItemStack.EMPTY && !this.player.capabilities.isCreativeMode) {
			currentAccessory.damageItem(1, this.player);
			if (currentAccessory.getItemDamage() >= currentAccessory.getMaxDamage()) {
				this.breakItem(currentAccessory.getItem());
			}
		}
	}
}
