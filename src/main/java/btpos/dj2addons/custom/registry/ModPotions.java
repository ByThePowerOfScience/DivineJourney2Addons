package btpos.dj2addons.custom.registry;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;
import btpos.dj2addons.DJ2Addons;
import btpos.dj2addons.custom.impl.StatusEffects.SatuRegen;
import btpos.dj2addons.common.util.Util;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("SameParameterValue")
public class ModPotions {
	@ObjectHolder(DJ2Addons.MOD_ID)
	public static class Registered {
		public static final Potion saturegen = null;
	}
	
	private static final List<Potion> effects = new ArrayList<>(1);
	private static final List<PotionType> potionBottles = new ArrayList<>(2);
	
	static {
		register("saturegen", new SatuRegen(), 900, true, true);
	}
	
	
	private static void register(String name, Potion effect, int baseDur, boolean hasStrong, boolean hasLong) {
		effects.add(effect);
		potionBottles.add(
				new PotionType(DJ2Addons.MOD_ID + "." + name, new PotionEffect(effect, baseDur))
						.setRegistryName(Util.loc(name)));
		if (hasStrong) {
			potionBottles.add(
					new PotionType(DJ2Addons.MOD_ID + ".strong_" + name, new PotionEffect(effect, baseDur, 1))
							.setRegistryName(Util.loc("strong_" + name)));
		}
		if (hasLong)
			potionBottles.add(
					new PotionType(DJ2Addons.MOD_ID + ".long_" + name, new PotionEffect(effect, baseDur * 2))
							.setRegistryName(Util.loc("long_" + name)));
	}
	
	public static void registerPotions(IForgeRegistry<Potion> registry) {
		registry.registerAll(effects.toArray(new Potion[0]));
	}
	
	public static void registerPotionTypes(IForgeRegistry<PotionType> registry) {
		registry.registerAll(potionBottles.toArray(new PotionType[0]));
	}
	
	
}
