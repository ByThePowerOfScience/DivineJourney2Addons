package btpos.dj2addons.optimizations.mixinducks.aether_legacy;

import net.minecraft.item.Item;

public interface Duck_Accessories {
	boolean wearingAccessory(Item item);
	
	void damageWornStack(int damage, Item item);
}
