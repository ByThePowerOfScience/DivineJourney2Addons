package btpos.dj2addons.asm.api.thaumcraft.infusionstabilizers;

import btpos.dj2addons.common.CoreInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Used since we can't read anything "normal" at this stage of loading.
 */
public class JankConfig {
	public static final String folder = "dj2addons" + File.separator;
	public static final String infusionStabsFileName = folder + "infusion_stabilizers.txt";
	
	public static final Map<String, Float> infusionStabilizersToAdd = new HashMap<>();

//	public static boolean doesModExist(String modName) {
//		return Loader.instance( // need to scan mods directory to match anything with the name "thaumcraft" to know if we can safely implement the interface
//				// wait... what if I just provide my own file for the interface? It's not like it'll do anything since nothing's calling it, and it'll stop a noclassdeffounderror
//	}
	
	public static void readInfusionStabilizers() {
		try (BufferedReader br = new BufferedReader(new FileReader(infusionStabsFileName))) {
			String line;
			while ((line = br.readLine()) != null) {
				try {
					line = line.substring(0, line.indexOf("//"));
				} catch (Exception e) {
					CoreInfo.LOGGER.error("Line \"{}\" is only a comment.", line);
					continue;
				}
				String[] splitLine = line.split(",");
				try {
					infusionStabilizersToAdd.put(splitLine[0].trim(), Float.parseFloat(splitLine[1].trim()));
				} catch (Exception e) {
					CoreInfo.LOGGER.error("Line \"{}\" did not parse as an infusion stabilizer.", line);
				}
			}
		} catch (IOException e) {
			CoreInfo.LOGGER.info("No janky Thaumcraft config detected. Skipping.");
		}
	}
}
