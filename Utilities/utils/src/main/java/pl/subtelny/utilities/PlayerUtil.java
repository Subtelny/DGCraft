package pl.subtelny.utilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.subtelny.utilities.messages.MessageUtil;

public final class PlayerUtil {

    public static void kick(Player player, String message) {
        player.kickPlayer(MessageUtil.color(message).replaceAll("/n", "\n"));
    }

    public static void teleportToSpawn(Player player) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawn " + player.getName());
    }

}
