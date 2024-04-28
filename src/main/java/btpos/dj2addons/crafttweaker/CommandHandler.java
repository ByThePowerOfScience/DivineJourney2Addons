package btpos.dj2addons.crafttweaker;

import btpos.dj2addons.core.DJ2Addons;
import btpos.dj2addons.api.bewitchment.Rituals;
import btpos.dj2addons.api.extrautils2.ExtraUtilities;
import btpos.dj2addons.commands.util.DJ2ACommandUtils;
import btpos.dj2addons.commands.util.MessageHelper;
import btpos.dj2addons.common.modrefs.CBigReactors;
import btpos.dj2addons.common.modrefs.CBigReactors.ReactorInteriorDataWrapper;
import btpos.dj2addons.common.modrefs.CBotania;
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
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.commands.CommandUtils;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import crafttweaker.mc1120.commands.NBTUtils;
import crafttweaker.mc1120.commands.SpecialMessagesChat;
import crafttweaker.mc1120.data.NBTConverter;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.mod.data.WorldData;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase.NBTType;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.Loader;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

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
				case botania:
					return Loader.isModLoaded(sc.name());
				default:
					return true;
			}
		}).map(Enum::toString).collect(Collectors.toList());
	}
	
	private enum SubCommand {
		hand, info, mods, bewitchment, extrautils2, totemic, findextending, networks, clearnetworks, syncablenbt, breakdebug, botania
//		,validate
	}
	
	@Override
	public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
		if (sender == null) {
			return;
		}
		
		MessageHelper m = new MessageHelper(sender, "dj2addons.commands").withChat().setUsageSuffix("usage");
		if (args.length < 1) {
			m.usage();
			return;
		}
		
		List<String> params = Lists.newArrayList(args);
		
		//noinspection
		Optional<SubCommand> command = Enums.getIfPresent(SubCommand.class, params.remove(0));
		if (command.isPresent()) {
			SubCommand subCommand = command.get();
			switch (subCommand) {
				case hand:
					executeHandCommand(m, server, sender, args);
					break;
				case info:
					executeBlockInfoCommand(m, sender, params);
					break;
//				case findextending:
//					TODO
//				case validate:
//					m.withChat().send(String.join("\n", VCraftTweaker.validate().toArray(new String[0])));
//					break;
				case breakdebug:
					System.out.println();
					break;
				case syncablenbt: // DEBUG
					BlockPos lookAtPos = DJ2ACommandUtils.getLookAtPos(((EntityPlayer) sender.getCommandSenderEntity()));
					if (lookAtPos == null) {
						m.sendError("Null pos");
						return;
					}
					TileEntity tileEntity = sender.getEntityWorld()
					                              .getTileEntity(lookAtPos);
					if(!(tileEntity instanceof TileEntityBase)) {
						m.sendError("No tile entity");
						return;
					}
					NBTTagCompound nbt = new NBTTagCompound();
					((TileEntityBase) tileEntity).writeSyncableNBT(nbt, NBTType.SYNC);
					m.sendHeading("Syncable NBT for " + DJ2ACommandUtils.formatPos(lookAtPos));
					m.send(NBTUtils.getAppealingString(nbt.toString()));
					break;
				case clearnetworks:
					Map<BlockPos, GraphNetwork> networkLookupMap = ((OptimizedLaserRelayConnectionHandler) ActuallyAdditionsAPI.connectionHandler).networkLookupMap;
					networkLookupMap.values().forEach(network -> {
						network.nodeLookupMap.values().forEach(node -> {
							node.connections.clear();
							node.network = null;
						});
					});
					GraphNetwork.debug$idCount = 0;
					networkLookupMap.clear();
					WorldData worldData = WorldData.get(sender.getEntityWorld());
					worldData.laserRelayNetworks.clear();
					worldData.markDirty();
					break;
				case mods:
				case bewitchment:
					if (Loader.isModLoaded("bewitchment"))
						bewitchmentHandler(m.withLog());
					else
						m.usage();
					if (subCommand == SubCommand.bewitchment)
						break;
				case totemic:
					if (Loader.isModLoaded("totemic"))
						totemicHandler(m.withLog());
					else
						m.usage();
					if (subCommand == SubCommand.totemic)
						break;
				case extrautils2:
					if (Loader.isModLoaded("extrautils2"))
						extrautilsHandler(m.withChat());
					else
						m.usage();
					if (subCommand == SubCommand.extrautils2)
						break;
				case botania:
					if (Loader.isModLoaded("botania"))
						botaniaHandler(m.withChat().withLog());
					else
						m.usage();
					
			}
			switch (subCommand) {
				case mods:
				case bewitchment:
				case totemic:
				case botania:
					sender.sendMessage(SpecialMessagesChat.getLinkToCraftTweakerLog("Printed to CraftTweaker log.", sender));
			}
		} else {
			sender.sendMessage(new TextComponentTranslation("dj2addons.commands.usage"));
		}
		
	}
	
	private void botaniaHandler(MessageHelper messageHelper) {
		CBotania.printAllBrews(messageHelper);
	}
	
	
	private static void executeBlockInfoCommand(MessageHelper m, ICommandSender sender, List<String> args) {
		m.enterSubCommand("info").withChat().withLog();
		if (args.isEmpty()) {
			m.usage();
			return;
		}
		
		switch (args.remove(0).toLowerCase(Locale.ROOT)) {
			case "blockmeta":
				info_printBlockMeta(m, sender, args);
				break;
			case "class":
				info_printBlockClasses(m, sender, args);
				break;
			case "capabilities":
				printBlockCapabilitiesBySide(m, sender, args);
				break;
			case "nbt":
				info_printNBT(m, sender, args);
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
	
	private static void info_printNBT(MessageHelper m, ICommandSender sender, List<String> args) {
		m.enterSubCommand("nbt");
		
		if (!(sender instanceof EntityPlayer) && args.size() < 3) {
			m.sendError("Non-players must specify coordinates.");
			return;
		}
		
		Object target = null;
		if (args.size() == 3) {
			try {
				target = CommandBase.parseBlockPos(sender, args.toArray(new String[0]), 1, true);
			} catch (NumberInvalidException e) {
				m.sendError("Invalid position provided.");
			}
		} else if (sender instanceof EntityPlayer) {
			RayTraceResult rayTraceResult = CommandUtils.getPlayerLookat((EntityPlayer) sender.getCommandSenderEntity(), 100);
			if (rayTraceResult.typeOfHit == Type.BLOCK)
				target = rayTraceResult.getBlockPos();
			else if (rayTraceResult.typeOfHit == Type.ENTITY)
				target = rayTraceResult.entityHit;
		} else {
			m.sendError("Non-players must provide coordinates.");
		}
		
		if (target == null) { // catches any errors
			m.usage();
			return;
		} else if (target instanceof BlockPos) { // turn blockpos into tileentity
			TileEntity te = sender.getEntityWorld().getTileEntity(((BlockPos) target));
			if (te != null) {
				target = te;
			} else {
				m.sendError("No tile entity found at position " + Util.Format.formatPos(((BlockPos) target)));
				return;
			}
		}
		
		m.send(NBTUtils.getAppealingString(((INBTSerializable<?>) target).serializeNBT().toString()));
	}
	
	private static void info_printBlockMeta(MessageHelper m, ICommandSender sender, List<String> args) {
		m.enterSubCommand("blockmeta");
		
		BlockPos target;
		// get target pos
		if (args.size() == 3) {
			try {
				target = CommandBase.parseBlockPos(sender, args.toArray(new String[0]), 1, true);
			} catch (NumberInvalidException e) {
				m.usage();
				return;
			}
		} else if (args.isEmpty()) {
			if (!(sender.getCommandSenderEntity() instanceof EntityPlayer)) {
				m.usage();
				return;
			}
			
			target = DJ2ACommandUtils.getLookAtPos((EntityPlayer)sender.getCommandSenderEntity());
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
		DJ2ACommandUtils.printBlockMeta(m, target, state);
	}
	
	private static void info_dumpTileCommand(MessageHelper m, ICommandSender sender, List<String> args) throws NumberFormatException, NumberInvalidException {
		m.enterSubCommand("dump");
		
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
			
			target = DJ2ACommandUtils.getLookAtPos((EntityPlayer)sender.getCommandSenderEntity());
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
			m.sendComponent(SpecialMessagesChat.getClickableCommandText("Click to cancel current tile dump.", "/ct dj2addons info tiledump stop", true));
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
			try {
				dumperThread.interrupt();
			} catch (Exception ignored) {}
			
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
			target = DJ2ACommandUtils.getLookAtPos((EntityPlayer) sender.getCommandSenderEntity());
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
			target = DJ2ACommandUtils.getLookAtPos((EntityPlayer) sender.getCommandSenderEntity());
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
		m.enterSubCommand("bewitchment");
		
		m.sendHeading("Rituals:");
		
		Rituals.getAllRituals().stream().filter(r -> !(r instanceof Rituals.Internal.DummyRitual))
		       .forEach(r -> m.sendPropertyWithCopy(null, r.getRegistryName() + ""));
		
		
		if (Rituals.Internal.getRitualsToRemove().isEmpty()) {
			m.sendHeading("No removed rituals.");
		} else {
			m.sendHeading("Removed rituals:");
			
			Rituals.Internal.getRitualsToRemove()
			                .forEach(r -> m.sendPropertyWithCopy(null, r.getRegistryName() + ""));
			
		}
	}
	
	
	@SuppressWarnings({"raw"})
	private static void totemicHandler(MessageHelper m) {
		m.enterSubCommand("totemic");
		m.sendHeading("Instruments:");
		CTotemic.getInstrumentRegistry().forEach(i -> {
			m.sendPropertyWithCopy(null, i.getRegistryName() + "");
			m.sendPropertyWithCopy("baseOutput", String.valueOf(CTotemic.getBaseOutput(i)), 1);
			m.sendPropertyWithCopy("musicMaximum", String.valueOf(CTotemic.getMusicMaximum(i)), 1);
		});
	}
	
	// Prints list of mill names
	private static void extrautilsHandler(MessageHelper m) {
		m.enterSubCommand("extrautils");
		Map<String, float[]> generators = ExtraUtilities.Internal.getScalingMap();
		if (!generators.isEmpty()) {
			m.sendHeading("GP Mills:");
			
			generators.forEach((name, scaling) -> m.sendPropertyWithCopy(null, name, "Mills.setScaling(" + name + ", " + m.listToAssociativeArrayPretty(Arrays.asList(ArrayUtils.toObject(scaling)), true, 0) + ");"));
		} else {
			m.sendHeading("No GP mills found.");
		}
	}
	
	
	
	private static void executeHandCommand(MessageHelper m, MinecraftServer server, ICommandSender sender, String[] args) {
		m.enterSubCommand("hand").withChat().withLog();
		if (sender.getCommandSenderEntity() instanceof EntityPlayer) {
			// Gets player and held item
			EntityPlayer player = (EntityPlayer) sender.getCommandSenderEntity();
			ItemStack heldItem = player.getHeldItemMainhand();
			
			
			// Tries to get name of held item first
			if (!heldItem.isEmpty()) {
				int meta = heldItem.getMetadata();
				String itemName = "<" + heldItem.getItem().getRegistryName() + (meta == 0 ? "" : ":" + meta) + ">";
				
				String withNBT = "";
				NBTTagCompound nbt = heldItem.serializeNBT();
				if (nbt.hasKey("tag")) {
					String nbtString = NBTConverter.from(nbt.getTag("tag"), false).toString();
					if (!nbtString.isEmpty())
						withNBT = ".withTag(" + nbtString + ")";
				}
				m.sendMessageWithCopy("Item §2" + itemName + "§a" + withNBT, itemName + withNBT);
				
				// adds liquid the item contains
				printLiquidInfo(m, heldItem);
				
				List<String> oreDictNames = CommandUtils.getOreDictOfItem(heldItem);
				// adds the oredict names if it has some
				DJ2ACommandUtils.printOreDictInfo(m, oreDictNames);
			} else {
				// if hand is empty, tries to get oreDict of block
				RayTraceResult rayTraceResult = CommandUtils.getPlayerLookat(player, 100);
				
				if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
					BlockPos blockPos = rayTraceResult.getBlockPos();
					IBlockState block = server.getEntityWorld().getBlockState(blockPos);
					
					DJ2ACommandUtils.printBlockMeta(m, blockPos, block);
				} else {
					m.send("§4Please hold an Item in your hand or look at a Block.");
				}
			}
		} else {
			m.send("This command can only be cast by a player in-game.");
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
	
	
}
