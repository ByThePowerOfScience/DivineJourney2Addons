package btpos.dj2addons.api.impl.bewitchment;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import btpos.dj2addons.common.modrefs.CBewitchment;
import btpos.dj2addons.common.modrefs.CBewitchment.DummyWorldState;

import java.util.HashSet;
import java.util.Set;

public class VAltarUpgrades {
	public static final Set<ItemStack> toRemove = new HashSet<>();
	
	public static void removeUpgrade(String oreDict) {
		OreDictionary.getOres(oreDict).forEach(VAltarUpgrades::removeUpgrade);
	}
	
	public static void removeUpgrade(Item item) {
		removeUpgrade(new ItemStack(item, 1, item.isDamageable() ? Short.MAX_VALUE : 0));
	}
	
	public static void removeUpgrade(ItemStack is) {
		toRemove.add(is);
	}
	
	public static void executeUpgradeRemoval() {
		toRemove.stream()
		        .map(DummyWorldState::new)
		        .forEach(dws -> CBewitchment.getAltarUpgrades().keySet().removeIf(p -> p.test(dws)));
	}
	
	
}
