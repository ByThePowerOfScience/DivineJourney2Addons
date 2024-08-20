package btpos.dj2addons.api.thaumcraft;

import btpos.dj2addons.asmducks.InfusionStabilizerDelegateDuck;
import btpos.dj2addons.common.util.CollectionsUtils;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.api.crafting.IInfusionStabiliserExt;

import java.util.Map;

/**
 * @see btpos.dj2addons.core.asm.api.thaumcraft.infusionstabilizers.InfusionStabilizerClassTransformer
 * @see btpos.dj2addons.config.CfgAPI.Thaumcraft
 */
public final class InfusionStabilizers {
	public static void addLogic(Class<?> target, IInfusionStabiliserExt logic) {
		// make sure we've implemented the right interface
		if (!CollectionsUtils.anyMatch(target.getInterfaces(), InfusionStabilizerDelegateDuck.class)) {
			throw new RuntimeException("Target class \"" + target.getName() + "\" does not implement InfusionStabilizer! Make sure to add the class name to the config in `config/dj2addons/features.cfg`!");
		}
		Internal.LOGIC.put(target, logic);
	}
	
	public static final class Internal {
		private static final Map<Class<?>, IInfusionStabiliserExt> LOGIC = new Reference2ObjectOpenHashMap<>();
		
		public static IInfusionStabiliserExt getLogic(Class<?> c) {
			return LOGIC.get(c);
		}
		
		public static void giveBlocksLogic() {
		
		}
		
		static final IInfusionStabiliserExt DEFAULT = new IInfusionStabiliserExt() {
			@Override
			public float getStabilizationAmount(World var1, BlockPos var2) {
				return 0;
			}
			
			@Override
			public boolean canStabaliseInfusion(World world, BlockPos pos) {
				return false;
			}
		};
	}
}
