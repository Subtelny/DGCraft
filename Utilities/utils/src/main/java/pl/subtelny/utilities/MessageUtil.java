package pl.subtelny.utilities;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public final class MessageUtil {

    public static boolean error(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        return true;
    }

    public static boolean message(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        return true;
    }

}
