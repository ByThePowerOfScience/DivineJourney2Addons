package org.btpos.dj2addons.impl.modrefs;

import fr.frinn.modularmagic.common.tile.TileAspectProvider;
import net.minecraft.tileentity.TileEntity;

public class CModularMagic {
	public static boolean isTileAspectProvider(TileEntity te) {
		return te instanceof TileAspectProvider;
	}
}
