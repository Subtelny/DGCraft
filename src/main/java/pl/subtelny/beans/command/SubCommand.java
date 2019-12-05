package pl.subtelny.beans.command;

import pl.subtelny.beans.Component;
import pl.subtelny.command.BaseCommand;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SubCommand {

    Class<? extends BaseCommand> baseCommand();

    String command();

}
