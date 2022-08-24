package org.btpos.dj2addons.crafttweaker;

import com.google.common.base.Enums;
import com.google.common.base.Optional;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.commands.ClipboardHelper;
import crafttweaker.mc1120.commands.CommandUtils;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import crafttweaker.mc1120.commands.SpecialMessagesChat;
import crafttweaker.mc1120.data.NBTConverter;
import erogenousbeef.bigreactors.api.data.ReactorInteriorData;
import erogenousbeef.bigreactors.api.registry.ReactorInterior;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.btpos.dj2addons.DJ2Addons;
import org.btpos.dj2addons.impl.bewitchment.VModRecipes;
import org.btpos.dj2addons.impl.extrautilities.VExtraUtilities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pokefenn.totemic.api.music.MusicInstrument;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandHandler extends CraftTweakerCommand {
	
	public CommandHandler() {
		super(DJ2Addons.MOD_ID);
	}
	
	@Override
	protected void init() {
		setDescription(new TextComponentTranslation("dj2addons.commands.desc"));
	}
	
	@Override
	public List<String> getSubSubCommand(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
		return Arrays.stream(SubCommand.values()).map(Enum::toString).collect(Collectors.toList());
	}
	
	private enum SubCommand {
		hand, mods, bewitchment, extrautils2, totemic
	}
	
	@Override
	public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
		if (sender == null)
			return;
		if (args.length < 1) {
			sender.sendMessage(new TextComponentTranslation("dj2addons.commands.usage"));
			return;
		}
		
		Optional<SubCommand> command = Enums.getIfPresent(SubCommand.class, args[0]);
		if (command.isPresent()) {
			EntityPlayer player = sender.getCommandSenderEntity() instanceof EntityPlayer ? (EntityPlayer) sender.getCommandSenderEntity() : null;
			
			switch (command.get()) {
				case hand:
					executeHandCommand(server, sender, args);
					break;
				case mods:
				case bewitchment:
					bewitchmentHandler(sender, player);
					if (command.get().equals(SubCommand.bewitchment))
						break;
				case totemic:
					totemicHandler(sender, player);
					if (command.get().equals(SubCommand.totemic))
						break;
				case extrautils2:
					extrautilsHandler(sender, player);
					if (command.get().equals(SubCommand.extrautils2))
						break;
			}
			switch (command.get()) {
				case mods:
				case bewitchment:
				case totemic:
					sender.sendMessage(SpecialMessagesChat.getLinkToCraftTweakerLog("Printed to CraftTweaker log.", sender));
			}
		} else {
			sender.sendMessage(new TextComponentTranslation("dj2addons.commands.usage"));
		}
		
	}
	
	
	private void bewitchmentHandler(ICommandSender sender, EntityPlayer player) {
		Messages.sendHeading(sender, "Rituals:");
		CraftTweakerAPI.logInfo("Bewitchment Rituals:");
		if (player != null) {
			VModRecipes.getAllRituals().stream().filter(r -> !(r instanceof VModRecipes.DummyRitual))
					.forEach(r -> {
						CraftTweakerAPI.logInfo("- " + r.getRegistryName());
						Messages.sendPropertyWithCopy(player, null, r.getRegistryName() + "");
					});
		} else {
			VModRecipes.getAllRituals().stream().filter(r -> !(r instanceof VModRecipes.DummyRitual))
					.forEach(r -> {
						CraftTweakerAPI.logInfo("- " + r.getRegistryName());
						Messages.sendProperty(sender, null, r.getRegistryName() + "");
					});
		}
		
		if (VModRecipes.getRitualsToRemove().size() == 0) {
			Messages.sendHeading(sender, "§3No removed rituals.");
			CraftTweakerAPI.logInfo("\nNo removed rituals.");
		} else {
			Messages.sendHeading(sender, "Removed rituals:");
			CraftTweakerAPI.logInfo("\nRemoved rituals:");
			if (player != null) {
				VModRecipes.getRitualsToRemove()
						.forEach(r -> {
							CraftTweakerAPI.logInfo("- " + r.getRegistryName());
							Messages.sendPropertyWithCopy(player, null, r.getRegistryName() + "");
						});
			} else {
				VModRecipes.getRitualsToRemove().forEach(r -> {
					CraftTweakerAPI.logInfo("- " + r.getRegistryName());
					Messages.sendProperty(sender, null, r.getRegistryName() + "");
				});
			}
		}
	}
	
	
	private void totemicHandler(@NotNull ICommandSender sender, EntityPlayer player) {
		sender.sendMessage(new TextComponentString("§3Instruments:"));
		CraftTweakerAPI.logInfo("Totemic Instruments:");
		GameRegistry.findRegistry(MusicInstrument.class).forEach(i -> {
			CraftTweakerAPI.logInfo("- " + i.getRegistryName() + " [baseOutput: " + i.getBaseOutput() + ", musicMaximum: " + i.getMusicMaximum() + "]");
			if (player != null) {
				Messages.sendPropertyWithCopy(player, null, i.getRegistryName() + "");
				Messages.sendPropertyWithCopy(player, "baseOutput", String.valueOf(i.getBaseOutput()), 1);
				Messages.sendPropertyWithCopy(player, "musicMaximum", String.valueOf(i.getMusicMaximum()), 1);
			} else {
				Messages.sendProperty(sender, null, i.getRegistryName() + "");
				Messages.sendProperty(sender, "baseOutput", String.valueOf(i.getBaseOutput()), 1);
				Messages.sendProperty(sender, "musicMaximum", String.valueOf(i.getMusicMaximum()), 1);
			}
		});
	}
	
	// Prints list of mill names
	private void extrautilsHandler(ICommandSender sender, EntityPlayer player) {
		Map<String, float[]> generators = VExtraUtilities.getCurrentScaling();
		if (generators.size() != 0) {
			Messages.sendHeading(sender,"GP Mills:");
			if (player != null) {
				generators.forEach((name, scaling) -> {
					Messages.sendPropertyWithCopy(player, null, name, "Mills.setScaling(" + name + ", " + Messages.listToAssociativeArrayPretty(Arrays.asList(ArrayUtils.toObject(scaling)), true, 0) + ");");
				});
			} else {
				generators.forEach((name, scaling) -> {
					Messages.sendProperty(sender, null, name);
					Messages.sendProperty(sender, "Scaling", Messages.listToAssociativeArrayPretty(Arrays.asList(ArrayUtils.toObject(scaling)), true, 2), 1);
				});
			}
		} else {
			Messages.sendHeading(sender, "No GP mills found.");
		}
	}
	
	
	
	
	private static void executeHandCommand(MinecraftServer server, ICommandSender sender, String[] args) {
		if (sender.getCommandSenderEntity() instanceof EntityPlayer) {
			// Gets player and held item
			EntityPlayer player = (EntityPlayer) sender.getCommandSenderEntity();
			ItemStack heldItem = player.getHeldItemMainhand();
			
			// Tries to get name of held item first
			if (!heldItem.isEmpty()) {
				int meta = heldItem.getMetadata();
				String itemName = "<" + heldItem.getItem().getRegistryName() + (meta == 0 ? "" : ":" + meta) + ">";
				
				String withNBT = "";
				if (heldItem.serializeNBT().hasKey("tag")) {
					String nbt = NBTConverter.from(heldItem.serializeNBT().getTag("tag"), false).toString();
					if (nbt.length() > 0)
						withNBT = ".withTag(" + nbt + ")";
				}
				ClipboardHelper.sendMessageWithCopy(player, "Item §2" + itemName + "§a" + withNBT, itemName + withNBT);
				
				// adds liquid the item contains
				printLiquidInfo(sender, player, heldItem);
				
				
				List<String> oreDictNames = CommandUtils.getOreDictOfItem(heldItem);
				// adds the oredict names if it has some
				printOreDictInfo(sender, player, oreDictNames);
				
				
			} else {
				// if hand is empty, tries to get oreDict of block
				RayTraceResult rayTraceResult = CommandUtils.getPlayerLookat(player, 100);
				
				if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
					BlockPos blockPos = rayTraceResult.getBlockPos();
					IBlockState block = server.getEntityWorld().getBlockState(blockPos);
					
					int meta = block.getBlock().getMetaFromState(block);
					String blockName = "<" + block.getBlock().getRegistryName() + (meta == 0 ? "" : ":" + meta) + ">";
					
					ClipboardHelper.sendMessageWithCopy(player, "Block §2" + blockName + " §rat §9[" + blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ() + "]§r", blockName);
					
					// adds the oreDict names if it has some
					try {
						List<String> oreDictNames = CommandUtils.getOreDictOfItem(new ItemStack(block.getBlock(), 1, block.getBlock().getMetaFromState(block)));
						printOreDictInfo(sender, player, oreDictNames);
						
					} catch (IllegalArgumentException e) { // catches if it couldn't create a valid ItemStack for the Block
						Messages.sendHeading(sender, "No OreDict Entries.");
					}
					
				} else {
					Messages.send(sender,"§4Please hold an Item in your hand or look at a Block.");
				}
			}
		} else {
			sender.sendMessage(new TextComponentString("This command can only be casted by a player in-game."));
		}
	}
	
	private static void printLiquidInfo(ICommandSender sender, EntityPlayer player, ItemStack heldItem) {
		ILiquidStack liquidStack = CraftTweakerMC.getILiquidStack(FluidUtil.getFluidContained(heldItem));
		if (liquidStack != null) {
			String liquidCommandString = liquidStack.toCommandString();
			ClipboardHelper.sendMessageWithCopy(player, "Contains Liquid §2" + liquidCommandString, liquidCommandString);
			if (!printFluidReactorInteriorData(sender, player, liquidStack.getName()))
				Messages.sendHeading(sender, "No Reactor Interior Data.");
		}
	}
	
	private static void printOreDictInfo(ICommandSender sender, EntityPlayer player, List<String> oreDictNames) {
		if (!oreDictNames.isEmpty()) {
			Messages.sendHeading(sender, "OreDict Entries:");
			
			for (String oreName : oreDictNames) {
				Messages.sendPropertyWithCopy(player, null, oreName, "<ore:" + oreName + ">");
				if (!printOreDictReactorInteriorData(sender, player, oreName))
					Messages.sendHeading(sender,"No Reactor Interior Data.");
			}
		} else {
			Messages.sendHeading(sender,"No OreDict Entries");
		}
	}
	
	
	
	private static boolean printOreDictReactorInteriorData(ICommandSender sender, EntityPlayer player, String oreName) {
		ReactorInteriorData rid = ReactorInterior.getBlockData(oreName);
		return printReactorInteriorData(sender, player, rid);
	}
	
	private static boolean printFluidReactorInteriorData(ICommandSender sender, EntityPlayer player, String fluidName) {
		ReactorInteriorData rid = ReactorInterior.getFluidData(fluidName);
		return printReactorInteriorData(sender, player, rid);
	}
	
	private static boolean printReactorInteriorData(ICommandSender sender, EntityPlayer player, ReactorInteriorData rid) {
		if (rid != null) {
			Messages.sendHeading(sender, "Reactor Interior Data:");
			Messages.sendPropertyWithCopy(player, "absorption", String.valueOf(rid.absorption));
			Messages.sendPropertyWithCopy(player, "moderation", String.valueOf(rid.moderation));
			Messages.sendPropertyWithCopy(player, "heatEfficiency", String.valueOf(rid.heatEfficiency));
			Messages.sendPropertyWithCopy(player, "heatConductivity", String.valueOf(rid.heatConductivity));
			return true;
		}
		return false;
	}
	
	
	
	private static class Messages {
		private static final String INDENT = "    ";
		private static final char[] HIGHLIGHTING = {'b', '2'};
		
		private static String indent(int level) {
			return StringUtils.repeat(INDENT, level + 1);
		}
		
		static void send(ICommandSender sender, String message) {
			sender.sendMessage(new TextComponentString(message));
		}
		
		static void sendHeading(ICommandSender sender, String s) {
			send(sender, "§3" + s);
		}
		
		static void sendPropertyWithCopy(EntityPlayer player, String property, String value) {
			sendPropertyWithCopy(player, property, value, 0);
		}
		
		static void sendPropertyWithCopy(EntityPlayer player, String property, String value, int indent) {
			sendPropertyWithCopy(player, property, value, value, indent);
		}
		
		static void sendPropertyWithCopy(EntityPlayer player, String property, String value, String toCopy) {
			sendPropertyWithCopy(player, property, value, toCopy, 0);
		}
		
		static void sendPropertyWithCopy(EntityPlayer player, String property, String value, String toCopy, int indent) {
			ClipboardHelper.sendMessageWithCopy(player, getPropertyMessage(property, value, indent), toCopy);
		}
		static void sendProperty(ICommandSender sender, String property, String value) {
			sendProperty(sender, property, value, 0);
		}
		
		static void sendProperty(ICommandSender sender, String property, String value, int indent) {
			send(sender, getPropertyMessage(property, value, indent));
		}
		
		static String getPropertyMessage(String property, String value, int indent) {
			if (property == null || property.equals(""))
				property = "";
			else
				property += ": ";
			return indent(indent) + "§e- " + property + "§" + HIGHLIGHTING[indent] + value;
		}
		
		static String listToAssociativeArrayPretty(List<Object> list, boolean raw, int indent) {
			StringBuilder sb = new StringBuilder();
			
			Map<Object, Object> map = new HashMap<>();
			for (int i = 0; i < list.size(); i+=2) {
				Object o1 = list.get(i);
				Object o2;
				try {
					o2 = list.get(i+1);
				} catch (Exception e) {
					o2 = null;
				}
				map.put(o1, o2);
			}
			return mapToAssociativeArrayPretty(map, raw, indent);
		}
		
		static String mapToAssociativeArrayPretty(Map<Object, Object> map, boolean raw, int indent) {
			StringBuilder sb = new StringBuilder();
			if (!raw)
				sb.append(TextFormatting.YELLOW);
			sb.append("{\n");
			if (!raw)
				sb.append(indent(indent));
			map.forEach((x, y) -> {
				if (raw) {
					sb.append("\t").append(x).append(": ").append(y).append(",\r\n");
				} else {
					sb.append(INDENT)
							.append(TextFormatting.GREEN).append(x).append(TextFormatting.YELLOW).append(": ")
							.append(TextFormatting.AQUA).append(y).append(TextFormatting.YELLOW).append(",\n").append(indent(indent));
				}
			});
			if (!raw)
				sb.append(indent(indent)).append(TextFormatting.YELLOW);
			sb.append("}");
			return sb.toString();
		}
	}
}
