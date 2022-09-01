package org.btpos.dj2addons.impl.modrefs;

import com.infinityraider.agricraft.api.v1.misc.IAgriHarvestable;
import net.minecraft.tileentity.TileEntity;

public class CAgricraft {
	public static boolean isAgriHarvestable(TileEntity te) {
		return te instanceof IAgriHarvestable;
	}
	
	public static IAgriHarvestable asAgriHarvestable(TileEntity te) {
		return (IAgriHarvestable)te;
	}
}
