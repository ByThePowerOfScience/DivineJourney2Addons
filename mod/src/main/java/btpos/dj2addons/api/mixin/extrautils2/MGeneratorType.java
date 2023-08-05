package btpos.dj2addons.api.mixin.extrautils2;

import com.rwtema.extrautils2.blocks.BlockPassiveGenerator;
import btpos.dj2addons.api.impl.extrautils2.VExtraUtilities;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(BlockPassiveGenerator.GeneratorType.class)
public abstract class MGeneratorType {
	
	@ModifyArg(
			remap=false,
			method = "<clinit>",
			slice=@Slice(
					from=@At(
							value="CONSTANT",
							args="stringValue=SOLAR"
					),
					to=@At(
							value="FIELD",
							target="Lcom/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType;SOLAR:Lcom/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType;",
							opcode=Opcodes.PUTSTATIC
					)
			),
			at=@At(
					value="INVOKE",
					target="com/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType$1.<init> (Ljava/lang/String;ILcom/rwtema/extrautils2/power/IWorldPowerMultiplier;[F)V"
			)
	)
	private static float[] modifySolarScaling(float[] values) {
		return VExtraUtilities.getScalingOrReturnOriginal("SOLAR", values);
	}
	
	
	@ModifyArg(
			remap=false,
			method = "<clinit>",
			slice=@Slice(
					from=@At(
							value="CONSTANT",
							args="stringValue=LUNAR"
					),
					to=@At(
							value="FIELD",
							target="Lcom/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType;LUNAR:Lcom/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType;",
							opcode=Opcodes.PUTSTATIC
					)
			),
			at=@At(
					value="INVOKE",
					target="com/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType$2.<init> (Ljava/lang/String;ILcom/rwtema/extrautils2/power/IWorldPowerMultiplier;[F)V"
			)
	)
	private static float[] modifyLunarScaling(float[] values) {
		return VExtraUtilities.getScalingOrReturnOriginal("LUNAR", values);
	}
	
	
	@ModifyArg(
			remap=false,
			method = "<clinit>",
			slice=@Slice(
					from=@At(
							value="CONSTANT",
							args="stringValue=LAVA"
					),
					to=@At(
							value="FIELD",
							target="Lcom/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType;LAVA:Lcom/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType;",
							opcode=Opcodes.PUTSTATIC
					)
			),
			at=@At(
					value="INVOKE",
					target="com/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType$3.<init> (Ljava/lang/String;ILcom/rwtema/extrautils2/power/IWorldPowerMultiplier;[F)V"
			)
	)
	private static float[] modifyLavaScaling(float[] values) {
		return VExtraUtilities.getScalingOrReturnOriginal("LAVA", values);
	}
	
	@ModifyArg(
			remap=false,
			method = "<clinit>",
			slice=@Slice(
					from=@At(
							value="CONSTANT",
							args="stringValue=WATER"
					),
					to=@At(
							value="FIELD",
							target="Lcom/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType;WATER:Lcom/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType;",
							opcode=Opcodes.PUTSTATIC
					)
			),
			at=@At(
					value="INVOKE",
					target="com/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType$4.<init> (Ljava/lang/String;ILcom/rwtema/extrautils2/power/IWorldPowerMultiplier;[F)V"
			)
	)
	private static float[] modifyWaterScaling(float[] values) {
		return VExtraUtilities.getScalingOrReturnOriginal("WATER", values);
	}
	
	
	@ModifyArg(
			remap=false,
			method = "<clinit>",
			slice=@Slice(
					from=@At(
							value="CONSTANT",
							args="stringValue=WIND"
					),
					to=@At(
							value="FIELD",
							target="Lcom/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType;WIND:Lcom/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType;",
							opcode=Opcodes.PUTSTATIC
					)
			),
			at=@At(
					value="INVOKE",
					target="com/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType$5.<init> (Ljava/lang/String;ILcom/rwtema/extrautils2/power/IWorldPowerMultiplier;[F)V"
			)
	)
	private static float[] modifyWindScaling(float[] values) {
		return VExtraUtilities.getScalingOrReturnOriginal("WIND", values);
	}
	
	@ModifyArg(
			remap=false,
			method = "<clinit>",
			slice=@Slice(
					from=@At(
							value="CONSTANT",
							args="stringValue=FIRE"
					),
					to=@At(
							value="FIELD",
							target="Lcom/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType;FIRE:Lcom/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType;",
							opcode=Opcodes.PUTSTATIC
					)
			),
			at=@At(
					value="INVOKE",
					target="com/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType$6.<init> (Ljava/lang/String;ILcom/rwtema/extrautils2/power/IWorldPowerMultiplier;[F)V"
			)
	)
	private static float[] modifyFireScaling(float[] values) {
		return VExtraUtilities.getScalingOrReturnOriginal("FIRE", values);
	}
	
	@ModifyArg(
			remap=false,
			method = "<clinit>",
			slice=@Slice(
					from=@At(
							value="CONSTANT",
							args="stringValue=PLAYER_WIND_UP"
					),
					to=@At(
							value="FIELD",
							target="Lcom/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType;PLAYER_WIND_UP:Lcom/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType;",
							opcode=Opcodes.PUTSTATIC
					)
			),
			at=@At(
					value="INVOKE",
					target="com/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType$8.<init> (Ljava/lang/String;ILcom/rwtema/extrautils2/power/IWorldPowerMultiplier;[F)V"
			)
	)
	private static float[] modifyPlayerWindUpScaling(float[] values) {
		return VExtraUtilities.getScalingOrReturnOriginal("PLAYER_WIND_UP", values);
	}
	
	
	@ModifyArg(
			remap=false,
			method = "<clinit>",
			slice=@Slice(
					from=@At(
							value="CONSTANT",
							args="stringValue=DRAGON_EGG"
					),
					to=@At(
							value="FIELD",
							target="Lcom/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType;DRAGON_EGG:Lcom/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType;",
							opcode=Opcodes.PUTSTATIC
					)
			),
			at=@At(
					value="INVOKE",
					target="com/rwtema/extrautils2/blocks/BlockPassiveGenerator$GeneratorType$9.<init> (Ljava/lang/String;ILcom/rwtema/extrautils2/power/IWorldPowerMultiplier;[F)V"
			)
	)
	private static float[] modifyDragonEggMillScaling(float[] values) {
		return VExtraUtilities.getScalingOrReturnOriginal("DRAGON_EGG", values);
	}
}
