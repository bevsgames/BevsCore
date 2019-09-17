package games.bevs.core.module.abilties;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface AbilityInfo {
	String name();

	String author() default "Unknown";

	String[] description();
}
