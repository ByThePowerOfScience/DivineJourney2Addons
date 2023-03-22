package org.btpos.dj2addons.mixin.def.patches.mekanism;

import mekanism.common.block.states.BlockStateMachine;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockStateMachine.class)
public abstract class MBlockStateMachine {
	public EnumPushReaction getPushReaction(IBlockState state) {
		return EnumPushReaction.DESTROY;
	}
}
