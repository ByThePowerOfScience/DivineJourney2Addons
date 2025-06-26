//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package btpos.dj2addons.common.util.zendoc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ZenDocArg {
	/**
	 * The name of the parameter.  Needed since MM ZenDoc can't see parameter names in the compiled code.
	 */
	String value();
	
	/**
	 * Documentation for the parameter.  Will show up as a comment in the method declaration.
	 */
	String info() default "";
}
