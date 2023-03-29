package org.btpos.dj2addons.util;

import net.minecraft.block.properties.IProperty;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import org.btpos.dj2addons.DJ2Addons;

import java.lang.reflect.Field;
import java.util.Map;

public class Util {
	public static class Numbers {
		public static boolean isDecimal_Fast(Number n) {
			return (n.doubleValue() - n.intValue() != 0.0D);
		}
		
		public static boolean isDecimal(Number n) {
			return (n instanceof Double || n instanceof Float);
		}
	}
	
	public static class DevTools {
		@SuppressWarnings("unchecked")
		public static Map<String, Capability<?>> getAllCapabilities() {
			try {
				Field f = CapabilityManager.class.getDeclaredField("providers");
				f.setAccessible(true);
				return (Map<String, Capability<?>>) f.get(CapabilityManager.INSTANCE);
			} catch (IllegalAccessException | NoSuchFieldException e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	public static class Strings {
		public static String capitalizeFirstLetter(String input) {
			return input.substring(0, 1).toLowerCase() + input.substring(1);
		}
		
		public static String uncapitalizeFirstLetter(String input) {
			return input.substring(0, 1).toLowerCase() + input.substring(1);
		}
		
	}
	
	public static class BlockStates {
		public static <T extends Comparable<T>> String getPropertyValueName(IProperty<T> property, Comparable<T> value) {
			return property.getName((T) value);
		}
	}
	
	
	public static ResourceLocation loc(String unitName) {
		return new ResourceLocation(DJ2Addons.MOD_ID, unitName);
	}
}
