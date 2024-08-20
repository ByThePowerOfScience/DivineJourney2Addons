// Thaumcraft © Azanor13, All Rights Reserved
// TODO manually classload these specific classes early so as not to violate license

package thaumcraft.api.crafting;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** @deprecated */
@Deprecated
public interface IInfusionStabiliser {
	boolean canStabaliseInfusion(World world, BlockPos pos);
}