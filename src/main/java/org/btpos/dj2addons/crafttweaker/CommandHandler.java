package org.btpos.dj2addons.crafttweaker;

import com.google.common.base.Enums;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.commands.ClipboardHelper;
import crafttweaker.mc1120.commands.CommandUtils;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import crafttweaker.mc1120.commands.SpecialMessagesChat;
import crafttweaker.mc1120.data.NBTConverter;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.Loader;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.btpos.dj2addons.DJ2Addons;
import org.btpos.dj2addons.impl.api.bewitchment.VModRecipes;
import org.btpos.dj2addons.impl.api.extrautilities.VExtraUtilities;
import org.btpos.dj2addons.impl.modrefs.CBigReactors;
import org.btpos.dj2addons.impl.modrefs.CBigReactors.ReactorInteriorDataWrapper;
import org.btpos.dj2addons.impl.modrefs.CTotemic;
import org.btpos.dj2addons.impl.modrefs.IsModLoaded;
import org.btpos.dj2addons.util.StringDumpUtils;
import org.btpos.dj2addons.util.Util.DevTools;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings({"unused","Inspection"})
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
		return Arrays.stream(SubCommand.values()).filter(sc -> {
			switch (sc) {
				case bewitchment:
				case totemic:
				case extrautils2:
					return Loader.isModLoaded(sc.name());
				default:
					return true;
			}
		}).map(Enum::toString).collect(Collectors.toList());
	}
	
	private enum SubCommand {
		hand, tileinfo, mods, bewitchment, extrautils2, totemic
//		,validate
	}
	
	@Override
	public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
		if (sender == null)
			return;
		if (args.length < 1) {
			sender.sendMessage(new TextComponentTranslation("dj2addons.commands.usage"));
			return;
		}
		
		//noinspection
		Optional<SubCommand> command = Enums.getIfPresent(SubCommand.class, args[0]);
		if (command.isPresent()) {
			MessageHelper m = new MessageHelper(sender);
			List<String> strings = Lists.newArrayList(args);
			strings.remove(0);
			args = strings.toArray(new String[0]);
			
			switch (command.get()) {
				case hand:
					executeHandCommand(server, sender, args);
					break;
				case tileinfo:
					executeBlockDumpCommand(m.withChat().setUsage("dj2addons.commands.dumpblock.usage"), sender, args);
					break;
//				case validate:
//					m.withChat().send(String.join("\n", VCraftTweaker.validate().toArray(new String[0])));
//					break;
				case mods:
				case bewitchment:
					if (Loader.isModLoaded("bewitchment"))
						bewitchmentHandler(m.withLog());
					if (command.get().equals(SubCommand.bewitchment))
						break;
				case totemic:
					if (Loader.isModLoaded("totemic"))
						totemicHandler(m.withLog());
					if (command.get().equals(SubCommand.totemic))
						break;
				case extrautils2:
					if (Loader.isModLoaded("extrautils2"))
						extrautilsHandler(m.withChat());
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
	
	
	
	private static void executeBlockDumpCommand(MessageHelper m, ICommandSender sender, String[] args) {
		if (args.length < 1) {
			m.usage();
			return;
		}
		BlockPos target;
		try {
			target = getTargetedPosition(sender, args, m);
		} catch (Exception e) {
			m.usage();
			return;
		}
		if (target == null) {
			m.sendError("Command must be executed with a block targeted.");
			return;
		}
		switch (args[0]) {
			case "block":
				printBlockMeta(m.withLog(), target, sender.getEntityWorld().getBlockState(target));
				break;
			case "capabilities":
				TileEntity te = sender.getEntityWorld().getTileEntity(target);
				if (te == null) {
					m.sendError("No tile entity found at " + target);
					return;
				}
				listBlockCapabilitiesBySide(m.withLog(), te);
				break;
			case "dump":
				m.setUsage("dj2addons.commands.dumpblock.dump.usage");
				if (!sender.canUseCommand(4, "op")) {
					m.sendError("You do not have access to this command.");
					return;
				}
				TileEntity te2 = sender.getEntityWorld().getTileEntity(target);
				if (te2 == null) {
					m.sendError("No tile entity found at " + target);
					return;
				}
				new Thread(() -> {
					try {
						StringDumpUtils.dump(te2, m::log, args.length >= 2 ? Integer.parseInt(args[1]) : 0);
						m.linkToLog();
					} catch (NumberFormatException e) {
						m.usage();
					}
				}).start();
				break;
			default:
				m.usage();
		}
	}
	
	private static void listBlockCapabilitiesBySide(MessageHelper m, TileEntity te) {
		Map<EnumFacing, List<Capability<?>>> blockCapabilitiesBySide = getBlockCapabilitiesBySide(m, te);
		if (blockCapabilitiesBySide == null)
			return;
		m.sendHeading("Capabilities:");
		blockCapabilitiesBySide.forEach((k, v) -> {
			m.sendProperty(null, k == null ? "Innate:" : k.getName() + ":");
			v.forEach(c -> m.sendProperty(null, c.getName(), 1));
		});
	}
	
	private static Map<EnumFacing, List<Capability<?>>> getBlockCapabilitiesBySide(MessageHelper m, TileEntity te) {
		Map<String, Capability<?>> allCapabilities = DevTools.getAllCapabilities();
		if (allCapabilities == null) {
			m.sendError("Could not access Capability registry.");
			return null;
		}
		Map<EnumFacing, List<Capability<?>>> capsBySide = new HashMap<>(7);
		for (EnumFacing direction : EnumFacing.VALUES)
			capsBySide.put(direction, new ArrayList<>());
		capsBySide.put(null, new ArrayList<>());
		
		for (Capability<?> c : allCapabilities.values()) {
			for (EnumFacing direction : EnumFacing.VALUES) {
				if (te.hasCapability(c, direction))
					capsBySide.get(direction).add(c);
			}
			if (te.hasCapability(c, null)) {
				capsBySide.get(null).add(c);
			}
		}
		return capsBySide;
	}
	
	
	
	
	
	
	
	private static void bewitchmentHandler(MessageHelper m) {
		m.sendHeading("Rituals:");
		
		VModRecipes.getAllRituals().stream().filter(r -> !(r instanceof VModRecipes.DummyRitual))
				.forEach(r -> m.sendPropertyWithCopy(null, r.getRegistryName() + ""));
		
		
		if (VModRecipes.getRitualsToRemove().size() == 0) {
			m.sendHeading("No removed rituals.");
		} else {
			m.sendHeading("Removed rituals:");
			
			VModRecipes.getRitualsToRemove()
					.forEach(r -> m.sendPropertyWithCopy(null, r.getRegistryName() + ""));
			
		}
	}
	
	
	@SuppressWarnings({"raw"})
	private static void totemicHandler(MessageHelper m) {
		m.sendHeading("Instruments:");
		CTotemic.getInstrumentRegistry().forEach(i -> {
			m.sendPropertyWithCopy(null, i.getRegistryName() + "");
			m.sendPropertyWithCopy("baseOutput", String.valueOf(CTotemic.getBaseOutput(i)), 1);
			m.sendPropertyWithCopy("musicMaximum", String.valueOf(CTotemic.getMusicMaximum(i)), 1);
		});
	}
	
	// Prints list of mill names
	private static void extrautilsHandler(MessageHelper m) {
		Map<String, float[]> generators = VExtraUtilities.getCurrentScaling();
		if (generators.size() != 0) {
			m.sendHeading("GP Mills:");
			
			generators.forEach((name, scaling) -> m.sendPropertyWithCopy(null, name, "Mills.setScaling(" + name + ", " + MessageHelper.listToAssociativeArrayPretty(Arrays.asList(ArrayUtils.toObject(scaling)), true, 0) + ");"));
		} else {
			m.sendHeading("No GP mills found.");
		}
	}
	
	
	
	private static void executeHandCommand(MinecraftServer server, ICommandSender sender, String[] args) {
		MessageHelper m = new MessageHelper(sender).withChat().withLog();
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
				m.sendMessageWithCopy("Item §2" + itemName + "§a" + withNBT, itemName + withNBT);
				
				// adds liquid the item contains
				printLiquidInfo(m, heldItem);
				
				List<String> oreDictNames = CommandUtils.getOreDictOfItem(heldItem);
				// adds the oredict names if it has some
				printOreDictInfo(m, oreDictNames);
			} else {
				// if hand is empty, tries to get oreDict of block
				RayTraceResult rayTraceResult = CommandUtils.getPlayerLookat(player, 100);
				
				if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
					BlockPos blockPos = rayTraceResult.getBlockPos();
					IBlockState block = server.getEntityWorld().getBlockState(blockPos);
					
					printBlockMeta(m, blockPos, block);
				} else {
					m.send("§4Please hold an Item in your hand or look at a Block.");
				}
			}
		} else {
			sender.sendMessage(new TextComponentString("This command can only be casted by a player in-game."));
		}
	}
	
	private static void printBlockMeta(MessageHelper m, BlockPos blockPos, IBlockState block) {
		m.sendHeading("Block State:");
		int meta = block.getBlock().getMetaFromState(block);
		String blockName = "<" + block.getBlock().getRegistryName() + (meta == 0 ? "" : ":" + meta) + ">";
		
		m.sendMessageWithCopy("Block §2" + blockName + " §rat §9[" + blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ() + "]§r", blockName);
		
		// adds the oreDict names if it has some
		try {
			List<String> oreDictNames = CommandUtils.getOreDictOfItem(new ItemStack(block.getBlock(), 1, block.getBlock().getMetaFromState(
					block)));
			printOreDictInfo(m, oreDictNames);
			
		} catch (IllegalArgumentException e) { // catches if it couldn't create a valid ItemStack for the Block
			m.sendHeading("No OreDict Entries.");
		}
	}
	
	private static void printLiquidInfo(MessageHelper m, ItemStack heldItem) {
		ILiquidStack liquidStack = CraftTweakerMC.getILiquidStack(FluidUtil.getFluidContained(heldItem));
		if (liquidStack != null) {
			String liquidCommandString = liquidStack.toCommandString();
			m.sendMessageWithCopy("Contains Liquid §2" + liquidCommandString, liquidCommandString);
			if (IsModLoaded.bigreactors)
				if (!printFluidReactorInteriorData(m, liquidStack.getName()))
					m.sendHeading("No Reactor Interior Data.");
		}
	}
	
	private static void printOreDictInfo(MessageHelper m, List<String> oreDictNames) {
		if (!oreDictNames.isEmpty()) {
			m.sendHeading("OreDict Entries:");
			
			for (String oreName : oreDictNames) {
				m.sendPropertyWithCopy(null, oreName, "<ore:" + oreName + ">");
				if (IsModLoaded.bigreactors)
					if (!printOreDictReactorInteriorData(m, oreName))
						m.sendHeading("No Reactor Interior Data.");
			}
		} else {
			m.sendHeading("No OreDict Entries");
		}
	}
	
	
	private static boolean printOreDictReactorInteriorData(MessageHelper m, String oreName) {
		ReactorInteriorDataWrapper rid = CBigReactors.getBlockData(oreName);
		return printReactorInteriorData(m, rid);
	}
	
	private static boolean printFluidReactorInteriorData(MessageHelper m, String fluidName) {
		ReactorInteriorDataWrapper rid = CBigReactors.getFluidData(fluidName);
		return printReactorInteriorData(m, rid);
	}
	
	private static boolean printReactorInteriorData(MessageHelper m, ReactorInteriorDataWrapper rid) {
		if (rid != null) {
			m.sendHeading("Reactor Interior Data:");
			m.sendPropertyWithCopy("absorption", String.valueOf(rid.absorption()));
			m.sendPropertyWithCopy("moderation", String.valueOf(rid.moderation()));
			m.sendPropertyWithCopy("heatEfficiency", String.valueOf(rid.heatEfficiency()));
			m.sendPropertyWithCopy("heatConductivity", String.valueOf(rid.heatConductivity()));
			return true;
		}
		return false;
	}
	
	static BlockPos getLookAtPos(EntityPlayer player) {
		RayTraceResult rayTraceResult = CommandUtils.getPlayerLookat(player, 100);
		
		if (rayTraceResult != null && rayTraceResult.typeOfHit != Type.MISS) {
			return rayTraceResult.getBlockPos();
		}
		
		return null;
	}
	
	@Nullable
	private static BlockPos getTargetedPosition(ICommandSender sender, String[] args, MessageHelper m) throws Exception {
		if (args.length == 1 && !(sender.getCommandSenderEntity() instanceof EntityPlayer)) {
			m.sendError("Command must be executed by a player or include coordinates.");
			throw new Exception();
		}
		BlockPos target;
		if (args.length >= 4) {
			target = CommandBase.parseBlockPos(sender, args, 1, false);
		} else {
			target = getLookAtPos((EntityPlayer) sender.getCommandSenderEntity());
		}
		
		return target;
	}
	
	
	private static class MessageHelper {
		private final ICommandSender sender;
		private boolean doLog = false;
		private boolean doChat = false;
		private String usage = null;
		
		public MessageHelper(ICommandSender sender) {
			this.sender = sender;
		}
		
		public MessageHelper withChat() {
			doChat = true;
			return this;
		}
		
		public MessageHelper withLog() {
			doLog = true;
			return this;
		}
		
		public MessageHelper setUsage(String translationKey) {
			this.usage = translationKey;
			return this;
		}
		
		private static final String INDENT = "    ";
		private static final char[] HIGHLIGHTING = {'b', '2'};
		
		void send(String message) {
			if (doChat)
				sender.sendMessage(new TextComponentString(message));
			if (doLog)
				log(new TextComponentString(message).toString());
		}
		
		void log(String message) {
			CraftTweakerAPI.logCommand(message);
		}
		
		void usage() {
			if (this.usage != null)
				sender.sendMessage(new TextComponentTranslation(usage));
		}
		
		void linkToLog() {
			sender.sendMessage(SpecialMessagesChat.getLinkToCraftTweakerLog("Printed to CraftTweaker log.", sender));
		}
		
		void sendHeading(String s) {
			send("§3" + s);
		}
		
		void sendError(String s) {
			send("§4" + s);
		}
		
		void sendPropertyWithCopy(String property, String value) {
			sendPropertyWithCopy(property, value, 0);
		}
		
		void sendPropertyWithCopy(String property, String value, int indent) {
			sendPropertyWithCopy(property, value, value, indent);
		}
		
		void sendPropertyWithCopy(String property, String value, String toCopy) {
			sendPropertyWithCopy(property, value, toCopy, 0);
		}
		
		void sendPropertyWithCopy(String property, String value, String toCopy, int indent) {
			sendMessageWithCopy(getPropertyMessage(property, value, indent), toCopy);
		}
		
		private void sendMessageWithCopy(String message, String toCopy) {
			if (doChat) {
				if (sender.getCommandSenderEntity() instanceof EntityPlayer)
					ClipboardHelper.sendMessageWithCopy((EntityPlayer) sender.getCommandSenderEntity(), message, toCopy);
				else
					send(message);
			}
			if (doLog)
				log(message);
		}
		
		void sendProperty(String property, String value) {
			sendProperty(property, value, 0);
		}
		
		void sendProperty(String property, String value, int indent) {
			send(getPropertyMessage(property, value, indent));
		}
		
		private static String indent(int level) {
			return StringUtils.repeat(INDENT, level + 1);
		}
		
		static String getPropertyMessage(String property, String value, int indent) {
			if (property == null || property.equals(""))
				property = "";
			else
				property += ": ";
			return indent(indent) + "§e- " + property + "§" + HIGHLIGHTING[indent] + value;
		}
		
		static String listToAssociativeArrayPretty(List<Object> list, boolean raw, int indent) {
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
