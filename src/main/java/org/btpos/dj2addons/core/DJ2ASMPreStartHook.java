package org.btpos.dj2addons.core;

import net.minecraftforge.fml.relauncher.IFMLCallHook;

import java.util.Map;

public class DJ2ASMPreStartHook implements IFMLCallHook {
	@Override
	public void injectData(Map<String, Object> data) {}
	
	@Override
	public Void call() {
		JankConfig.readInfusionStabilizers();
		return null;
	}
	
	
	
	
	/*public static <T> T getFromConfig(String location, Class<T> typeOfResponse) {
		String[] split = location.split("\\.");
		Object current = map;
		for (String loc : split) {
			if (current == null)
				return typeOfResponse.newInstance();
			if (current instanceof Map) {
				current = ((Map<?, ?>) current).get(loc);
			}
		}
	}*/
	
}
