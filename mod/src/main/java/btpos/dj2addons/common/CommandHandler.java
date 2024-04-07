package btpos.dj2addons.common;

import btpos.dj2addons.DJ2Addons;
import btpos.dj2addons.api.bewitchment.Rituals;
import btpos.dj2addons.api.extrautils2.ExtraUtilities;
import btpos.dj2addons.common.modrefs.CBigReactors;
import btpos.dj2addons.common.modrefs.CBigReactors.ReactorInteriorDataWrapper;
import btpos.dj2addons.common.modrefs.CTotemic;
import btpos.dj2addons.common.modrefs.IsModLoaded;
import btpos.dj2addons.common.util.StringDumpUtils;
import btpos.dj2addons.common.util.Util;
import btpos.dj2addons.common.util.Util.DevTools;
import btpos.dj2addons.optimizations.impl.actuallyadditions.GraphNetwork;
import btpos.dj2addons.optimizations.impl.actuallyadditions.OptimizedLaserRelayConnectionHandler;
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
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.mod.data.WorldData;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.Loader;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static btpos.dj2addons.DJ2AMixinConfig.LOGGER;

@SuppressWarnings({"unused", "Inspection", "Guava"})
public class CommandHandler extends CraftTweakerCommand {
	private static Thread dumperThread = null;
	
	public CommandHandler() {
		super(DJ2Addons.MOD_ID);
	}
	
	@Override
	protected void init() {
		setDescription(new TextComponentTranslation("dj2addons.commands.desc"));
	}
	
	@Override
	public List<String> getSubSubCommand(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
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
		hand, info, mods, bewitchment, extrautils2, totemic, findextending, networks, clearnetworks
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
		
		List<String> params = Lists.newArrayList(args);
		
		//noinspection
		Optional<SubCommand> command = Enums.getIfPresent(SubCommand.class, params.remove(0));
		if (command.isPresent()) {
			MessageHelper m = new MessageHelper(sender);
			
			switch (command.get()) {
				case hand:
					executeHandCommand(server, sender, args);
					break;
				case info:
					m.setUsage("dj2addons.commands.info.usage");
					if (params.size() < 1)
						m.usage();
					else
						executeBlockInfoCommand(m.withChat(), sender, params);
					break;
//				case findextending:
//					TODO
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
				case networks: // DEBUG
					LOGGER.debug("[Command] Printing AA networks: ");
					((OptimizedLaserRelayConnectionHandler) ActuallyAdditionsAPI.connectionHandler).networkLookupMap.values().forEach(DJ2Addons.LOGGER::info);
					LOGGER.debug(((OptimizedLaserRelayConnectionHandler) ActuallyAdditionsAPI.connectionHandler).networkLookupMap);
					break;
				case clearnetworks:
					Map<BlockPos, GraphNetwork> networkLookupMap = ((OptimizedLaserRelayConnectionHandler) ActuallyAdditionsAPI.connectionHandler).networkLookupMap;
					networkLookupMap.values().forEach(network -> {
						network.nodeLookupMap.values().forEach(node -> {
							node.connections.clear();
							node.network = null;
						});
					});
					networkLookupMap.clear();
					WorldData.get(sender.getEntityWorld()).laserRelayNetworks.clear();
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
	
	
	
	private static void executeBlockInfoCommand(MessageHelper m, ICommandSender sender, List<String> args) {
		if (args.size() < 1) {
			m.usage();
			return;
		}
		
		switch (args.remove(0).toLowerCase(Locale.ROOT)) {
			case "blockmeta":
				info_printBlockMeta(m.withLog(), sender, args);
				break;
			case "class":
				info_printBlockClasses(m.withLog(), sender, args);
				break;
			case "capabilities":
				printBlockCapabilitiesBySide(m.withLog(), sender, args);
				break;
			case "dumptile":
				if (!sender.canUseCommand(4, "op")) {
					m.sendError("You do not have access to this command.");
					return;
				}
				try {
					info_dumpTileCommand(m.withLog(), sender, args);
				} catch (NumberFormatException | NumberInvalidException e) {
					m.usage();
				}
				break;
			default:
				m.usage();
		}
	}
	
	private static void info_printBlockMeta(MessageHelper m, ICommandSender sender, List<String> args) {
		m.setUsage("dj2addons.commands.info.blockmeta.usage");
		
		BlockPos target;
		// get target pos
		if (args.size() == 3) {
			try {
				target = CommandBase.parseBlockPos(sender, args.toArray(new String[0]), 1, true);
			} catch (NumberInvalidException e) {
				m.usage();
				return;
			}
		} else if (args.size() == 0) {
			if (!(sender.getCommandSenderEntity() instanceof EntityPlayer)) {
				m.usage();
				return;
			}
			
			target = getLookAtPos((EntityPlayer)sender.getCommandSenderEntity());
			if (target == null) {
				m.sendError("Command must specify coordinates or be executed while looking at a block.");
				return;
			}
		} else {
			m.usage();
			return;
		}
		
		IBlockState state = sender.getEntityWorld().getBlockState(target);
//		if (state.equals(Blocks.AIR.getDefaultState())) {
//			m.sendError("No block found at " + target);
//			return;
//		}
		printBlockMeta(m, target, state);
	}
	
	private static void info_dumpTileCommand(MessageHelper m, ICommandSender sender, List<String> args) throws NumberFormatException, NumberInvalidException {
		m.setUsage("dj2addons.commands.info.dump.usage");
		
		final int numSupers;
		final BlockPos target;
		
		if (args.size() <= 1) {
			if (!(sender.getCommandSenderEntity() instanceof EntityPlayer)) {
				m.usage();
				return;
			}
			
			if (args.size() == 1) {
				if ("stop".equalsIgnoreCase(args.get(0))) {
					info_stopDumpTile(m);
					return;
				}
				numSupers = Integer.parseInt(args.get(0));
			}
			else 
				numSupers = -1;
			
			target = getLookAtPos((EntityPlayer)sender.getCommandSenderEntity());
			if (target == null) {
				m.sendError("Command must specify coordinates or be executed while looking at a block.");
				return;
			}
		} else if (args.size() == 4) {
			target = CommandBase.parseBlockPos(sender, args.toArray(new String[0]), 1, true);
			numSupers = Integer.parseInt(args.remove(0));
		} else {
			m.usage();
			return;
		}
		
		TileEntity te = sender.getEntityWorld().getTileEntity(target);
		if (te == null) {
			m.sendError("No tile entity found at " + target);
			return;
		}
		
		if (dumperThread != null && dumperThread.isAlive()) {
			m.sendError("Tile dump already in progress.");
			m.sendRaw(SpecialMessagesChat.getClickableCommandText("Click to cancel current tile dump.", "/ct dj2addons info tiledump stop", true));
		}
		
		m.send("Starting tile data dump.");
		dumperThread = new Thread(() -> {
			StringDumpUtils.dump(te, m::log, numSupers);
			m.linkToLog();
		});
		dumperThread.start();
	}
	
	private static void info_stopDumpTile(MessageHelper m) {
		if (dumperThread == null || dumperThread.isAlive()) {
			m.send("No tile dump in progress.");
		} else {
			dumperThread.stop();
			if (dumperThread.isAlive()) {
				m.sendError("Failed to kill dumper thread.");
			} else {
				m.sendError("Killed dumper thread.");
				dumperThread = null;
			}
		}
	}
	
	private static void info_printBlockClasses(MessageHelper m, ICommandSender sender, List<String> args) {
		final BlockPos target;
		if (args.size() >= 3) {
			try {
				target = CommandBase.parseBlockPos(sender, args.toArray(new String[0]), 0, true);
			} catch (NumberInvalidException e) {
				m.usage();
				return;
			}
		} else {
			if (!(sender.getCommandSenderEntity() instanceof EntityPlayer)) {
				m.sendError("Command must either be run by a player looking at a block or must specify coordinates."); //TODO translate for OneOneTwoTwo
				return;
			}
			target = getLookAtPos((EntityPlayer) sender.getCommandSenderEntity());
			if (target == null) {
				m.sendError("Command must either be run by a player looking at a block or must specify coordinates.");
				return;
			}
		}
		Class<? extends Block> bc = sender.getEntityWorld().getBlockState(target).getBlock().getClass();
		m.sendHeading("Block:");
		m.sendPropertyWithCopy("Class", bc.getName());
		m.sendPropertyWithCopy("Extends Class", bc.getSuperclass().getName());
		if (bc.getDeclaringClass() != bc)
			m.sendPropertyWithCopy("Declared Inside", bc.getDeclaringClass().getName());
		m.sendProperty(null, "Implements Interfaces:");
		Arrays.stream(bc.getInterfaces()).forEach(c -> m.sendPropertyWithCopy(null, c.getName(), 1));
		
		TileEntity t = sender.getEntityWorld().getTileEntity(target);
		if (t == null) {
			m.sendHeading("Tile Entity: NONE");
			return;
		}
		m.sendHeading("Tile Entity:");
		Class<?> te = t.getClass();
		m.sendPropertyWithCopy("Class", te.getName());
		if (te.getDeclaringClass() != te && te.getDeclaringClass() != null)
			m.sendPropertyWithCopy("Declaring Class", te.getDeclaringClass().getName());
		m.sendProperty(null, "Implements Interfaces:");
		Arrays.stream(te.getInterfaces()).forEach(c -> m.sendPropertyWithCopy(null, c.getName(), 1));
	}
	
	private static void printBlockCapabilitiesBySide(MessageHelper m, ICommandSender sender, List<String> args) {
		final BlockPos target;
		if (args.size() >= 3) {
			try {
				target = CommandBase.parseBlockPos(sender, args.toArray(new String[0]), 0, true);
			} catch (NumberInvalidException e) {
				m.usage();
				return;
			}
		} else {
			if (!(sender.getCommandSenderEntity() instanceof EntityPlayer)) {
				m.sendError("Command must either be run by a player looking at a block or must specify coordinates."); //TODO translate for OneOneTwoTwo
				return;
			}
			target = getLookAtPos((EntityPlayer) sender.getCommandSenderEntity());
			if (target == null) {
				m.sendError("Command must either be run by a player looking at a block or must specify coordinates.");
				return;
			}
		}
		
		TileEntity te = sender.getEntityWorld().getTileEntity(target);
		if (te == null) {
			m.sendError("No tile entity found at " + target);
			return;
		}
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
		
		Rituals.getAllRituals().stream().filter(r -> !(r instanceof Rituals.Internal.DummyRitual))
		       .forEach(r -> m.sendPropertyWithCopy(null, r.getRegistryName() + ""));
		
		
		if (Rituals.Internal.getRitualsToRemove().size() == 0) {
			m.sendHeading("No removed rituals.");
		} else {
			m.sendHeading("Removed rituals:");
			
			Rituals.Internal.getRitualsToRemove()
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
		Map<String, float[]> generators = ExtraUtilities.Internal.getScalingMap();
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
		
		m.sendMessageWithCopy(formatPos(blockPos, blockName) + "§r", blockName);
		
		// adds the oreDict names if it has some
		try {
			List<String> oreDictNames = CommandUtils.getOreDictOfItem(new ItemStack(block.getBlock(), 1, block.getBlock().getMetaFromState(
					block)));
			printOreDictInfo(m, oreDictNames);
			
		} catch (IllegalArgumentException e) { // catches if it couldn't create a valid ItemStack for the Block
			m.sendHeading("No OreDict Entries.");
		}
	}
	
	@NotNull
	private static String formatPos(BlockPos blockPos, String blockName) {
		return "Block §2" + blockName + " §rat §9" + Util.Format.formatPos(blockPos);
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
		
		@SuppressWarnings("UnusedReturnValue")
		public MessageHelper setUsage(String translationKey) {
			this.usage = translationKey;
			return this;
		}
		
		private static final String INDENT = "    ";
		private static final char[] HIGHLIGHTING = {'b', '2'};
		
		void send(String message) {
			sendRaw(new TextComponentString(message));
		}
		
		void log(String message) {
			CraftTweakerAPI.logCommand(message);
		}
		
		void sendRaw(ITextComponent textComponent) {
			if (doChat)
				sender.sendMessage(textComponent);
			if (doLog)
				log(textComponent.toString());
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
			else if (property.charAt(property.length() - 1) == ':') {
				property += " ";
			} else { //noinspection StatementWithEmptyBody
				if (property.charAt(property.length() - 2) == ':' && property.charAt(property.length() - 1) == ' ') {} // leave alone
				else property += ": ";
			}
			return indent(indent) + "§e- " + property + "§" + HIGHLIGHTING[indent] + value;
		}
		
		static <T> String listToAssociativeArrayPretty(List<T> list, boolean raw, int indent) {
			Map<T, T> map = new HashMap<>();
			for (int i = 0; i < list.size(); i+=2) {
				T o1 = list.get(i);
				T o2;
				try {
					o2 = list.get(i+1);
				} catch (Exception e) {
					o2 = null;
				}
				map.put(o1, o2);
			}
			return mapToAssociativeArrayPretty(map, raw, indent);
		}
		
		static <T> String mapToAssociativeArrayPretty(Map<T, T> map, boolean raw, int indent) {
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
