package btpos.dj2addons.crafttweaker;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;
import java.util.Objects;

public class CraftTweakerHelpers {
	/**
	 * Formats the exception cause with the ZenClass and ZenMethod names so the pack dev knows where in the script it came from.
	 * @param originalExc The new exception to throw.
	 * @return A new instance of that exception type with the same stacktrace
	 */
	public static <T extends Exception> T fillExc(T originalExc) {
		StackTraceElement throwLocation = originalExc.getStackTrace()[0];
		
		Class<?> throwingClass;
		try {
			throwingClass = Class.forName(throwLocation.getClassName());
		} catch (ClassNotFoundException e) {
			// zero chance of this happening but whatever
			return originalExc;
		}
		String zcName;
		if (!throwingClass.isAnnotationPresent(ZenClass.class)) {
			zcName = throwLocation.getClassName();
		} else {
			zcName = throwingClass.getDeclaredAnnotation(ZenClass.class).value();
			if (zcName.isEmpty()) {
				zcName = throwingClass.getName();
			}
		}
		
		
		String methodName = throwLocation.getMethodName();
		
		String zMethodName = Arrays.stream(throwingClass.getDeclaredMethods())
		                           .filter(el -> methodName.equals(el.getName()))
		                           .map(el -> el.getDeclaredAnnotation(ZenMethod.class))
		                           .filter(Objects::nonNull)
		                           .map(ZenMethod::value)
		                           .findFirst()
		                           .orElse(methodName);
		
		// make a new exception of the same type
		T exception;
		try {
			exception = (T) originalExc.getClass()
			                           .getConstructor(String.class)
			                           .newInstance(String.format("[%s#%s] %s", zcName, zMethodName, originalExc.getMessage()));
		} catch (Exception e) {
			return originalExc;
		}
		// set its stacktrace to be the same as the other one
		exception.setStackTrace(originalExc.getStackTrace());
		// return it to be thrown
		return exception;
	}
	
}
