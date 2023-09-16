package btpos.dj2addons.optimizations.mixin.aether_legacy;

import com.gildedgames.the_aether.player.abilities.AbilityAccessories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.HashMap;
import java.util.Map;

/**
 * Fixes this method creating 1000 new itemstacks every tick for LITERALLY NO REASON
 */
@Mixin(AbilityAccessories.class)
public abstract class MAbilityAccessories {
    @Unique private static final Map<Item, ItemStack> dj2addons$itemstackCache = new HashMap<>();
	
	@Redirect(
			remap=false,
			method="onUpdate",
			at=@At(
					value="NEW",
					target="(Lnet/minecraft/item/Item;)net/minecraft/item/ItemStack"
			)
	)
	private ItemStack cacheResult(Item itemIn) {
		return dj2addons$itemstackCache.computeIfAbsent(itemIn, ItemStack::new);
	}
	
}

