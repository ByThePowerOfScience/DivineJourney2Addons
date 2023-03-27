//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.btpos.dj2addons.util.zendoc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tells the ZenDocExporter to document this class and its members (if any).
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ZenDocClass {
	String value() default "";
	
	String[] description() default {};
	
	/**
	 * Set to true if this class should not have its own file generated and only be included as a part of another class's documentation.
	 */
	boolean onlyInOther() default false;
}
