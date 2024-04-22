package btpos.dj2addons.common.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class Containers {
	public static int calcRedstoneFromInventory(@Nullable IItemHandler inv) {
		if (inv == null)
		{
			return 0;
		}
		else
		{
			int i = 0;
			float f = 0.0F;
			
			for (int j = 0; j < inv.getSlots(); ++j)
			{
				ItemStack itemstack = inv.getStackInSlot(j);
				
				if (!itemstack.isEmpty())
				{
					f += (float)itemstack.getCount() / (float)Math.min(inv.getSlotLimit(j), itemstack.getMaxStackSize());
					++i;
				}
			}
			
			f = f / (float)inv.getSlots();
			return MathHelper.floor(f * 14.0F) + (i > 0 ? 1 : 0);
		}
	}
}
