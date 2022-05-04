package org.btpos.dj2addons.mixin.bewitchment;

import com.bewitchment.api.registry.Ritual;
import org.apache.logging.log4j.Level;
import org.btpos.dj2addons.DJ2Addons;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Objects;

@Mixin(Ritual.class)
public class MRitual {
	@Override
	public boolean equals(Object o) {
		if (o instanceof Ritual)
			if (!Objects.equals(((Ritual) o).getRegistryName(), ((Ritual) (Object) this).getRegistryName()))
				DJ2Addons.LOGGER.log(Level.DEBUG, ((Ritual) o).getRegistryName() + " does not equal " + ((Ritual)(Object)this).getRegistryName());
		return (o instanceof Ritual)
				&& Objects.equals(((Ritual) o).getRegistryName(), ((Ritual)(Object)this).getRegistryName());
	}
}
