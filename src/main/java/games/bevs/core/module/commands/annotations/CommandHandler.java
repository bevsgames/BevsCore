package games.bevs.core.module.commands.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import games.bevs.core.client.Rank;

@Target(value={ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface CommandHandler
{
	public String name();
    public String permission() default "";

    public String noPerm() default "You do not have permission to perform that action";

    public String[] aliases() default {};

    public String description() default "";

    public String usage() default "";

    public boolean inGameOnly() default false;
    
    public Rank requiredRank();
}
