package btpos.dj2addons.common.modrefs;

import com.infinityraider.agricraft.api.v1.misc.IAgriHarvestable;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.function.Consumer;

public class CAgricraft {
	public static boolean isAgriHarvestable(TileEntity te) {
		return te instanceof IAgriHarvestable;
	}
	
	public static void callOnHarvest(TileEntity te, Consumer<ItemStack> consumer) {
		((IAgriHarvestable)te).onHarvest(consumer, null);
	}
}
