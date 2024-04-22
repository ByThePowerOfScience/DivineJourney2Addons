package btpos.dj2addons.common.modrefs;

import btpos.dj2addons.commands.util.MessageHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import vazkii.botania.api.BotaniaAPI;

public class CBotania {
	public static void printAllBrews(MessageHelper m) {
		BotaniaAPI.brewMap.forEach((key, brew) -> m.sendComponent(m.getPropertyMessage(new TextComponentTranslation(brew.getUnlocalizedName()), new TextComponentString(key), 0)));
	}
}
