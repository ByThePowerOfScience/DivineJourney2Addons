package btpos.dj2addons.common.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.World;
import sun.reflect.generics.repository.ClassRepository;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Consumer;

/**
 * Utility class to dump the entire object graph of a given object to string using reflection.<p>
 * This version contains a powerful iterative algorithm that can traverse extremely long object graphs,
 * bounded by memory and not runtime stack frames.
 *
 * Edited by ByThePowerOfScience to use consumers and exclusions to save the heap.
 * @author  Bryan Wagner
 * @since   2013-08-24
 * @version 2015-09-19
 */
@SuppressWarnings("ALL")
public class StringDumpUtils {
	
	public static final String INDENT        = "  ";
	public static final String SYS_ID_FORMAT = "sysId#%d";
	public static final String SUPER_PREFIX  = "super.";
	public static final String ERROR_FORMAT  = "!%s:%s";
	
	public static void dump(Object object) {
		dump(object, System.out::println);
	}
	
	static final Object[] fieldTypeExclusions = new Object[] {
			World.class,
			"io.netty",
			ClassRepository.class
	};
	static final Map<String, List<String>> fieldNameExclusions = ImmutableMap.of(
			"net.minecraftforge.registries.RegistryDelegate", ImmutableList.of("type")
	);
	
	
	/**
	 * Iteratively traverses the object graph of the given object, dump all values and references using a custom format. Does not format static fields.<p>
	 * @param object the {@code Object} to dump
	 */
	public static void dump(Object object, Consumer<String> out) {
		dump(object, false, out, -1);
	}
	
	public static void dump(Object object, Consumer<String> out, int maxSupers) {
		dump(object, false, out, maxSupers);
	}
	
	/**
	 * Iteratively traverses the object graph of the given object, dump all values and references using a custom format.<p>
	 * Parses all fields of the runtime class including super class fields, which are successively prefixed with "{@link #SUPER_PREFIX}" at each level.<p>
	 * {@code Number}s, {@code enum}s, and {@code null} references are formatted using the standard {@code String#valueOf()} method.
	 * {@code CharSequences}s are wrapped with quotes.<p>
	 * The traversal implements an iterative algorithm based on a stack data structure, so the traversal is limited by memory and not runtime stack frames.<p>
	 * Backwards references are tracked using a "visitor map" which is an instance of {@link IdentityHashMap}.
	 * When an existing object reference is encountered the system identity hashcode is printed as a leaf (to avoid infinite loops).<p>
	 *
	 * @param object       the {@code Object} to dump
	 * @param isIncludingStatics {@code true} if {@code static} fields should be dumped, {@code false} to skip them
	 */
	public static void dump(Object object, boolean isIncludingStatics, Consumer<String> out, int maxSupers) {
		StringBuilder                   builder    = new StringBuilder();
		Stack<Object[]>                 stack      = new Stack<>();
		Map<Object, Object> visitorMap             = new IdentityHashMap<>();
		TreeMap<String, Field>          fieldMap   = new TreeMap<>();  // can modify this to change or omit the sort order
		ArrayList<Entry<String, Field>> fieldList  = new ArrayList<>();
		
		Object        endArray    = new Object();  // signals the end bracket of an array
		Object        endObject   = new Object();  // signals the end bracket of an object
		StringBuilder emptyString = new StringBuilder();
		stack.push(new Object[] {emptyString, emptyString, object});
		
		while (!stack.isEmpty()) {
			Object[] params = stack.pop();
			StringBuilder indents = (StringBuilder) params[0];
			StringBuilder label = (StringBuilder) params[1];
			Object next = params[2];
			fieldMap.clear();
			fieldList.clear();
			builder.append(indents).append(label);
			
			if (next == null ||
			    next instanceof Number || next instanceof Character || next instanceof Boolean ||
			    next.getClass().isPrimitive() || next.getClass().isEnum())
			{
				builder.append(next);
			} else if (next == endArray) {
				builder.append("]");
			} else if (next == endObject) {
				builder.append("}");
			} else if (next instanceof CharSequence) {
				builder.append("\"").append(next).append("\"");
			} else {
				int sysId = System.identityHashCode(next);
				
				if (visitorMap.containsKey(next)) {
					builder.append("(").append(String.format(SYS_ID_FORMAT, sysId)).append(")");
				} else {
					visitorMap.put(next, next);
					
					if (next.getClass().isArray()) {
						builder.append("[")
						       .append(next.getClass().getName())
						       .append(":")
						       .append(String.format(SYS_ID_FORMAT, sysId));
						int length = Array.getLength(next);
						if (length == 0) {
							builder.append("]");
						} else {
							stack.push(new Object[] {indents, emptyString, endArray});  // add on its own line
							StringBuilder nextTab = new StringBuilder().append(indents).append(INDENT);
							for (int i = length - 1; i >= 0; i--) {
								Object arrayObject = Array.get(next, i);
								StringBuilder nextLabel = new StringBuilder().append("\"").append(i).append("\":");
								stack.push(new Object[] {nextTab, nextLabel, arrayObject});
							}
						}
					} else {
						// enumerate the desired fields of the object before accessing
						StringBuilder superPrefix = new StringBuilder();
						int supersTraversed = 0;
						for (Class<?> clazz = next.getClass(); clazz != null && !clazz.equals(Object.class); clazz = clazz.getSuperclass())
						{
							if (maxSupers != -1 && supersTraversed > maxSupers)
								break;
							Field[] fields = clazz.getDeclaredFields();
							findFields:
							for (Field field : fields) {
								for (Object exclusion : fieldTypeExclusions) {
									if (exclusion instanceof Class && field.getType().isAssignableFrom((Class<?>) exclusion) || ((Class<?>) exclusion).isAssignableFrom(field.getType()))
										continue findFields;
									else if (exclusion instanceof String) { // Packages
										if (field.getType().getPackage().toString().contains((String)exclusion))
											continue findFields;
									}
								}
								List<String> exclusionsForClass = fieldNameExclusions.get(clazz.getName());
								if (exclusionsForClass != null && exclusionsForClass.contains(field.getName())) {
									continue findFields;
								}
								if (isIncludingStatics || !Modifier.isStatic(field.getModifiers())) {
									fieldMap.put(superPrefix + field.getName(), field);
								}
							}
							superPrefix.append(SUPER_PREFIX);
							supersTraversed++;
						}
						// add in sorted order
						fieldList.addAll(fieldMap.entrySet());
						
						builder.append("{")
						       .append(next.getClass().getName())
						       .append(":")
						       .append(String.format(SYS_ID_FORMAT, sysId));
						if (fieldList.isEmpty()) {
							builder.append("}");
						} else {
							stack.push(new Object[] {indents, emptyString, endObject});  // add on its own line
							StringBuilder nextTab = new StringBuilder().append(indents).append(INDENT);
							for (int i = fieldList.size() - 1; i >= 0; i--) {
								Entry<String, Field> entry = fieldList.get(i);
								String name = entry.getKey();
								Field field = entry.getValue();
								Object fieldObject;
								try {
									field.setAccessible(true);
									fieldObject = field.get(next);
								} catch (Throwable e) {
									fieldObject = String.format(ERROR_FORMAT,
									                            e.getClass().getName(),
									                            e.getMessage());
								}
								StringBuilder nextLabel = new StringBuilder().append("\"")
								                                             .append(name)
								                                             .append("\":");
								stack.push(new Object[] {nextTab, nextLabel, fieldObject});
							}
						}
					}
				}
			}
			if (!stack.isEmpty()) {
				out.accept(builder.toString());
				builder = new StringBuilder();
			}
		}
		out.accept(builder.toString());
	}
}
