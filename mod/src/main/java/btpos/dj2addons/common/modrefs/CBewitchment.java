package btpos.dj2addons.common.modrefs;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.registry.AltarUpgrade;
import net.minecraft.block.state.BlockWorldState;

import java.util.Map;
import java.util.function.Predicate;

public class CBewitchment {
	
	public static Map<Predicate<BlockWorldState>, AltarUpgrade> getAltarUpgrades() {
		return BewitchmentAPI.ALTAR_UPGRADES;
	}
	
}
