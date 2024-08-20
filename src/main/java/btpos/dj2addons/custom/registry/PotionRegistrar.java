package btpos.dj2addons.custom.registry;

import btpos.dj2addons.common.util.Util;
import btpos.dj2addons.core.DJ2Addons;
import btpos.dj2addons.custom.impl.PotionSatuRegen;
import btpos.dj2addons.custom.registry.holders.DJ2APotions;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraftforge.registries.IForgeRegistry;

public class PotionRegistrar {
	public static void registerPotions(IForgeRegistry<Potion> registry) {
		registry.register(DJ2APotions.saturegen = new PotionSatuRegen());
	}
	
	public static void registerPotionTypes(IForgeRegistry<PotionType> registry) {
		registerBottles(registry, DJ2APotions.saturegen, 900, true, true);
	}
	
	private static void registerBottles(IForgeRegistry<PotionType> registry, Potion effect, int baseDur, boolean hasStrong, boolean hasLong) {
		final String name = effect.getRegistryName().getPath();
		registry.register(
				new PotionType(DJ2Addons.MOD_ID + "." + name, new PotionEffect(effect, baseDur))
						.setRegistryName(Util.loc(name)));
		if (hasStrong) {
			registry.register(
					new PotionType(DJ2Addons.MOD_ID + ".strong_" + name, new PotionEffect(effect, baseDur, 1))
							.setRegistryName(Util.loc("strong_" + name)));
		}
		if (hasLong) {
			registry.register(
					new PotionType(DJ2Addons.MOD_ID + ".long_" + name, new PotionEffect(effect, baseDur * 2))
							.setRegistryName(Util.loc("long_" + name)));
		}
	}
}
