package org.btpos.dj2addons.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;
import org.btpos.dj2addons.DJ2Addons;
import org.btpos.dj2addons.registry.blocks.BMMobBlock;
import org.btpos.dj2addons.util.Util;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {
	private static final List<Block> blocks = new ArrayList<>(1);
	private static final List<Item> items = new ArrayList<>(1);
	
	public static Block BM_MOB_BLOCK;
	
	
	public static void registerBlocks(IForgeRegistry<Block> registry) {
		registry.registerAll(blocks.toArray(new Block[0]));
	}
	public static void registerBlockItems(IForgeRegistry<Item> registry) {
		registry.registerAll(items.toArray(new Item[0]));
	}
	
	static {
		BM_MOB_BLOCK = registerBlock("bm_mob_block", new BMMobBlock());
	}
	
	public static Block registerBlock(String name, Block block) {
		block.setRegistryName(Util.loc(name));
		block.setTranslationKey(DJ2Addons.MOD_ID + ".block." + name);
		block.setCreativeTab(ModCreativeTabs.MAIN);
		blocks.add(block);
		Item i = (new ItemBlock(block)).setRegistryName(Util.loc(name)).setTranslationKey(block.getTranslationKey());
		DJ2Addons.proxy.registerTexture(i, "normal");
		items.add(i);
		return block;
	}
}
