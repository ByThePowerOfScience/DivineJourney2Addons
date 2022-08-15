package org.btpos.dj2addons.registry;

import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import org.btpos.dj2addons.DJ2Addons;
import org.btpos.dj2addons.impl.custom.StatusEffects;

public class ModPotions {
	@GameRegistry.ObjectHolder(DJ2Addons.MOD_ID)
	public static class Registered {
		public static final Potion saturegen = null;
	}
	
	public static void init(RegistryEvent.Register<Potion> evt) {
		IForgeRegistry<Potion> registry = evt.getRegistry();
		registry.register(new StatusEffects.SatuRegen());
	}
}
