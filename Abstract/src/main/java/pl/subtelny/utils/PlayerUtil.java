package pl.subtelny.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;

public final class PlayerUtil {

    public static void message(Player player, String[] messages) {
        String[] coloredMessages = Arrays.stream(messages).map(PlayerUtil::colorMessage).toArray(String[]::new);
        player.sendMessage(coloredMessages);
    }

    private static String colorMessage(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static void message(Player player, String message) {
        String coloredMessage = colorMessage(message);
        player.sendMessage(coloredMessage);
    }

}
