package pl.subtelny.commands.api;

import pl.subtelny.components.core.api.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface PluginSubCommand {

    Class mainCommand();

    String command();

}