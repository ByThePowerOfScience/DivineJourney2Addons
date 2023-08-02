package btpos.dj2addons.patches.impl.aether_legacy;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import btpos.dj2addons.common.modrefs.CAetherLegacy;
import btpos.dj2addons.common.modrefs.IsModLoaded;

@EventBusSubscriber
public class AetherEventHandler {
	@SubscribeEvent
	public void onPlaceBlock(EntityPlaceEvent event) {
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
