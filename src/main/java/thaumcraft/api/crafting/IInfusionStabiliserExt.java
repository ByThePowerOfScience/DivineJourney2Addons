// Thaumcraft © Azanor13, All Rights Reserved
// TODO manually classload these specific classes early so as not to violate license

package thaumcraft.api.crafting;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IInfusionStabiliserExt extends IInfusionStabiliser {
	float getStabilizationAmount(World var1, BlockPos var2);
	
	default boolean hasSymmetryPenalty(World world, BlockPos pos1, BlockPos pos2) {
		return false;
	}
	
	default float getSymmetryPenalty(World world, BlockPos pos) {
		return 0.0F;
	}
}