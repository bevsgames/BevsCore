package games.bevs.core.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface ModInfo 
{
	public String name();
	public String[] alises() default {};
	public boolean hasCommand() default true;
	public boolean hasConfig() default false;
}
