//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package btpos.dj2addons.common.util.zendoc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Inside a class marked with {@link btpos.dj2addons.common.util.zendoc.ZenDocClass}, mark this method as needing to be added to the documentation.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ZenDocMethod {
	/**
	 * The placement of this method in the list.
	 */
	int order() default 0;
	
	/**
	 * Displayed after the declaration in the generated file.
	 */
	String[] description() default {};
	
	/**
	 * The names and descriptions of this method's parameters, in order.
	 *
	 * <p>The parameter names get removed from the code before MM ZenDoc sees it, so if the names aren't specified it'll show up as "p0", "p1", etc.
	 */
	ZenDocArg[] args() default {};
}
