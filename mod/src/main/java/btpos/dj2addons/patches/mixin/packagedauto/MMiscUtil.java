package btpos.dj2addons.patches.mixin.packagedauto;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import thelm.packagedauto.api.MiscUtil;

@Mixin(MiscUtil.class)
public abstract class MMiscUtil {
	private static NBTTagCompound tagcompound;
	
	@ModifyVariable(
			remap=false,
			method="condenseStacks(Ljava/util/List;Z)Ljava/util/List;",
			at = @At(
					value="STORE",
					ordinal = 0
			),
			slice = @Slice(
					from = @At(
							target = "it/unimi/dsi/fastutil/objects/Object2IntMap$Entry.getIntValue()I",
							value = "INVOKE"
					)
			)
	)
	private static NBTTagCompound getNBTCompound(NBTTagCompound nbt) {
		tagcompound = nbt;
		return nbt;
	}
	
	
	/**
	 * Adds the NBT back to the ItemStack when "ignoreStackSize" is true.
	 */
	@ModifyArg(
			remap = false,
			method = "condenseStacks(Ljava/util/List;Z)Ljava/util/List;",
			at = @At(
					target="java/util/List.add (Ljava/lang/Object;)Z",
					value="INVOKE",
					ordinal = 0
			),
			slice = @Slice(
					from = @At(
							target = "it/unimi/dsi/fastutil/objects/Object2IntMap$Entry.getIntValue()I",
							value = "INVOKE"
					)
			)
	)
	private static Object addTagCompound(Object stack) {
		if (stack instanceof ItemStack) {
			ItemStack is = ((ItemStack)stack);
			is.setTagCompound(tagcompound);
			return is;
		} else {
			return stack;
		}
	}
}

