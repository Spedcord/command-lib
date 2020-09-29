package xyz.spedcord.commandlib.command.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import xyz.spedcord.commandlib.permission.PermissionLevel;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SubCommand {

    PermissionLevel requiredPerms() default PermissionLevel.NORMAL;

    String args() default "";

    boolean isDefault() default false;

}
