package btpos.dj2addons.api.mixin.bewitchment;

import com.bewitchment.api.registry.Ritual;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Objects;

@Mixin(Ritual.class)
public class MRitual {
	@Override
	public boolean equals(Object o) {
		return (o instanceof Ritual)
				&& Objects.equals(
						((Ritual) o).getRegistryName(),
						((Ritual)(Object)this).getRegistryName()
					);
	}
}
