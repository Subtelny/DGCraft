package pl.subtelny.commands.api;

import pl.subtelny.components.core.api.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface PluginCommand {

    String command();

    String permission() default "";

    String[] aliases() default {};

}
