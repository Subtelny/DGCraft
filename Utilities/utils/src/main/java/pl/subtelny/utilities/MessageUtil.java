package pl.subtelny.utilities;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public final class MessageUtil {

    public static boolean message(CommandSender sender, String message) {
        sender.sendMessage(color(message).replaceAll("/n", "\n"));
        return true;
    }

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}