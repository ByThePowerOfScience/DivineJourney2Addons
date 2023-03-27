package org.btpos.dj2addons.impl.modrefs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.FakePlayer;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.api.crafting.IInfusionStabiliserExt;

import java.util.Set;

public class CThaumcraft {
	public static void setFakePlayerKnowledge(FakePlayer fakePlayer, EntityPlayer realPlayer) {
		try {
			if (realPlayer == null)
				return;
			Set<String> ownerResearch = ThaumcraftCapabilities.getKnowledge(realPlayer).getResearchList();
			
			IPlayerKnowledge fakePlayerKnowledge = ThaumcraftCapabilities.getKnowledge(fakePlayer);
			ownerResearch.forEach(fakePlayerKnowledge::addResearch);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
}
