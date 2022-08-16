import epicsquid.roots.util.StringHelper;
import epicsquid.roots.util.zen.*;
import org.apache.commons.lang3.StringUtils;
import org.btpos.dj2addons.crafttweaker.bewitchment.Rituals;
import org.btpos.dj2addons.crafttweaker.bewitchment.WitchesAltar;
import org.btpos.dj2addons.crafttweaker.bloodmagic.ZenSoulForge;
import org.btpos.dj2addons.crafttweaker.botania.Brews;
import org.btpos.dj2addons.crafttweaker.extremereactors.CTExtremeReactors;
import org.btpos.dj2addons.crafttweaker.extremereactors.CTReactorInterior;
import org.btpos.dj2addons.crafttweaker.totemic.Instruments;
import stanhebben.zenscript.annotations.Optional;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Sources:
 * <p>https://github.com/MysticMods/Roots/blob/release/3.1.5/src/main/java/epicsquid/roots/ExportDocumentation.java
 * <p>https://github.com/MysticMods/Roots/blob/release/3.1.5/src/main/java/epicsquid/roots/util/zen/ZenDocExporter.java
 *
 * <p>Original author: MysticMods<p>
 * Seriously I didn't write a word of this; this incredible piece of code wouldn't even be copied if it didn't have some hardcoded paths.
 */
public class ExportZenDocs {
	public static void main(String[] args) {
		String targetPath = "./docs/zs/";
		
		Class<?>[] classes = new Class[] {
				Rituals.class,
				WitchesAltar.class,
				ZenSoulForge.class,
				Brews.class,
				Brews.ZenBrew.class,
				Instruments.class,
				CTExtremeReactors.class,
				CTReactorInterior.class
		}; //TODO automate this
		ZenDocExporter export = new ZenDocExporter();
		Path path = Paths.get(targetPath);
		
		try {
			Files.createDirectories(path);
			export.export(path, classes);
		} catch (IOException var6) {
			var6.printStackTrace();
		}
		
	}
	
	private static class ZenDocExporter {
		private static String className = "";
		
		public ZenDocExporter() {
		}
		
		public void export(Path path, Class<?>[] classes) {
			for(int i = 0; i < classes.length; ++i) {
				className = "";
				StringBuilder out = new StringBuilder();
				ZenDocClass zenClass = classes[i].getDeclaredAnnotation(ZenDocClass.class);
				ZenDocAppend zenDocAppend = classes[i].getDeclaredAnnotation(ZenDocAppend.class);
				if (zenClass != null) {
					if (i > 0) {
						out.append("\n");
					}
					
					String[] h3 = zenClass.value().split("\\.");
					String zenClassName = h3[h3.length - 1];
					className = zenClass.value();
					out.append("### Class\n");
					out.append("\n");
					out.append("```zenscript").append("\n");
					out.append("import ").append(zenClass.value()).append(";").append("\n");
					out.append("```").append("\n");
					out.append("\n");
					String[] description = zenClass.description();
					if (description.length > 0) {
						
						for (String line : description) {
							out.append(this.parse(line)).append("\n");
						}
						
						out.append("\n");
					}
					
					Method[] methods = classes[i].getDeclaredMethods();
					List<MethodAnnotationPair> methodList = this.getSortedMethodList(methods);
					Field[] fields = classes[i].getDeclaredFields();
					List<PropertyAnnotationPair> fieldList = this.getSortedFieldList(fields);
					List<MethodAnnotationPair> staticMethodList = methodList.stream().filter((pair) -> Modifier.isStatic(pair.type.getModifiers())).collect(Collectors.toList());
					if (!methodList.isEmpty()) {
						out.append("#### Methods\n");
						out.append("\n");
					}
					
					methodList = methodList.stream().filter((pair) -> !Modifier.isStatic(pair.type.getModifiers())).collect(Collectors.toList());
					if (!staticMethodList.isEmpty()) {
						this.writeMethodList(out, staticMethodList);
					}
					
					if (!methodList.isEmpty()) {
						this.writeMethodList(out, methodList);
					}
					
					if (!fieldList.isEmpty()) {
						out.append("### Static Properties\n");
						out.append("\n```zenscript\n");
						this.writePropertyList(out, fieldList);
						out.append("\n```");
					}
					
					if (zenDocAppend != null) {
						String[] toAppend = zenDocAppend.value();
						out.append("\n");
						
						for (String s : toAppend) {
							Path p = Paths.get("./" + s);
							
							try {
								List<String> lines = Files.readAllLines(p);
								
								for (String line : lines) {
									out.append(line).append("\n");
								}
							} catch (IOException var25) {
								var25.printStackTrace();
							}
						}
					}
					
					try {
						Files.write(path.resolve(zenClassName.toLowerCase() + ".md"), out.toString().getBytes());
					} catch (IOException var24) {
						var24.printStackTrace();
					}
				}
			}
			
		}
		
		private void writeMethodList(StringBuilder out, List<MethodAnnotationPair> staticMethodList) {
			for(int j = 0; j < staticMethodList.size(); ++j) {
				if (j > 0) {
					out.append("\n");
				}
				
				this.writeMethod(out, staticMethodList.get(j).type, staticMethodList.get(j).annotation);
			}
			
		}
		
		private void writePropertyList(StringBuilder out, List<PropertyAnnotationPair> staticPropertyList) {
			for(int j = 0; j < staticPropertyList.size(); ++j) {
				if (j > 0) {
					out.append("\n");
				}
				
				this.writeProperty(out, staticPropertyList.get(j).type, staticPropertyList.get(j).annotation);
			}
			
		}
		@SuppressWarnings("rawtypes")
		private void writeMethod(StringBuilder out, Method method, ZenDocMethod annotation) {
			String methodName = method.getName();
			Class<?> returnType = method.getReturnType();
			String returnTypeString = this.getSimpleTypeString(returnType);
			out.append("```zenscript").append("\n");
			out.append(returnTypeString).append(" ").append(methodName).append("(");
			Annotation[][] parameterAnnotations = method.getParameterAnnotations();
			Class[] types = method.getParameterTypes();
			ZenDocArg[] args = annotation.args();
			if (types.length != args.length) {
				throw new IllegalStateException("Wrong number of parameter names found for type: " + methodName);
			} else {
				if (args.length > 0) {
					out.append("\n");
				}
				
				int largest = 0;
				String[] parameterStrings = new String[types.length];
				
				int k;
				String line;
				for(k = 0; k < types.length; ++k) {
					boolean optional = false;
					boolean nullable = false;
					Annotation[] var15 = parameterAnnotations[k];
					
					for (Annotation parameterAnnotation : var15) {
						if (parameterAnnotation instanceof Optional) {
							optional = true;
						}
						
						if (parameterAnnotation instanceof ZenDocNullable) {
							nullable = true;
						}
					}
					
					String optionalString = optional ? "@Optional " : "";
					line = nullable ? "@Nullable " : "";
					String typeString = this.getSimpleTypeString(types[k]);
					String nameString = args[k].arg();
					if (k < types.length - 1) {
						parameterStrings[k] = "  " + optionalString + line + typeString + " " + nameString + ",";
					} else {
						parameterStrings[k] = "  " + optionalString + typeString + " " + nameString;
					}
					
					if (parameterStrings[k].length() > largest) {
						largest = parameterStrings[k].length();
					}
				}
				
				for(k = 0; k < parameterStrings.length; ++k) {
					parameterStrings[k] = StringUtils.rightPad(parameterStrings[k], largest);
					out.append(parameterStrings[k]);
					if (!args[k].info().isEmpty()) {
						out.append(" // ").append(args[k].info());
					}
					
					out.append("\n");
				}
				
				out.append(");\n");
				out.append("```").append("\n\n");
				String[] description = annotation.description();
				if (description.length > 0) {
					
					for (String s : description) {
						line = s;
						out.append(this.parse(line));
					}
				}
				
				out.append("\n---\n\n");
			}
		}
		
		private void writeProperty(StringBuilder out, Field field, ZenDocProperty annotation) {
			String fieldName = field.getName();
			String[] h3 = className.split("\\.");
			String zenClassName = h3[h3.length - 1];
			out.append(zenClassName).append(".").append(fieldName).append(" // ");
			ZenDocProperty propertyAnnotation = field.getAnnotation(ZenDocProperty.class);
			String[] var8 = propertyAnnotation.description();
			
			for (String line : var8) {
				out.append(line);
			}
			
			out.append("\n");
		}
		
		private String parse(String line) {
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
		
		private List<MethodAnnotationPair> getSortedMethodList(Method[] methods) {
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
		
		private List<PropertyAnnotationPair> getSortedFieldList(Field[] fields) {
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
		
		private String getSimpleTypeString(Class type) {
			String result = type.getSimpleName();
			if (result.startsWith("Zen")) {
				result = result.substring(3);
			} else if (result.startsWith("String")) {
				result = StringHelper.lowercaseFirstLetter(result);
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
	}
	
}
