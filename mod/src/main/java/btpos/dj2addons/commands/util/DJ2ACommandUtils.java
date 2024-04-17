package btpos.dj2addons.commands.util;

import btpos.dj2addons.common.util.Util;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;

import java.util.List;
import java.util.UUID;

// We depend on crafttweaker rn, but eventually this won't be the case so we'll have to do all of the "copy" logic in-house
public final class DJ2ACommandUtils {
	public static final UUID me_lol = UUID.fromString("48a5a976-9d08-421c-b4f3-1128c8f1aeba");
	
	private DJ2ACommandUtils() {}
	
	public static void printBlockMeta(MessageHelper m, BlockPos blockPos, IBlockState block) {
		m.sendHeading("Block State:");
		int meta = block.getBlock().getMetaFromState(block);
		String blockName = "<" + block.getBlock().getRegistryName() + (meta == 0 ? "" : ":" + meta) + ">";
		
		m.sendMessageWithCopy(formatPos(blockPos, blockName) + "§r", blockName);
		
		// adds the oreDict names if it has some
		try {
			List<String> oreDictNames = crafttweaker.mc1120.commands.CommandUtils.getOreDictOfItem(new ItemStack(block.getBlock(), 1, block.getBlock().getMetaFromState(block)));
			printOreDictInfo(m, oreDictNames);
			
		} catch (IllegalArgumentException e) { // catches if it couldn't create a valid ItemStack for the Block
			m.sendHeading("No OreDict Entries.");
		}
	}
	
	private static String formatPos(BlockPos blockPos, String blockName) {
		return "Block §2" + blockName + " §rat " + formatPos(blockPos);
	}
	
	public static String formatPos(BlockPos blockPos) {
		return "§9" + Util.Format.formatPos(blockPos);
	}
	
	public static BlockPos getLookAtPos(EntityPlayer player) {
		RayTraceResult rayTraceResult = crafttweaker.mc1120.commands.CommandUtils.getPlayerLookat(player, 100);
		
		if (rayTraceResult != null && rayTraceResult.typeOfHit != Type.MISS) {
			return rayTraceResult.getBlockPos();
		}
		
		return null;
	}
	
	public static void printOreDictInfo(MessageHelper m, List<String> oreDictNames) {
		if (!oreDictNames.isEmpty()) {
			m.sendHeading("OreDict Entries:");
			for (String oreName : oreDictNames) {
				m.sendPropertyWithCopy(null, oreName, "<ore:" + oreName + ">");
			}
		} else {
			m.sendHeading("No OreDict Entries");
		}
	}
}
