import btpos.dj2addons.common.util.Util.Format;
import btpos.dj2addons.common.util.zendoc.ZenDocAppend;
import btpos.dj2addons.common.util.zendoc.ZenDocArg;
import btpos.dj2addons.common.util.zendoc.ZenDocClass;
import btpos.dj2addons.common.util.zendoc.ZenDocInclude;
import btpos.dj2addons.common.util.zendoc.ZenDocMethod;
import btpos.dj2addons.common.util.zendoc.ZenDocNullable;
import btpos.dj2addons.common.util.zendoc.ZenDocProperty;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.reflections.Reflections;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;

import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <p>Sources:
 * <p><a href="https://github.com/MysticMods/Roots/blob/release/3.1.5/src/main/java/epicsquid/roots/ExportDocumentation.java">...</a>
 * <p><a href="https://github.com/MysticMods/Roots/blob/release/3.1.5/src/main/java/epicsquid/roots/util/zen/ZenDocExporter.java">...</a>
 *
 * <p>Original author: Noobanidus from the MysticMods Team<p>
 * <p>This incredible piece of code wouldn't even be copied if it didn't have some hardcoded paths.
 * <p>Revised somewhat by ByThePowerOfScience
 */
public class ExportZenDocs { //TODO turn this into an annotation processor
	public static void main(String[] args) {
		String targetPath = "./docs/zs/";
		//		ClassPath.from(ClassLoader.getSystemClassLoader())
		//		         .getAllClasses()
		//		         .stream()
		//		         .filter(classInfo -> classInfo.getPackageName().contains("org.btpos.dj2addons.crafttweaker"))
		//		         .map(ResourceInfo::asByteSource)
		//				.map(bs -> );
		
		Set<Class<?>> classes = new Reflections("btpos.dj2addons").getTypesAnnotatedWith(ZenDocClass.class);
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
			for (Class<?> aClass : classes) {
				StringBuilder out = new StringBuilder();
				ZenDocClass zenClass = aClass.getDeclaredAnnotation(ZenDocClass.class);
				ZenDocAppend zenDocAppend = aClass.getDeclaredAnnotation(ZenDocAppend.class);
				ZenDocInclude zenDocInclude = aClass.getDeclaredAnnotation(ZenDocInclude.class);
				if (zenClass != null && !zenClass.onlyInOther()) {
					//					if (i > 0) {
					//						out.append("\n");
					//					}
					System.out.println("Documenting " + aClass.getName());
					out.append(docClass(aClass));
					
					
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
						String name = aClass.getDeclaredAnnotation(ZenClass.class).value();
						if ("".equals(name)) {
							name = aClass.getCanonicalName();
						}
						String[] h3 = name.split("\\.");
						String filename = h3[h3.length - 1] + ".md";
						Path rel = path;
						for (int i = 0; i <= h3.length - 2; i++) {
							rel = rel.resolve(h3[i]);
						}
						Files.createDirectories(rel);
						Files.write(rel.resolve(filename), out.toString().getBytes());
					} catch (IOException var24) {
						var24.printStackTrace();
					}
				}
			}
			
		}
		
		//		private static final Type TYPE_ZEN_METHOD = Type.getType(ZenMethod.class);
		//		private static final Type TYPE_ZEN_CLASS = Type.getType(ZenClass.class);
		//		private static final Type TYPE_ZENDOC_METHOD = Type.getType(ZenDocMethod.class);
		//		private static final Type TYPE_ZENDOC_CLASS = Type.getType(ZenDocClass.class);
		//		private static final Type TYPE_ZENDOC_INCLUDE = Type.getType(ZenDocInclude.class);
		//		private static final Type TYPE_ZENDOC_ARG = Type.getType(ZenDocArg.class);
		//		private static final Type TYPE_ZENDOC_APPEND = Type.getType(ZenDocAppend.class);
		//
		//
		//		private static boolean hasAnnotation(ClassNode node, Type annotationType) {
		//			return node.visibleAnnotations != null || node.visibleAnnotations.stream().map(n -> Type.getType(n.desc)).anyMatch(t -> t.equals(annotationType));
		//		}
		//		private static boolean hasAnnotation(MethodNode node, Type annotationType) {
		//			return node.visibleAnnotations != null || node.visibleAnnotations.stream().map(n -> Type.getType(n.desc)).anyMatch(t -> t.equals(annotationType));
		//		}
		//
		//		private static boolean isStatic(MethodNode node) {
		//			return (node.access & Opcodes.ACC_STATIC) != 0;
		//		}
		
		
		//		private static String docClassWithAsm(Class<?> clazz) throws IOException {
		//
		//			ClassReader reader = new ClassReader(clazz.getTypeName());
		//			ClassNode node = new ClassNode(Opcodes.ASM5);
		//			reader.accept(node, ClassReader.SKIP_CODE);
		//
		//			List<MethodNode> staticMethods = new LinkedList<>();
		//			List<MethodNode> instanceMethods = new LinkedList<>();
		//
		//			node.methods.forEach(method -> {
		//				if (!hasAnnotation(method, TYPE_ZEN_METHOD))
		//					return;
		//
		//				if (isStatic(method)) {
		//					staticMethods.add(method);
		//				} else {
		//					instanceMethods.add(method);
		//				}
		//			});
		//
		//			staticMethods.stream()
		//		}
		
		
		private static String docClass(Class<?> clazz) {
			ZenDocClass zenClass = clazz.getDeclaredAnnotation(ZenDocClass.class);
			ZenClass zen = clazz.getDeclaredAnnotation(ZenClass.class);
			StringBuilder out = new StringBuilder();
			String zenName;
			if ("".equals(zen.value())) {
				zenName = clazz.getCanonicalName();
			} else {
				zenName = zen.value();
			}
			String[] h3 = zenName.split("\\.");
			String zenTypeName = h3[h3.length - 1];
			out.append("### Class\n");
			out.append("\n");
			out.append("```zenscript").append("\n");
			out.append("import ").append(zenName).append(";").append("\n");
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
			for (int j = 0; j < list.size(); ++j) {
				if (j > 0) {
					out.append("\n");
				}
				
				writeMethod(out, list.get(j).type, list.get(j).annotation);
				//				if (j != list.size() - 1) // I'm not sure I like the line separators between each method
				//					appendLineSeparator(out);
			}
			
		}
		
		private static void writePropertyList(StringBuilder out, List<PropertyAnnotationPair> staticPropertyList, String className) {
			for (int j = 0; j < staticPropertyList.size(); ++j) {
				if (j > 0) {
					out.append("\n");
				}
				
				writeProperty(out, staticPropertyList.get(j).type, staticPropertyList.get(j).annotation, className);
				if (j != staticPropertyList.size() - 1) {
					out.append("\n");
				}
			}
		}
		
		//		@Nullable
		//		private static MethodNode getMethodNode(Method method) {
		//			ClassReader reader;
		//			try {
		//				reader = new ClassReader(method.getDeclaringClass().getName());
		//			} catch (IOException e) {
		//				e.printStackTrace();
		//				return null;
		//			}
		//			ClassNode classNode = new ClassNode();
		//			reader.accept(classNode, ClassReader.SKIP_CODE);
		//			String methodDescriptor = Type.getMethodDescriptor(method);
		//			return classNode.methods.stream().filter(it -> it.desc.equals(methodDescriptor)).findFirst().orElse(null);
		//		}
		
		static final Pattern genericpattern = Pattern.compile("([\\w.]+)<(.+?)>");
		
		/**
		 * Turn a Type that has generic args into a set of Strings, since java.lang.X classes can't be found by Class.forName
		 */
		static String[] getGenericTypeParams(java.lang.reflect.Type type) {
			Matcher matcher = genericpattern.matcher(type.getTypeName());
			if (!matcher.find())
				throw new RuntimeException("couldn't parse pattern");
			
			return matcher.group(2).split(",");
		}
		
		
		static String getTypeString(Class<?> type, Type typeWithGenerics) {
			if (type.getTypeParameters().length == 0) {
				return getSimpleTypeString(type);
			}
			
			String genericTypeParams = Arrays.stream(getGenericTypeParams(typeWithGenerics))
			                                 .map(ZenDocExporter::getSimpleTypeString)
			                                 .collect(Collectors.joining(", "));
			
			return getSimpleTypeString(type) + "<" + genericTypeParams + ">";
		}
		
		
		private static void writeMethod(StringBuilder out, Method method, ZenDocMethod annotation) {
			String methodName = method.getName();
			Class<?> returnType = method.getReturnType();
			String returnTypeString = getTypeString(returnType, method.getGenericReturnType());
			out.append("```zenscript").append("\n");
			out.append(returnTypeString).append(" ").append(methodName).append("(");
			
			Parameter[] params = method.getParameters();
			
			ZenDocArg[] args = annotation.args();
			
			if (params.length != args.length) // I really tried to get rid of this, but the parameter names aren't saved by the compiler ;_;
				throw new IllegalStateException("Error in (" + method.getDeclaringClass().getName() + "#" + method.getName() + "): All arguments must have a corresponding ZenDocArg annotation!");
			
			Type[] genericParameterTypes = method.getGenericParameterTypes();
			
			if ((params.length == 0 || params.length == 1) && (args.length == 0 || args[0].info() == null || args[0].info().isEmpty())) {
				if (params.length == 1) {
					if (params[0].getDeclaredAnnotation(Optional.class) != null)
						out.append("@Optional ");
					if (params[0].getDeclaredAnnotation(ZenDocNullable.class) != null)
						out.append("@Nullable ");
					
					// append param type
					out.append(getTypeString(params[0].getType(), genericParameterTypes[0]));
					out.append(" ");
					// append param name
					out.append(args[0].value());
				}
			} else {
				out.append('\n');
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
					String typeString = getTypeString(p.getType(), genericParameterTypes[i]);
					
					String nameString = arg.value();
					
					String outString = "  " + optionalString + line + typeString + " " + nameString;
					
					if (params.length != 1)
						outString += ',';
					
					if (outString.length() > largest) {
						largest = outString.length();
					}
					
					
					parameterStrings.add(new String[] {outString, arg.info()});
				}
				
				for (String[] parameterString : parameterStrings) {
					out.append(StringUtils.rightPad(parameterString[0], largest));
					if (parameterString[1] != null && !parameterString[1].equals("")) {
						out.append(" // ").append(parameterString[1]);
					}
					
					out.append("\n");
				}
				
			}
			
			
			out.append(");\n");
			out.append("```").append("\n\n");
			String[] description = annotation.description();
			String line;
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
			ZenClass zc = type.getDeclaredAnnotation(ZenClass.class);
			if (zc != null && !Strings.isNullOrEmpty(zc.value())) {
				String[] split = zc.value().split("\\.");
				result = split[split.length - 1];
			} else if (result.equals("String")) {
				result = Format.uncapitalizeFirstLetter(result);
			}
			
			return result;
		}
		
		
		private static String getSimpleTypeString(String className) {
			if (className.contains("java.lang")) {
				String[] split = className.split("\\.");
				return split[split.length - 1];
			}
			
			Class<?> clazz;
			try {
				clazz = Class.forName(className);
			} catch (ClassNotFoundException e) {
				return className;
			}
			
			return getSimpleTypeString(clazz);
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
				return nodes.stream().collect(Collectors.toMap(
						a -> a.desc, a -> {
							Map<String, Object> annotations = new HashMap<>();
							for (int i = 0; i < a.values.size(); i += 2) {
								annotations.put((String) a.values.get(i), a.values.get(i + 1));
							}
							return annotations;
						}
				));
			}
		}
	}
	
}
