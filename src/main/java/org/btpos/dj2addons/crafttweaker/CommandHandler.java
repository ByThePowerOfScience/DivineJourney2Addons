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
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.btpos.dj2addons.DJ2Addons;
import org.btpos.dj2addons.impl.bewitchment.VModRecipes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pokefenn.totemic.api.music.MusicInstrument;

import java.util.Arrays;
import java.util.List;
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
		hand, mods, bewitchment, totemic
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
		sender.sendMessage(new TextComponentString("§3Ritual IDs:"));
		CraftTweakerAPI.logInfo("Bewitchment Rituals:");
		VModRecipes.getAllRituals().stream().filter(r -> !(r instanceof VModRecipes.DummyRitual))
				.forEach(r -> {
					CraftTweakerAPI.logInfo("- " + r.getRegistryName());
					if (player != null)
						ClipboardHelper.sendMessageWithCopy(player, "    §e- §b" + r.getRegistryName(), r.getRegistryName() + "");
					else
						sender.sendMessage(new TextComponentString("    §e- §b" + r.getRegistryName()));
				});
		
		if (VModRecipes.getRitualsToRemove().size() == 0) {
			sender.sendMessage(new TextComponentString("§3No removed rituals."));
			CraftTweakerAPI.logInfo("\nNo removed rituals.");
		} else {
			sender.sendMessage(new TextComponentString("§3Removed rituals:"));
			CraftTweakerAPI.logInfo("\nRemoved rituals:");
			VModRecipes.getRitualsToRemove()
					.forEach(r -> {
						CraftTweakerAPI.logInfo("- " + r.getRegistryName());
						if (player != null)
							ClipboardHelper.sendMessageWithCopy(player, "    §e- §b" + r.getRegistryName(), r.getRegistryName() + "");
						else
							sender.sendMessage(new TextComponentString("    §e- §b" + r.getRegistryName()));
					});
		}
	}
	
	
	private void totemicHandler(@NotNull ICommandSender sender, EntityPlayer player) {
		sender.sendMessage(new TextComponentString("§3Instruments:"));
		CraftTweakerAPI.logInfo("Totemic Instruments:");
		GameRegistry.findRegistry(MusicInstrument.class).forEach(i -> {
			CraftTweakerAPI.logInfo("- " + i.getRegistryName() + " [baseOutput: " + i.getBaseOutput() + ", musicMaximum: " + i.getMusicMaximum() + "]");
			if (player != null) {
				ClipboardHelper.sendMessageWithCopy(player, "    §e- §b" + i.getRegistryName(), i.getRegistryName() + "");
				ClipboardHelper.sendMessageWithCopy(player, "        §e- §2baseOutput: " + i.getBaseOutput(), i.getBaseOutput() + "");
				ClipboardHelper.sendMessageWithCopy(player, "        §e- §2musicMaximum: " + i.getMusicMaximum(), i.getMusicMaximum() + "");
			} else {
				sender.sendMessage(new TextComponentString("    §e- §b" + i.getRegistryName()));
				sender.sendMessage(new TextComponentString("        §e- §2baseOutput: " + i.getBaseOutput()));
				sender.sendMessage(new TextComponentString("        §e- §2musicMaximum: " + i.getMusicMaximum()));
			}
		});
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
				ClipboardHelper.sendMessageWithCopy(player, "Item \u00A72" + itemName + "\u00A7a" + withNBT, itemName + withNBT);
				
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
					
					ClipboardHelper.sendMessageWithCopy(player, "Block \u00A72" + blockName + " \u00A7rat \u00A79[" + blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ() + "]\u00A7r", blockName);
					
					// adds the oreDict names if it has some
					try {
						List<String> oreDictNames = CommandUtils.getOreDictOfItem(new ItemStack(block.getBlock(), 1, block.getBlock().getMetaFromState(block)));
						printOreDictInfo(sender, player, oreDictNames);
						
					} catch (IllegalArgumentException e) { // catches if it couldn't create a valid ItemStack for the Block
						sender.sendMessage(new TextComponentString("\u00A73No OreDict Entries."));
					}
					
				} else {
					sender.sendMessage(new TextComponentString("\u00A74Please hold an Item in your hand or look at a Block."));
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
			ClipboardHelper.sendMessageWithCopy(player, "Contains Liquid \u00A72" + liquidCommandString, liquidCommandString);
			if (!printFluidReactorInteriorData(sender, player, liquidStack.getName()))
				sender.sendMessage(new TextComponentString("§3No Reactor Interior Data."));
		}
	}
	
	private static void printOreDictInfo(ICommandSender sender, EntityPlayer player, List<String> oreDictNames) {
		if (!oreDictNames.isEmpty()) {
			sender.sendMessage(new TextComponentString("\u00A73OreDict Entries:"));
			
			for (String oreName : oreDictNames) {
				ClipboardHelper.sendMessageWithCopy(player, "    \u00A7e- \u00A7b" + oreName, "<ore:" + oreName + ">");
				if (!printOreDictReactorInteriorData(sender, player, oreName))
					sender.sendMessage(new TextComponentString("§3No Reactor Interior Data."));
			}
		} else {
			sender.sendMessage(new TextComponentString("\u00A73No OreDict Entries"));
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
			sender.sendMessage(new TextComponentString("§3Reactor Interior Data:"));
			ClipboardHelper.sendMessageWithCopy(player, "    §e- absorption: §b" + rid.absorption, String.valueOf(rid.absorption));
			ClipboardHelper.sendMessageWithCopy(player, "    §e- moderation: §b" + rid.moderation, String.valueOf(rid.moderation));
			ClipboardHelper.sendMessageWithCopy(player, "    §e- heatEfficiency: §b" + rid.heatEfficiency, String.valueOf(rid.heatEfficiency));
			ClipboardHelper.sendMessageWithCopy(player, "    §e- heatConductivity: §b" + rid.heatConductivity, String.valueOf(rid.heatConductivity));
			return true;
		}
		return false;
	}
}
