package btpos.dj2addons.api.thaumcraft;

import btpos.dj2addons.common.objects.BiFunction2Boolean;
import btpos.dj2addons.common.objects.BiFunction2Float;
import btpos.dj2addons.common.objects.TriFunction2Boolean;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.api.crafting.IInfusionStabiliserExt;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * @see btpos.dj2addons.core.asm.api.thaumcraft.infusionstabilizers.InfusionStabilizerClassTransformer
 * @see btpos.dj2addons.config.CfgAPI.Thaumcraft
 */
public final class InfusionStabilizers {
	public static void addLogic(Class<?> target, InfusionStabilizerLogic logic) {
		Internal.LOGIC.put(target, logic);
	}
	
	public static final class Internal {
		private static final Map<Class<?>, InfusionStabilizerLogic> LOGIC = new Reference2ObjectOpenHashMap<>();
		
		public static InfusionStabilizerLogic getLogic(Class<?> c) {
			return LOGIC.get(c);
		}
	}
	
	public static class InfusionStabilizerLogic implements IInfusionStabiliserExt {
		private final BiFunction2Boolean<World, BlockPos> canStabilizeInfusion;
		private final BiFunction2Float<World, BlockPos> getStabilizationAmount;
		private final TriFunction2Boolean<World, BlockPos, BlockPos> hasSymmetryPenalty;
		private final BiFunction2Float<World, BlockPos> getSymmetryPenalty;
		
		public InfusionStabilizerLogic(BiFunction2Boolean<World, BlockPos> canStabilizeInfusion, BiFunction2Float<World, BlockPos> getStabilizationAmount, @Nullable TriFunction2Boolean<World, BlockPos, BlockPos> hasSymmetryPenalty, @Nullable BiFunction2Float<World, BlockPos> getSymmetryPenalty) {
			this.canStabilizeInfusion = canStabilizeInfusion;
			this.getStabilizationAmount = getStabilizationAmount;
			this.hasSymmetryPenalty = hasSymmetryPenalty == null ? (w, p1, p2) -> false : hasSymmetryPenalty;
			this.getSymmetryPenalty = getSymmetryPenalty == null ? (w, p) -> 0.0f : getSymmetryPenalty;
		}
		
		public InfusionStabilizerLogic(BiFunction2Boolean<World, BlockPos> canStabilizeInfusion, BiFunction2Float<World, BlockPos> getStabilizationAmount) {
			this(canStabilizeInfusion, getStabilizationAmount, null, null);
		}
		
		public InfusionStabilizerLogic(float stabilizationAmount, float symmetryPenalty) {
			this((w, p) -> true, (w, p) -> stabilizationAmount, (w, p1, p2) -> true, (w, p) -> symmetryPenalty);
		}
		
		public InfusionStabilizerLogic(float stabilizationAmount) {
			this((w, p) -> true, (w, p) -> stabilizationAmount, (w, p1, p2) -> false, (w, p) -> 0.0f);
		}
		
		@Override
		public float getStabilizationAmount(World var1, BlockPos var2) {
			return getStabilizationAmount.call(var1, var2);
		}
		
		@Override
		public boolean hasSymmetryPenalty(World world, BlockPos pos1, BlockPos pos2) {
			return hasSymmetryPenalty.call(world, pos1, pos2);
		}
		
		@Override
		public float getSymmetryPenalty(World world, BlockPos pos) {
			return getSymmetryPenalty.call(world, pos);
		}
		
		@Override
		public boolean canStabaliseInfusion(World var1, BlockPos var2) {
			return canStabilizeInfusion.call(var1, var2);
		}
	}
}
