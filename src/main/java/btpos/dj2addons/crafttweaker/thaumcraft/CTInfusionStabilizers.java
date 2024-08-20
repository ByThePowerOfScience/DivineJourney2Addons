package btpos.dj2addons.crafttweaker.thaumcraft;

import btpos.dj2addons.api.thaumcraft.InfusionStabilizers;
import btpos.dj2addons.common.util.zendoc.ZenDocClass;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMap;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.minecraft.CraftTweakerMC;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import thaumcraft.api.crafting.IInfusionStabiliserExt;

import java.util.Map;
import java.util.function.Predicate;

@ZenRegister
@ModOnly("thaumcraft")
@ZenClass("dj2addons.thaumcraft.InfusionStabilizers")
@ZenDocClass(value = "dj2addons.thaumcraft.InfusionStabilizers", description = {
		"Utilities for blocks that stabilize Thaumcraft's Runic Matrix crafting operations."
})
public final class CTInfusionStabilizers {
	@ZenMethod
	public static CTInfusionStabilizerLogicBuilder newInfusionStabilizer(String className) {
		return new CTInfusionStabilizerLogicBuilder(className);
	}
	
	/**
	 * Essentially a builder for {@link thaumcraft.api.crafting.IInfusionStabiliserExt}
	 */
	@ZenClass("dj2addons.thaumcraft.InfusionStabilizerBuilder")
	public static final class CTInfusionStabilizerLogicBuilder {
		private final String className;
		private final Map<IBlockState, SubBuilder> blockLogicBuilders = new Object2ObjectOpenHashMap<>();
		
		private CTInfusionStabilizerLogicBuilder(String className) {
			this.className = className;
		}
		
		@ZenMethod
		public void build() throws ClassNotFoundException {
			InfusionStabilizers.addLogic(Class.forName(className), assemble());
		}
		
		@ZenMethod
		public SubBuilder forBlock(IBlockState target) {
			return new SubBuilder(target);
		}
		
		@ZenMethod
		private IInfusionStabiliserExt assemble() {
			ImmutableMap.Builder<net.minecraft.block.state.IBlockState, SingleBlockLogic> builder = ImmutableMap.builder();
			// map the keys from crafttweaker IBlockStates to actual IBlockStates
			blockLogicBuilders.forEach((k, v) -> builder.put(CraftTweakerMC.getBlockState(k), v.toImmutable()));
			ImmutableMap<net.minecraft.block.state.IBlockState, SingleBlockLogic> map = builder.build();
			return new BlockCheckDelegate(map);
		}
		
		/**
		 * Logic for a single block that's under that class name
		 * <p>Converted to immutable for memory reasons
		 */
		@ZenClass(SubBuilder.ZENCLASSNAME)
		public final class SubBuilder {
			private static final String ZENCLASSNAME = "dj2addons.thaumcraft.InfusionStabilizers.BlockBuilder";
			
			private final IBlockState target;
			private float stabAmt = 0.0f;
			/**
			 * takes in the opposing blockstate as an argument
			 */
			private Predicate<IBlockState> hasSymmetryPenalty = Predicates.alwaysFalse();
			private float symmetryPenalty = 0.0f;
			
			private SubBuilder(IBlockState target) {
				this.target = target;
			}
			
			@ZenMethod
			public SubBuilder stabilizationAmount(float stabAmt) {
				this.stabAmt = stabAmt;
				return this;
			}
			
			@ZenMethod
			public SubBuilder hasSymmetryPenalty(Predicate<IBlockState> hasSymmetryPenalty) {
				this.hasSymmetryPenalty = hasSymmetryPenalty;
				return this;
			}
			
			@ZenMethod
			public SubBuilder symmetryPenalty(float symmetryPenalty) {
				this.symmetryPenalty = symmetryPenalty;
				return this;
			}
			
			@ZenMethod
			public CTInfusionStabilizerLogicBuilder endBlock() {
				CTInfusionStabilizerLogicBuilder.this.blockLogicBuilders.put(this.target, this);
				return CTInfusionStabilizerLogicBuilder.this;
			}
			
			public SingleBlockLogic toImmutable() {
				return new SingleBlockLogic(stabAmt, hasSymmetryPenalty, symmetryPenalty);
			}
		}
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
		
		private SingleBlockLogic(float stabilizationAmount, Predicate<IBlockState> hasSymmetryPenalty, float symmetryPenalty) {
			this.stabilizationAmount = stabilizationAmount;
			this.hasSymmetryPenalty = hasSymmetryPenalty;
			this.symmetryPenalty = symmetryPenalty;
		}
	}
}
