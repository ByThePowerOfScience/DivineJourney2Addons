package org.btpos.dj2addons.impl.industrialforegoing;

import com.infinityraider.agricraft.api.v1.misc.IAgriHarvestable;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.Loader;

public class VPlantInteractor {
	public static boolean isAgricraftLoaded = Loader.isModLoaded("agricraft");
	
	public static boolean isAgriHarvestable(TileEntity te) {
		return te instanceof IAgriHarvestable;
	}
	
	public static IAgriHarvestable asAgriHarvestable(TileEntity te) {
		return (IAgriHarvestable)te;
	}
}
