package org.btpos.dj2addons.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Used since we can't read anything "normal" at this stage of loading.
 */
public class JankConfig extends Object {
	public static final String folder = "dj2addons" + File.separator;
	public static final String infusionStabsFileName = folder + "infusion_stabilizers.txt";
	
	public static final Map<String, Float> infusionStabilizersToAdd = new HashMap<>();
	
//	public static boolean doesModExist(String modName) {
//		return Loader.instance( // need to scan mods directory to match anything with the name "thaumcraft" to know if we can safely implement the interface
//				// wait... what if I just provide my own file for the interface? It's not like it'll do anything since nothing's calling it, and it'll stop a noclassdeffounderror
//	}
	
	static void readInfusionStabilizers() {
		try (BufferedReader br = new BufferedReader(new FileReader(JankConfig.infusionStabsFileName))) {
			String line;
			while ((line = br.readLine()) != null) {
				try {
					line = line.substring(0, line.indexOf("//"));
				} catch (Exception e) {
					DJ2AddonsCore.LOGGER.debug("Line \"{}\" is only a comment.", line);
					continue;
				}
				String[] splitLine = line.split(",");
				try {
					JankConfig.infusionStabilizersToAdd.put(splitLine[0].trim(), Float.parseFloat(splitLine[1].trim()));
				} catch (Exception e) {
					DJ2AddonsCore.LOGGER.debug("Line \"{}\" did not parse as an infusion stabilizer.", line);
				}
			}
		} catch (IOException e) {
			DJ2AddonsCore.LOGGER.catching(e);
		}
	}
}
