import org.btpos.dj2addons.util.Util;
import org.btpos.dj2addons.util.zendoc.*;
import org.apache.commons.lang3.StringUtils;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.reflections.Reflections;
import stanhebben.zenscript.annotations.Optional;

import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>Sources:
 * <p>https://github.com/MysticMods/Roots/blob/release/3.1.5/src/main/java/epicsquid/roots/ExportDocumentation.java
 * <p>https://github.com/MysticMods/Roots/blob/release/3.1.5/src/main/java/epicsquid/roots/util/zen/ZenDocExporter.java
 *
 * <p>Original author: Noobanidus from the MysticMods Team<p>
 * <p>This incredible piece of code wouldn't even be copied if it didn't have some hardcoded paths.
 * <p>Revised somewhat by ByThePowerOfScience
 */
public class ExportZenDocs {
	public static void main(String[] args) {
		String targetPath = "./docs/zs/";
//		ClassPath.from(ClassLoader.getSystemClassLoader())
//		         .getAllClasses()
//		         .stream()
//		         .filter(classInfo -> classInfo.getPackageName().contains("org.btpos.dj2addons.crafttweaker"))
//		         .map(ResourceInfo::asByteSource)
//				.map(bs -> );
		Reflections reflections = new Reflections("org.btpos.dj2addons.crafttweaker");
		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(ZenDocClass.class);
		System.out.println("Num classes: " + classes.size()); //debug
		ZenDocExporter export = new ZenDocExporter();
		Path path = Paths.get(targetPath);
		
		try {
			Files.createDirectories(path);
			export.export(path, classes.toArray(new Class[0]));
		} catch (IOException var6) {
			var6.printStackTrace();
		}
		
	}
	
	private static class ZenDocExporter {
		
		public ZenDocExporter() {
		}
		
		public void export(Path path, Class<?>[] classes) throws IOException {
			for(int i = 0; i < classes.length; ++i) {
				StringBuilder out = new StringBuilder();
				ZenDocClass zenClass = classes[i].getDeclaredAnnotation(ZenDocClass.class);
				ZenDocAppend zenDocAppend = classes[i].getDeclaredAnnotation(ZenDocAppend.class);
				ZenDocInclude zenDocInclude = classes[i].getDeclaredAnnotation(ZenDocInclude.class);
				if (zenClass != null && !zenClass.onlyInOther()) {
					if (i > 0) {
						out.append("\n");
					}
					System.out.println("Documenting " + classes[i].getName());
					out.append(docClass(classes[i]));
					
					
					if (zenDocInclude != null) {
						Class<?>[] toAppend = zenDocInclude.value();
						out.append('\n');
						for (Class<?> c : toAppend) {
							appendLineSeparator(out);
							out.append(docClass(c));
						}
					}
					if (zenDocAppend != null) {
						String[] toAppend = zenDocAppend.value();
						
						for (String s : toAppend) {
							
							Path p = Paths.get("./" + s);
							
							try {
								List<String> lines = Files.readAllLines(p); // Throws
								appendLineSeparator(out);
								for (String line : lines) {
									out.append(line).append("\n");
								}
							} catch (IOException var25) {
								var25.printStackTrace();
							}
						}
					}
					
					
					try {
						String[] h3 = zenClass.value().split("\\.");
						String zenClassName = h3[h3.length - 1];
						Files.write(path.resolve(zenClassName.toLowerCase() + ".md"), out.toString().getBytes());
					} catch (IOException var24) {
						var24.printStackTrace();
					}
				}
			}
			
		}
		
		private static String docClass(Class<?> clazz) {
			ZenDocClass zenClass = clazz.getDeclaredAnnotation(ZenDocClass.class);
			StringBuilder out = new StringBuilder();
			String[] h3 = zenClass.value().split("\\.");
			String zenTypeName = h3[h3.length - 1];
			out.append("### Class\n");
			out.append("\n");
			out.append("```zenscript").append("\n");
			out.append("import ").append(zenClass.value()).append(";").append("\n");
			out.append("```").append("\n");
			out.append("\n");
			String[] description = zenClass.description();
			if (description.length > 0) {
				
				for (String line : description) {
					out.append(parse(line)).append("\n");
				}
				
				out.append("\n");
			}
			
			
			Method[] methods = new Method[0];
//			Collection<MethodDetails> methods1;
			try {
				methods = clazz.getDeclaredMethods();
			} catch (Error | Exception e) {
				e.printStackTrace();
//				ClassNode classNode = new ClassNode(); //TODO retrieve method data through ASM if a referenced class doesn't exist
//				ClassReader classReader = new ClassReader(clazz.getCanonicalName());
//				classReader.accept(classNode, 0);
//				methods1 = classNode.methods.stream().map(MethodDetails::new).collect(Collectors.toSet());
			}
			
			
			List<MethodAnnotationPair> allMethods = getSortedMethodList(methods);
			List<MethodAnnotationPair> staticMethodList = allMethods.stream().filter((pair) -> Modifier.isStatic(pair.type.getModifiers())).collect(Collectors.toList());
			
			List<MethodAnnotationPair> methodList = allMethods.stream().filter((pair) -> !Modifier.isStatic(pair.type.getModifiers())).collect(Collectors.toList());
			if (!staticMethodList.isEmpty()) {
				out.append("#### Static Methods\n");
				out.append('\n');
				writeMethodList(out, staticMethodList);
				out.append('\n');
			}
			
			if (!methodList.isEmpty()) {
				out.append("#### Instance Methods\n");
				out.append('\n');
				writeMethodList(out, methodList);
				out.append('\n');
			}
			
			
			
			
			Field[] fields = clazz.getDeclaredFields();
			List<PropertyAnnotationPair> fieldList = getSortedFieldList(fields);
			if (!fieldList.isEmpty()) {
				out.append('\n');
				out.append("### Static Properties\n");
				out.append("\n```zenscript\n");
				writePropertyList(out, fieldList, zenTypeName);
				out.append("\n```");
			}
			
			return out.toString();
		}
		
		private static void writeMethodList(StringBuilder out, List<MethodAnnotationPair> list) {
			for(int j = 0; j < list.size(); ++j) {
				if (j > 0) {
					out.append("\n");
				}
				
				writeMethod(out, list.get(j).type, list.get(j).annotation);
//				if (j != list.size() - 1) // I'm not sure I like the line separators between each method
//					appendLineSeparator(out);
			}
			
		}
		
		private static void writePropertyList(StringBuilder out, List<PropertyAnnotationPair> staticPropertyList, String className) {
			for(int j = 0; j < staticPropertyList.size(); ++j) {
				if (j > 0) {
					out.append("\n");
				}
				
				writeProperty(out, staticPropertyList.get(j).type, staticPropertyList.get(j).annotation, className);
				if (j != staticPropertyList.size() - 1) {
					out.append("\n");
				}
			}
		}
		
		private static void writeMethod(StringBuilder out, Method method, ZenDocMethod annotation) {
			String methodName = method.getName();
			Class<?> returnType = method.getReturnType();
			String returnTypeString = getSimpleTypeString(returnType);
			out.append("```zenscript").append("\n");
			out.append(returnTypeString).append(" ").append(methodName).append("(");
			
			Parameter[] params = method.getParameters();
			
			ZenDocArg[] args = annotation.args();
			
			if (params.length != args.length)
				throw new IllegalStateException("Error in (" + method.getDeclaringClass().getName() + "#" + method.getName() + "): All arguments must have a corresponding ZenDocArg annotation!"); // I really tried to get rid of this, but the parameter names aren't saved by the compiler ;_;
			
			if (params.length > 0) {
				out.append("\n");
			}
			
			int largest = 0;
			List<String[]> parameterStrings = new ArrayList<>(params.length);
			
			String line;
			for (int i = 0; i < params.length; i++) {
				Parameter p = params[i];
				ZenDocArg arg = args[i];
				boolean optional = false;
				boolean nullable = false;
				Annotation[] paramAnnotations = p.getAnnotations();
				
				for (Annotation a : paramAnnotations) {
					if (a instanceof Optional) {
						optional = true;
					}
					
					if (a instanceof ZenDocNullable || a instanceof Nullable) {
						nullable = true;
					}
				}
				
				String optionalString = optional ? "@Optional " : "";
				line = nullable ? "@Nullable " : "";
				String typeString = getSimpleTypeString(p.getType());
				String nameString = arg.arg();
				
				String outString = "  " + optionalString + line + typeString + " " + nameString + ",";
				
				if (outString.length() > largest) {
					largest = outString.length();
				}
				
				// get ZenDocArg description for arg
				
				
				parameterStrings.add(new String[]{outString, arg.info()});
			}
		
			for (String[] parameterString : parameterStrings) {
				out.append(StringUtils.rightPad(parameterString[0], largest));
				if (parameterString[1] != null) {
					out.append(" // ").append(parameterString[1]);
				}
				
				out.append("\n");
			}
			
			out.append(");\n");
			out.append("```").append("\n\n");
			String[] description = annotation.description();
			if (description.length > 0) {
				
				for (String s : description) {
					line = s;
					out.append(parse(line));
				}
			}
		}
		
		private static void writeProperty(StringBuilder out, Field field, ZenDocProperty annotation, String className) {
			String fieldName = field.getName();
			String[] h3 = className.split("\\.");
			String zenClassName = h3[h3.length - 1];
			String[] comments = annotation.description();
			String fieldLine = zenClassName + "." + fieldName;
			
			if (comments != null && comments.length > 0) {
				if (comments.length == 1) {
					out.append(fieldLine).append(" // ").append(comments[0]);
				} else {
					for (String comment : comments) {
						out.append("\n// ").append(comment);
					}
					out.append(fieldLine);
				}
			} else {
				out.append(fieldLine);
			}
		}
		
		private static String parse(String line) {
			if (!line.startsWith("@see")) {
				return line + "\n";
			} else {
				String[] links = line.substring(4).trim().split(" ");
				StringBuilder sb = new StringBuilder("For more information, see:\n");
				
				for (String link : links) {
					sb.append("  * [").append(link).append("](").append(link).append(")\n");
				}
				
				return sb.toString();
			}
		}
		
		private static void appendLineSeparator(StringBuilder out) {
			out.append("\n\n---\n\n");
		}
		
		private static List<MethodAnnotationPair> getSortedMethodList(Method[] methods) {
			List<MethodAnnotationPair> methodList = new ArrayList<>();
			
			for (Method method : methods) {
				ZenDocMethod annotation = method.getDeclaredAnnotation(ZenDocMethod.class);
				if (annotation != null) {
					methodList.add(new MethodAnnotationPair(method, annotation));
				}
			}
			
			methodList.sort(Comparator.comparingInt((o) -> o.annotation.order()));
			return methodList;
		}
		
		private static List<PropertyAnnotationPair> getSortedFieldList(Field[] fields) {
			List<PropertyAnnotationPair> fieldList = new ArrayList<>();
			
			for (Field field : fields) {
				ZenDocProperty annotation = field.getDeclaredAnnotation(ZenDocProperty.class);
				if (annotation != null) {
					fieldList.add(new PropertyAnnotationPair(field, annotation));
				}
			}
			
			fieldList.sort(Comparator.comparingInt((o) -> o.annotation.order()));
			return fieldList;
		}
		
		private static String getSimpleTypeString(Class<?> type) {
			String result = type.getSimpleName();
			if (result.startsWith("Zen")) {
				result = result.substring(3);
			} else if (result.startsWith("String")) {
				result = Util.Strings.uncapitalizeFirstLetter(result);
			}
			
			return result;
		}
		
		private static class PropertyAnnotationPair extends AnnotationPairBase<Field, ZenDocProperty> {
			private PropertyAnnotationPair(Field method, ZenDocProperty annotation) {
				super(method, annotation);
			}
		}
		
		private static class MethodAnnotationPair extends AnnotationPairBase<Method, ZenDocMethod> {
			private MethodAnnotationPair(Method method, ZenDocMethod annotation) {
				super(method, annotation);
			}
		}
		
		private static class AnnotationPairBase<T, V> {
			public final T type;
			public final V annotation;
			
			private AnnotationPairBase(T type, V annotation) {
				this.type = type;
				this.annotation = annotation;
			}
		}
		
		private static class MethodDetails {
			final String methodName;
			final Map<String, Map<String, Object>> annotations; // [AnnotationName: [AnnotationProperty: Value]]
			Map<String, String> params; // [ParamName: TypeName]
			private MethodDetails(MethodNode node) {
				methodName = node.name;
				annotations = getAnnotations(node.visibleAnnotations);
				
			}
			
			public boolean hasAnnotations() {
				return annotations != null;
			}
			
			private static Map<String, Map<String, Object>> getAnnotations(@Nullable List<AnnotationNode> nodes) {
				if (nodes == null)
					return null;
				return nodes.stream().collect(Collectors.toMap(a -> a.desc, a -> {
					Map<String, Object> annotations = new HashMap<>();
					for (int i = 0; i < a.values.size(); i+=2) {
						annotations.put((String)a.values.get(i), a.values.get(i + 1));
					}
					return annotations;
				}));
			}
		}
	}
	
}
