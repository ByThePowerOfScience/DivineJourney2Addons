package org.btpos.dj2addons.mixin.totemic;

import com.mojang.realmsclient.util.Pair;
import org.btpos.dj2addons.impl.totemic.VInstruments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.init.ModContent;

@Mixin(ModContent.class)
public class MModContent {
//	private static final ImmutableMap<String, Pair<Integer, Integer>> INSTRUMENTS =
//			ImmutableMap.<String, Pair<Integer, Integer>>builder()
//						.put("totemic:flute", Pair.of(5, 80))
//						.put("totemic:drum", Pair.of(7,90))
//						.put("totemic:windChime", Pair.of(3,40))
//						.put("totemic:jingleDress", Pair.of(5,40))
//						.put("totemic:rattle", Pair.of(8,90))
//						.put("totemic:eagleBoneWhistle", Pair.of(10,100))
//						.build();
	
	@Redirect(remap=false, method="instruments", at=@At(target="pokefenn/totemic/api/music/MusicInstrument", value="NEW"))
	private static MusicInstrument changeTotemicValues(String name, int baseOutput, int musicMaximum) {
		Pair<Integer, Integer> vals = VInstruments.getValuesForInstrument(name, baseOutput, musicMaximum);
		return new MusicInstrument(name, vals.first(), vals.second());
	}
}
