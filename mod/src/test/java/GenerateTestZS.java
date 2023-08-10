import org.reflections.Reflections;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Random;
import java.util.Set;

/**
 * 1. List import statements at the top
 * 2. Write all methods with placeholder variables for their types
 */
public class GenerateTestZS {
	private static final Random rand = new Random();
	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			return;
		}
		String outputPath = args[0];
		Set<Class<?>> classes = new Reflections("btpos.dj2addons").getTypesAnnotatedWith(ZenClass.class);
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
			for (Class<?> clazz : classes) {
				String zenclassname = getZenClassName(clazz);
				writer.write("import ");
				writer.write(zenclassname);
				writer.write(";\n");
			}
			writer.write("\n\n");
			for (Class<?> clazz : classes) {
				String zenclassname = getZenClassName(clazz);
				int i = 0;
				for (Method method : clazz.getDeclaredMethods()) {
					ZenMethod declaredAnnotation = method.getDeclaredAnnotation(ZenMethod.class);
					if (declaredAnnotation == null)
						continue;
					String zenmethodname = declaredAnnotation.value();
					if (zenmethodname.equals("")) {
						zenmethodname = method.getName();
					}
					String[] splitName = zenclassname.split("\\.");
					writer.write(splitName[splitName.length - 1]);
					writer.write(".");
					writer.write(zenmethodname);
					writer.write("(");
					if (method.getParameterCount() >= 1) {
						writer.write("\n");
						for (Parameter param : method.getParameters()) {
							writer.write("\t" + getPlaceholderValue(param, ++i) + "\n");
						}
					}
					writer.write(");\n\n");
				}
			}
		}
		
	}
	
	private static String getZenClassName(Class<?> clazz) {
		String zenclassname = clazz.getDeclaredAnnotation(ZenClass.class).value();
		if ("".equals(zenclassname)) {
			zenclassname = clazz.getCanonicalName();
		}
		return zenclassname;
	}
	
	static String getPlaceholderValue(Parameter param, int ordinal) {
		Class<?> type = param.getType();
		if (type.isPrimitive()) {
			if (type == java.lang.Boolean.TYPE)
				return "true";
			else if (type == java.lang.Byte.TYPE
			         || type == Integer.TYPE
			         || type == java.lang.Long.TYPE
			         || type == java.lang.Short.TYPE)
				return String.valueOf(rand.nextInt(5));
			else if (type == java.lang.Character.TYPE)
				return String.valueOf(randomChar());
			else if (type == java.lang.Float.TYPE ||
			         type == java.lang.Double.TYPE)
				return "1." + rand.nextInt(9);
			else
				return "<placeholder:" + type.getName() + ">";
		} else if (type == String.class) {
			return "test" + ordinal;
		}
		ZenClass annotation = type.getAnnotation(ZenClass.class);
		if (annotation != null) {
			String name = annotation.value();
			if (name.equals(""))
				name = type.getCanonicalName();
			return "<ZENCLASS:" + name + ">";
		}
		return "<PLACEHOLDER:" + type.getCanonicalName() + ">";
	}

	private static char randomChar() {
		return (char)(rand.nextInt(27) + 65);
	}
	
//fun getConstructorForZenClass(param: Parameter) : String {
//    val sb = StringBuilder()
//    sb.write("new ").write(param.type.simpleName).write("(")
//    for (cparam in param.type.constructors[0].parameters) {
//        sb.write(getPlaceholderValue(cparam)).write(',')
//    }
//    return sb.toString().removeSuffix(",") + ")"
//}

}
