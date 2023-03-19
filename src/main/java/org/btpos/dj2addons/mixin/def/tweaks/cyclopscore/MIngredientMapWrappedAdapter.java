package org.btpos.dj2addons.mixin.def.tweaks.cyclopscore;

import org.cyclops.commoncapabilities.api.ingredient.IngredientInstanceWrapper;
import org.cyclops.cyclopscore.ingredient.collection.IngredientHashSet;
import org.cyclops.cyclopscore.ingredient.collection.IngredientMapWrappedAdapter;
import org.cyclops.cyclopscore.ingredient.collection.IngredientSet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(IngredientMapWrappedAdapter.class)
public abstract class MIngredientMapWrappedAdapter<T, M, V, C extends Map<IngredientInstanceWrapper<T, M>, V>> {
	@Shadow @Final
	private C collection;
	
	/**
	 * @author ByThePowerOfScience
	 * @reason Removes HashSet copy creation for lag fix.
	 */
	@Overwrite(
			remap=false
	) @SuppressWarnings({"raw", "unchecked"})
	public IngredientSet<T, M> keySet() {
		return new IngredientHashSet<>(
				((IngredientMapWrappedAdapter<T,M,V,C>)(Object)(this)).getComponent(),
				(Iterable<? extends T>) this.collection.keySet()
		);
	}
}

