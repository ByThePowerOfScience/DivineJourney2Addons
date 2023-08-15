package btpos.dj2addons.crafttweaker.extrautils2;

import btpos.dj2addons.DJ2ATest;
import btpos.dj2addons.Test;
import com.rwtema.extrautils2.backend.entries.XU2Entries;
import com.rwtema.extrautils2.items.ItemBiomeMarker;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomePlains;

class CTBiomeMarkerTest {
	
	@Test
	void excludeBiome() {
		ItemStack is = new ItemStack(XU2Entries.biomeMarker.initValue());
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		nbtTagCompound.setString("Biome", "");
		is.setTagCompound(nbtTagCompound);
		is = ItemBiomeMarker.setBiome(is, BiomePlains.getBiome(1));
		DJ2ATest.assertTrue(is.getTagCompound().getString("Biome").equals(""));
	}
}