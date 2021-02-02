package pl.subtelny.commands.api.util;

import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginCommand;
import pl.subtelny.commands.api.PluginSubCommand;

public final class CommandUtil {

    public static String getCommandPath(Class<? extends BaseCommand> clazz) {
        String commandPath = getCommandPath(clazz, "");
        return "/" + commandPath;
    }

    private static String getCommandPath(Class<? extends BaseCommand> clazz, String arg) {
        if (isMainCommand(clazz)) {
            PluginCommand baseCommand = clazz.getAnnotation(PluginCommand.class);
            String cmd = baseCommand.command();
            return constructPath(arg, cmd);
        }

        PluginSubCommand subCommand = clazz.getAnnotation(PluginSubCommand.class);
        String cmd = subCommand.command();
        String constructPath = constructPath(arg, cmd);
        return getCommandPath(subCommand.mainCommand(), constructPath);
    }

    private static String constructPath(String arg, String cmd) {
        if (arg.isEmpty()) {
            return cmd;
        }
        return cmd + " " + arg;
    }

    private static boolean isMainCommand(Class<? extends BaseCommand> clazz) {
        return clazz.isAnnotationPresent(PluginCommand.class);
    }

}
