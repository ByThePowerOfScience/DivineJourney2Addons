package btpos.dj2addons.commands.util;

import crafttweaker.mc1120.commands.SpecialMessagesChat;
import it.unimi.dsi.fastutil.chars.CharArrayList;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public final class MessageHelper {
	public final ICommandSender sender;
	private boolean doLog = false;
	private boolean doChat = false;
	
	/** Contains the usage translation subkey if translated or just the usage string if not */
	private String usage = "usage";
	private boolean isTranslated;
	private LinkedList<String> currentTranslationKey;
	private Consumer<String> loggerFunction;
	
	/**
	 * Creates a thing to help with messages, formatting, etc.  Not translated.
	 * @see #setUsage(String)
	 * @see #setLogger(java.util.function.Consumer)
	 * @see #withChat()
	 * @see #withLog()
	 * @see #send(String)
	 * @see #sendError(String)
	 * @see #sendHeading(String)
	 * @see #sendMessageWithCopy(String, String)
	 */
	public MessageHelper(ICommandSender sender) {
		this.sender = sender;
		this.currentTranslationKey = new LinkedList<>();
		isTranslated = false;
	}
	
	/**
	 * Creates a thing to help with messages, formatting, etc.
	 *
	 * <p>Example usage:</p>
	 * <pre>{@code
	 * public static void execute(MinecraftServer server, ICommandSender sender, String[] args) {
	 *      MessageHelper m = new MessageHelper(sender, "dj2addons.command"); // sets base translation key to "dj2addons.command"
	 *      m.setLogger(CraftTweaker::logCommand).withChat().withLog(); // all output will be printed to both chat and the given logger
	 *
	 *      if (args.length == 0) {
	 *          m.usage(); // prints the translation key "dj2addons.command.usage" to the sender's chat
	 *          return;
	 *      }
	 *      switch (args[0]) {
	 *           case "hand":
	 *               m.enterSubCommand("hand") // makes any translatable text start with "dj2addons.commands.hand", including the .usage() function
	 *                .withoutLog(); // disables logging for this specifically
	 *               if (!(sender.getCommandSenderEntity() instanceof EntityPlayer)) {
	 *                   m.sendError("Command must be run by a player in-game.");
	 *                   return;
	 *               }
	 *               ItemStack heldItem = player.getHeldItemMainhand();
	 *               if (heldItem.isEmpty()) {
	 *                   m.sendError("You must hold something in your hand to use this command.");
	 *                   return;
	 *               }
	 *               m.sendHeading(heldItem.getDisplayName());
	 *               m.sendPropertyWithCopy("Metadata", heldItem.getMeta()); // the metadata will be copyable
	 *               NBTTagCompound nbt = heldItem.serializeNBT();
	 *               if (nbt.isEmpty()) {
	 *                   m.sendError("No NBT.");
	 *               } else {
	 *                  m.sendProperty("NBT", "{");
	 *                  for (String key : nbt.getKeySet()) {
	 *                      m.sendPropertyWithCopy(key, nbt.get(key), 1); // sends "Key: Value", with one indent, with the color of that indent, and the value is copyable
	 *                  }
	 *                  m.sendProperty("", "}");
	 *               }
	 *               break;
	 *           default:
	 *               m.usage();
	 *      }
	 * }
	 * }</pre>
	 * <p>Sends:</p>
	 * <pre>
	 * If hand is empty:
	 *
	 * <span style="color:#AA0000">You must hold something in your hand to use this command.</span>
	 *
	 *
	 * If holding a stone block with NBT {"x": "y"}:
	 *
	 * <span style="color:#0000AA">Stone:</span>
	 *     <span style="color:#FFFF55">- Metadata:</span> <span style="color:#55FFFF">0</span>
	 *     <span style="color:#FFFF55">- NBT:</span> <span style="color:#55FFFF">{</span>
	 *         <span style="color:#FFFF55">- x:</span> <span style="color:#00AA00">y</span>
	 *     <span style="color:#FFFF55">- </span><span style="color:#55FFFF">}</span>
	 * </pre>
	 * <p>Note: The {@link #usage()} method expects the "usage" key for any (sub)command to be nested under it. Default is ".usage". See {@link #setUsageSuffix(String)}.</p>
	 * <p>For example, if the "/dj2addons" command has a base translation key of "dj2addons.command", its usage key would be "dj2addons.command.usage".
	 * <p>Use {@link #enterSubCommand(String) enterSubCommand(name)} at the start of any subcommand's method to append that onto the translation key. E.g. {@code m.enterSubCommand("tile")} would make the new base key "dj2addons.command.tile(.usage)".</p>
	 * @param sender The sender to send a message to.  Can be null if chat is disabled.
	 * @param baseTranslationKey The translation key all (sub)commands will be nested under.
	 * @see
	 */
	public MessageHelper(@Nullable ICommandSender sender, String baseTranslationKey) {
		this.sender = sender;
		this.currentTranslationKey = new LinkedList<>();
		this.currentTranslationKey.add(baseTranslationKey);
		isTranslated = true;
	}
	
	public MessageHelper withChat() {
		doChat = true;
		return this;
	}
	
	public MessageHelper withoutChat() {
		doChat = false;
		return this;
	}
	
	/**
	 * Enables log output for this
	 *
	 * @return
	 */
	public MessageHelper withLog() {
		doLog = true;
		return this;
	}
	
	public MessageHelper withoutLog() {
		doLog = false;
		return this;
	}
	
	/**
	 * Sets the function any logging should call.
	 *
	 * @param loggerFunction e.g. {@code CraftTweaker::logCommand} or {@code (s) -> LOGGER.info("[COMMAND] {}", s)}
	 * @see #withLog()
	 */
	public MessageHelper setLogger(Consumer<String> loggerFunction) {
		this.loggerFunction = loggerFunction;
		return this;
	}
	
	/**
	 * Set the default usage suffix for any translation key category.
	 */
	public MessageHelper setUsageSuffix(String usageSuffix) {
		this.usage = usageSuffix;
		return this;
	}
	
	public MessageHelper enterSubCommand(String label) {
		currentTranslationKey.add(label);
		return this;
	}
	
	public MessageHelper exitSubCommand() {
		currentTranslationKey.removeLast();
		return this;
	}
	
	private static final String INDENT = "    ";
	private static CharArrayList INDENT_HIGHLIGHTING = new CharArrayList(new char[] {'b', '2'});
	public static void setIndentHighlighting(char[] highlightingCodes) {
		INDENT_HIGHLIGHTING = new CharArrayList(highlightingCodes);
	}
	
	public void send(String message) {
		sendComponent(new TextComponentString(message));
	}
	
	public void sendComponent(ITextComponent textComponent) {
		if (doChat)
			sender.sendMessage(textComponent);
		if (doLog)
			log(textComponent.toString());
	}
	
	public void log(String message) {
		loggerFunction.accept(message);
	}
	
	public void sendTranslation(String subKey) {
		if (!currentTranslationKey.isEmpty()) {
			sendComponent(new TextComponentTranslation(String.join(".", currentTranslationKey) + "." + subKey));
		}
	}
	
	public void setUsage(String usageString) {
		this.usage = usageString;
	}
	
	public void usage() {
		if (isTranslated)
			sendTranslation(usage);
		else
			send(usage);
	}
	
	public void linkToLog() {
		sender.sendMessage(SpecialMessagesChat.getLinkToCraftTweakerLog("Printed to CraftTweaker log.", sender));
	}
	
	public void sendHeading(String s) {
		send("§3" + s);
	}
	
	public void sendError(String s) {
		send("§4" + s);
	}
	
	public void sendPropertyWithCopy(String property, String value) {
		sendPropertyWithCopy(property, value, 0);
	}
	
	public void sendPropertyWithCopy(String property, String value, int indent) {
		sendPropertyWithCopy(property, value, value, indent);
	}
	
	public void sendPropertyWithCopy(String property, String value, String toCopy) {
		sendPropertyWithCopy(property, value, toCopy, 0);
	}
	
	public void sendPropertyWithCopy(String property, String value, String toCopy, int indent) {
		sendMessageWithCopy(getPropertyMessage(property, value, indent), toCopy);
	}
	
	public void sendMessageWithCopy(String message, String toCopy) {
		if (doChat) {
			if (sender.getCommandSenderEntity() instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) sender.getCommandSenderEntity();
				player.sendMessage(SpecialMessagesChat.getCopyMessage(message, toCopy));
			}
			else
				send(message);
		}
		if (doLog)
			log(message);
	}
	
	public void sendProperty(String property, String value) {
		sendProperty(property, value, 0);
	}
	
	public void sendProperty(String property, String value, int indent) {
		send(getPropertyMessage(property, value, indent));
	}
	
	public static String makeIndent(int level) {
		return StringUtils.repeat(INDENT, level + 1);
	}
	
	public static String getPropertyMessage(String property, String value, int indent) {
		if (property == null || property.isEmpty())
			property = "";
		else if (property.charAt(property.length() - 1) == ':') {
			property += " ";
		}
		else {
			if (!(property.charAt(property.length() - 2) == ':' && property.charAt(property.length() - 1) == ' ')) {
				property += ": ";
			}
		}
		return makeIndent(indent) + "§e- " + property + "§" + INDENT_HIGHLIGHTING.getChar(indent % INDENT_HIGHLIGHTING.size()) + value;
	}
	
	public static <T> String listToAssociativeArrayPretty(List<T> list, boolean raw, int indent) {
		Map<T, T> map = new Object2ObjectOpenHashMap<>();
		for (int i = 0; i < list.size(); i += 2) {
			T o1 = list.get(i);
			T o2;
			try {
				o2 = list.get(i + 1);
			} catch (Exception e) {
				o2 = null;
			}
			map.put(o1, o2);
		}
		return mapToAssociativeArrayPretty(map, raw, indent);
	}
	
	public static <T> String mapToAssociativeArrayPretty(Map<T, T> map, boolean raw, int indent) {
		StringBuilder sb = new StringBuilder();
		if (!raw)
			sb.append(TextFormatting.YELLOW);
		sb.append("{\n");
		if (!raw)
			sb.append(makeIndent(indent));
		map.forEach((x, y) -> {
			if (raw) {
				sb.append("\t").append(x).append(": ").append(y).append(",\r\n");
			} else {
				sb.append(INDENT)
				  .append(TextFormatting.GREEN).append(x).append(TextFormatting.YELLOW).append(": ")
				  .append(TextFormatting.AQUA).append(y).append(TextFormatting.YELLOW).append(",\n").append(makeIndent(indent));
			}
		});
		if (!raw)
			sb.append(makeIndent(indent)).append(TextFormatting.YELLOW);
		sb.append("}");
		return sb.toString();
	}
}
