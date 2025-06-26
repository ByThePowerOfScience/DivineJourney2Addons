package btpos.dj2addons.crafttweaker.thaumcraft;

import btpos.dj2addons.api.thaumcraft.InfusionStabilizers;
import btpos.dj2addons.common.util.zendoc.ZenDocArg;
import btpos.dj2addons.common.util.zendoc.ZenDocClass;
import btpos.dj2addons.common.util.zendoc.ZenDocMethod;
import btpos.dj2addons.crafttweaker.thaumcraft.CTInfusionStabilizers.BlockCheckDelegate;
import btpos.dj2addons.crafttweaker.thaumcraft.CTInfusionStabilizers.SingleBlockLogic;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMap;
import crafttweaker.annotations.ZenDoc;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.block.IBlockStateMatcher;
import crafttweaker.api.minecraft.CraftTweakerMC;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import thaumcraft.api.crafting.IInfusionStabiliserExt;

import java.util.Map;
import java.util.function.Predicate;

/**
 * Essentially a builder for {@link thaumcraft.api.crafting.IInfusionStabiliserExt IInfusionStabiliserExt}.
 *
 * @implNote This class has to be on the top level to allow the project to build. Otherwise Mixin suddenly can't find the ZenClass annotation on specifically this class but not the outer class.
 */
@ZenClass("dj2addons.thaumcraft.InfusionStabilizerBuilder")
@ZenDocClass(value = "dj2addons.thaumcraft.InfusionStabilizerBuilder", description = "Builds an infusion stabilizer's logic for a class specified in the config.", onlyInOther = true)
public final class CTInfusionStabilizerLogicBuilder {
	private final String className;
	private final Map<IBlockState, SubBuilder> blockLogicBuilders = new Object2ObjectOpenHashMap<>();
	
	CTInfusionStabilizerLogicBuilder(String className) {
		this.className = className;
	}
	
	@ZenMethod
	@ZenDocMethod(order = 2, description = "Register this Infusion Stabilizer logic.")
	public void build() throws ClassNotFoundException {
		InfusionStabilizers.addLogic(Class.forName(className), assemble());
	}
	
	@ZenMethod
	@ZenDocMethod(order = 1, description = "Start building logic for a specific blockstate under this class name.", args = @ZenDocArg(value = "target"))
	public SubBuilder forBlock(IBlockState target) {
		return new SubBuilder(target);
	}
	
	private IInfusionStabiliserExt assemble() {
		ImmutableMap.Builder<net.minecraft.block.state.IBlockState, SingleBlockLogic> builder = ImmutableMap.builder();
		// map the keys from crafttweaker IBlockStates to actual IBlockStates
		blockLogicBuilders.forEach((k, v) -> builder.put(CraftTweakerMC.getBlockState(k), v.toImmutable()));
		ImmutableMap<net.minecraft.block.state.IBlockState, SingleBlockLogic> map = builder.build();
		return new BlockCheckDelegate(map);
	}
	
	/**
	 * Logic for a single block that's under that class name
	 * <p>Converted to immutable version {@link btpos.dj2addons.crafttweaker.thaumcraft.CTInfusionStabilizers.SingleBlockLogic} for memory's sake
	 */
	@ZenClass(SubBuilder.ZENCLASSNAME)
	@ZenDocClass(value = SubBuilder.ZENCLASSNAME, description = "Builds the infusion stabilization behavior for a specific blockstate.", onlyInOther = true)
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
		
		@ZenDoc("How much this blockstate stabilizes the infusion on its own.")
		@ZenMethod
		@ZenDocMethod(order = 1, description = "How much this block stabilizes the infusion on its own.", args = @ZenDocArg("amount"))
		public SubBuilder stabilizationAmount(float stabAmt) {
			this.stabAmt = stabAmt;
			return this;
		}
		
		@ZenMethod
		@ZenDoc("Takes in the opposing blockstate as an argument.")
		@ZenDocMethod(order = 2, description = {"Set logic for symmetry: check if the block opposite it is ok, or if it should add a symmetry penalty to the infusion for not being symmetrical.", "Defaults to false."}, args = @ZenDocArg(value = "predicate", info = "Takes in the opposing blockstate and returns 'true' if it should add a penalty."))
		public SubBuilder hasSymmetryPenalty(IBlockStateMatcher hasSymmetryPenalty) {
			this.hasSymmetryPenalty = hasSymmetryPenalty::matches;
			return this;
		}
		
		@ZenDoc("The strength of the symmetry penalty to be applied if hasSymmetryPenalty is true.")
		@ZenMethod
		@ZenDocMethod(order = 3, description = "Set the strength of the symmetry penalty if one is applied.", args = @ZenDocArg(value = "penalty"))
		public SubBuilder symmetryPenalty(float symmetryPenalty) {
			this.symmetryPenalty = symmetryPenalty;
			return this;
		}
		
		@ZenDoc("Finish building logic for this block.")
		@ZenMethod
		@ZenDocMethod(order = 4, description = "Finish building this block's logic and return to the main builder.")
		public CTInfusionStabilizerLogicBuilder endBlock() {
			CTInfusionStabilizerLogicBuilder.this.blockLogicBuilders.put(this.target, this);
			return CTInfusionStabilizerLogicBuilder.this;
		}
		
		public SingleBlockLogic toImmutable() {
			return new SingleBlockLogic(stabAmt, hasSymmetryPenalty, symmetryPenalty);
		}
	}
	
}
