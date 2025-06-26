package btpos.dj2addons.crafttweaker.thaumcraft;

import btpos.dj2addons.common.util.zendoc.ZenDocArg;
import btpos.dj2addons.common.util.zendoc.ZenDocClass;
import btpos.dj2addons.common.util.zendoc.ZenDocInclude;
import btpos.dj2addons.common.util.zendoc.ZenDocMethod;
import btpos.dj2addons.crafttweaker.thaumcraft.CTInfusionStabilizerLogicBuilder.SubBuilder;
import com.google.common.collect.ImmutableMap;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenDoc;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import thaumcraft.api.crafting.IInfusionStabiliserExt;

import java.util.function.Predicate;

@ZenRegister
@ModOnly("thaumcraft")
@ZenClass("dj2addons.thaumcraft.InfusionStabilizers")
@ZenDocClass(value = "dj2addons.thaumcraft.InfusionStabilizers", description = {
		"Utilities for blocks that stabilize Thaumcraft's Runic Matrix crafting operations."
}) @ZenDocInclude({CTInfusionStabilizerLogicBuilder.class, SubBuilder.class})
public final class CTInfusionStabilizers {
	@ZenMethod @ZenDoc("See docs on GitHub: /docs/other/thaumcraft/InfusionStabilizers.md")
	@ZenDocMethod(order=1, description = "Start building the stabilization logic for the class you registered in the [config](/docs/other/thaumcraft/InfusionStabilizers.md).", args=@ZenDocArg("className"))
	public static CTInfusionStabilizerLogicBuilder newInfusionStabilizer(String className) {
		return new CTInfusionStabilizerLogicBuilder(className);
	}
	
	/**
	 * Holder for all the logic we've covered in a way that just goes "get block" -> "get logic for block" -> "use logic"
	 */
	public static final class BlockCheckDelegate implements IInfusionStabiliserExt {
		private final ImmutableMap<net.minecraft.block.state.IBlockState, SingleBlockLogic> logic;
		
		public BlockCheckDelegate(ImmutableMap<net.minecraft.block.state.IBlockState, SingleBlockLogic> map) {
			this.logic = map;
		}
		
		@Override
		public float getStabilizationAmount(World world, BlockPos pos) {
			return getSingleBlockLogic(world, pos).stabilizationAmount;
		}
		
		@Override
		public boolean hasSymmetryPenalty(World world, BlockPos pos1, BlockPos pos2) {
			return getSingleBlockLogic(world, pos1).hasSymmetryPenalty.test(CraftTweakerMC.getBlockState(world.getBlockState(pos2)));
		}
		
		@Override
		public float getSymmetryPenalty(World world, BlockPos pos) {
			return getSingleBlockLogic(world, pos).symmetryPenalty;
		}
		
		@Override
		public boolean canStabaliseInfusion(World world, BlockPos var2) {
			return getSingleBlockLogic(world, var2) != null; // account for defaults being set I guess
		}
		
		private SingleBlockLogic getSingleBlockLogic(World world, BlockPos pos) {
			return logic.get(world.getBlockState(pos));
		}
	}
	
	/**
	 * Stripped-down struct for ease of access and to allow the JVM to optimize the hell out of it
	 */
	public static final class SingleBlockLogic {
		public final float stabilizationAmount;
		public final Predicate<IBlockState> hasSymmetryPenalty;
		public final float symmetryPenalty;
		
		protected SingleBlockLogic(float stabilizationAmount, Predicate<IBlockState> hasSymmetryPenalty, float symmetryPenalty) {
			this.stabilizationAmount = stabilizationAmount;
			this.hasSymmetryPenalty = hasSymmetryPenalty;
			this.symmetryPenalty = symmetryPenalty;
		}
	}
}
