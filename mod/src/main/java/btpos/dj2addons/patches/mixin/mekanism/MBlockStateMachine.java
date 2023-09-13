package btpos.dj2addons.patches.mixin.mekanism;

import mekanism.common.block.states.BlockStateMachine;
import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import org.spongepowered.asm.mixin.Mixin;

// TODO UNIMPLEMENTED
@Mixin(BlockStateMachine.class)
public abstract class MBlockStateMachine extends ExtendedBlockState {
	public MBlockStateMachine(Block blockIn, IProperty<?>[] properties, IUnlistedProperty<?>[] unlistedProperties) {
		super(blockIn, properties, unlistedProperties);
	}
	
	public EnumPushReaction getPushReaction(IBlockState state) {
		return EnumPushReaction.DESTROY;
	}
}
