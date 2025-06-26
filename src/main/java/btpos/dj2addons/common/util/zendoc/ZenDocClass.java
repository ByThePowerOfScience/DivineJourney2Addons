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
 * Tells the MM ZenDocExporter to document this class and its members (if any).
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ZenDocClass {
	/**
	 * The name of this class in ZenScript.
	 */
	String value() default "";
	
	/**
	 * This class's description. Will be printed above the list of methods.
	 */
	String[] description() default {};
	
	/**
	 * Set to true if this class should not have its own file generated and only be included as a part of another class's documentation via {@link btpos.dj2addons.common.util.zendoc.ZenDocInclude}
	 */
	boolean onlyInOther() default false;
}
