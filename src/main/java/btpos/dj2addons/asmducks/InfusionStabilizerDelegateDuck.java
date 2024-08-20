package btpos.dj2addons.asmducks;

import btpos.dj2addons.api.thaumcraft.InfusionStabilizers;
import btpos.dj2addons.core.CoreInfo;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.api.crafting.IInfusionStabiliserExt;

@SuppressWarnings("unused")
public interface InfusionStabilizerDelegateDuck extends IInfusionStabiliserExt {
	default void addToList() {
		CoreInfo.objectsToSetLogicFor.add(this);
	}
	
	default void retrieveLogic() {
		setLogicDelegate(InfusionStabilizers.Internal.getLogic(this.getClass()));
	}
	
	void setLogicDelegate(IInfusionStabiliserExt logic);
	
	IInfusionStabiliserExt getDelegate();
	
	
	
	@Override
	default float getStabilizationAmount(World var1, BlockPos var2) {
		return getDelegate().getStabilizationAmount(var1, var2);
	}
	
	@Override
	default boolean hasSymmetryPenalty(World world, BlockPos pos1, BlockPos pos2) {
		return getDelegate().hasSymmetryPenalty(world, pos1, pos2);
	}
	
	@Override
	default float getSymmetryPenalty(World world, BlockPos pos) {
		return getDelegate().getSymmetryPenalty(world, pos);
	}
	
	@Override
	default boolean canStabaliseInfusion(World world, BlockPos var2) {
		return getDelegate().canStabaliseInfusion(world, var2);
	}
}
