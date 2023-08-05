package btpos.dj2addons.custom.registry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import btpos.dj2addons.common.util.Util;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Credit to GildedGames ({@link com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs AetherCreativeTabs}) for general format.
 */
public class ModCreativeTabs {
	public static final DJ2ACreativeTab MAIN = new DJ2ACreativeTab("dj2addons");
	
	public static void init() {
		ItemStack icon;
		try {
			ItemStack is = new ItemStack(Items.POTIONITEM);
			icon = PotionUtils.addPotionToItemStack(is, Objects.requireNonNull(GameRegistry.findRegistry(PotionType.class).getValue(Util.loc("saturegen"))));
		} catch (NullPointerException e) {
			icon = new ItemStack(Blocks.GRASS);
		}
		MAIN.setIcon(icon);
	}
	
	public static class DJ2ACreativeTab extends CreativeTabs {
		private ItemStack stack;
		
		public DJ2ACreativeTab(String unlocalizedName) {
			super(unlocalizedName);
		}
		
		public DJ2ACreativeTab(String unlocalizedName, ItemStack stack) {
			super(unlocalizedName);
			this.stack = stack;
		}
		
		public void setIcon(ItemStack stack) {
			this.stack = stack;
		}
		
		@SideOnly(Side.CLIENT) @NotNull
		public String getTranslationKey() {
			return "tab." + this.getTabLabel();
		}
		
		@NotNull
		public ItemStack createIcon() {
			return this.stack;
		}
	}
}
