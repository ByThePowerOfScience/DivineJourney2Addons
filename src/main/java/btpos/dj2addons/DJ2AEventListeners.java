package btpos.dj2addons;

import btpos.dj2addons.core.DJ2Addons;
import btpos.dj2addons.custom.registry.PotionRegistrar;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

//@EventBusSubscriber(modid = DJ2Addons.MOD_ID)
public final class DJ2AEventListeners {
	@SubscribeEvent
	public static void addPotions(RegistryEvent.Register<Potion> evt) {
		DJ2Addons.LOGGER.debug("Registering potions!");
		PotionRegistrar.registerPotions(evt.getRegistry());
	}
	
	@SubscribeEvent
	public static void addPotionTypes(RegistryEvent.Register<PotionType> evt) {
		DJ2Addons.LOGGER.debug("Registering potions types!");
		PotionRegistrar.registerPotionTypes(evt.getRegistry());
	}
	
	@SubscribeEvent
	public static void onConfigChangedEvent(OnConfigChangedEvent event) {
		if (event.getModID().equals(DJ2Addons.MOD_ID)) {
			ConfigManager.sync(DJ2Addons.MOD_ID, Type.INSTANCE);
		}
	}
	
	private ITextComponent getBetaAdmonition() {
		Style defStyle = new Style().setUnderlined(true).setBold(true);
		return new TextComponentString("You are using a BETA version of ").setStyle(defStyle)
		                                                                  .appendSibling(new TextComponentString("DJ2Addons").setStyle(new Style().setColor(TextFormatting.GOLD).setBold(true)))
		                                                                  .appendSibling(new TextComponentString(". Report any issues ").setStyle(defStyle))
		                                                                  .appendSibling(new TextComponentString("here").setStyle(new Style().setUnderlined(true).setBold(true).setColor(TextFormatting.AQUA)
		                                                                                                                                     .setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/ByThePowerOfScience/DivineJourney2Addons/issues"))
		                                                                                                                                     .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("https://github.com/ByThePowerOfScience/DivineJourney2Addons/issues")))))
		                                                                  .appendSibling(new TextComponentString(".").setStyle(defStyle));
	}
	
	boolean havePrinted = false;
	
	// TODO figure this frickin thing out because half the time it just doesn't work and half the time it stops the whole mod from loading
	public void doBetaAdmonition(EntityJoinWorldEvent event) {
		if (!havePrinted) {
			DJ2Addons.LOGGER.debug("Printing beta notice");
			event.getEntity().sendMessage(getBetaAdmonition());
			havePrinted = true;
		}
	}
	
	//TODO Figure out models and how to register them
	//
	//		@SubscribeEvent
	//		public static void addBlocks(RegistryEvent.Register<Block> evt) {
	//			ModBlocks.registerBlocks(evt.getRegistry());
	//		}
	//
	//		@SubscribeEvent
	//		public static void addItems(RegistryEvent.Register<Item> evt) {
	//			ModBlocks.registerBlockItems(evt.getRegistry());
	//			//ModItems.registerItems(evt.getRegistry());
	//		}
}
