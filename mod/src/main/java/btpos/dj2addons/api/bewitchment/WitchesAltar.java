package btpos.dj2addons.api.bewitchment;

import btpos.dj2addons.common.modrefs.CBewitchment;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.registry.AltarUpgrade;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Set;

public class WitchesAltar {
	
	public static void addAltarUpgradeBlock(IBlockState b, AltarUpgrade upgrade) {
		BewitchmentAPI.ALTAR_UPGRADES.put(blockWorldState -> blockWorldState.getBlockState() == b, upgrade);
	}
	
	public static void removeUpgrade(String oreDict) {
		OreDictionary.getOres(oreDict).forEach(WitchesAltar::removeUpgrade);
	}
	
	public static void removeUpgrade(Item item) {
		removeUpgrade(new ItemStack(item, 1, item.isDamageable() ? Short.MAX_VALUE : 0));
	}
	
	public static void removeUpgrade(IBlockState b) {
		Internal.toRemove.add(new CBewitchment.DummyBlockWorldState(b));
	}
	
	public static void removeUpgrade(ItemStack is) {
		Internal.toRemove.add(new CBewitchment.DummyPlacedItemWorldState(is));
	}
	
	public static class Internal {
		
		public static final Set<BlockWorldState> toRemove = new ObjectOpenHashSet<>();
		
		public static void executeUpgradeRemoval() {
			toRemove.forEach(dws -> CBewitchment.getAltarUpgrades().keySet().removeIf(p -> p.test(dws)));
		}
	}
}
