package org.btpos.dj2addons.mixin.def.patches.packagedauto;

import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntRBTreeMap;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.lang3.tuple.Triple;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import thelm.packagedauto.api.MiscUtil;

import java.util.List;

@Mixin(MiscUtil.class)
public abstract class MMiscUtil {
	private static NBTTagCompound tagcompound;
	
	@Inject(
			remap=false,
			method = "condenseStacks(Ljava/util/List;Z)Ljava/util/List;",
			at = @At(
					target="java/util/List.add(Ljava/lang/Object;)Z",
					value="INVOKE",
					ordinal = 0,
					shift= Shift.BEFORE
			),
			locals = LocalCapture.CAPTURE_FAILSOFT
	)
	private static void injection(List<ItemStack> stacks, boolean ignoreStackSize, CallbackInfoReturnable<List<ItemStack>> cir, Object2IntRBTreeMap map, List list, ObjectBidirectionalIterator var4, Entry entry, Triple triple, int count, Item item, int meta, NBTTagCompound nbt) {
		tagcompound = nbt;
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
			)
	)
	private static Object foo(Object stack) {
		if (stack instanceof ItemStack) {
			ItemStack is = ((ItemStack)stack);
			is.setTagCompound(tagcompound);
			return is;
		} else {
			return stack;
		}
	}
}

