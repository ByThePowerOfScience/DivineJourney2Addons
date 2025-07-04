package btpos.dj2addons.common.util;

import btpos.dj2addons.core.DJ2Addons;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Map;

public final class Util {
	
	public static final class Numbers {
		public static boolean isDecimal_Fast(Number n) {
			return (n.floatValue() - n.intValue() != 0.0f);
		}
		
		public static boolean isDecimal(Number n) {
			return (n instanceof Float || n instanceof Double);
		}
	}
	
	public static final class DevTools {
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
	
	public static final class Format {
		public static String capitalizeFirstLetter(String input) {
			return input.substring(0, 1).toLowerCase() + input.substring(1);
		}
		
		public static String uncapitalizeFirstLetter(String input) {
			return input.substring(0, 1).toLowerCase() + input.substring(1);
		}
		
		@NotNull
		public static String formatPos(BlockPos blockPos) {
			return "[" + blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ() + "]";
		}
	}
	
	public static final class Misc {
		@Nullable @Contract("null, _->null;_,_->param2")
		public static <T> T nullOrElse(T object, T t) {
			if (object == null)
				return null;
			return t;
		}
	}
	
	
	public static ResourceLocation loc(String unitName) {
		return new ResourceLocation(DJ2Addons.MOD_ID, unitName);
	}
}
