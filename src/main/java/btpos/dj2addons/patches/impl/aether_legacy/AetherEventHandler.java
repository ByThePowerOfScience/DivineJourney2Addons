package btpos.dj2addons.patches.impl.aether_legacy;

import btpos.dj2addons.DJ2AConfig;
import btpos.dj2addons.common.modrefs.CAetherLegacy;
import btpos.dj2addons.common.modrefs.IsModLoaded;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AetherEventHandler {
	public static void tryEnable() {
		if (DJ2AConfig.disable_patches || !DJ2AConfig.patches.aether_legacy.stopAerogelCrash)
			return;
		
		MinecraftForge.EVENT_BUS.register(AetherEventHandler.class);
	}
	
	/**
	 * Attempt to stop aerogel automation crashing servers
	 */
	@SubscribeEvent
	public static void onPlaceBlock(EntityPlaceEvent event) {
		if (IsModLoaded.aether_legacy
		    && event.getBlockSnapshot().getWorld().provider.getDimension() == CAetherLegacy.getDimensionId()
		    && (event.getPlacedBlock().getBlock() == Blocks.LAVA || event.getPlacedBlock().getBlock() == Blocks.FLOWING_LAVA))
		{
			World world = event.getBlockSnapshot().getWorld();
			event.setCanceled(true);
			if (!world.isRemote)
				world.setBlockState(event.getBlockSnapshot().getPos(), CAetherLegacy.getAerogelBlock().getDefaultState());
			else
				event.getBlockSnapshot().setReplacedBlock(CAetherLegacy.getAerogelBlock().getDefaultState());
		}
	}
}
