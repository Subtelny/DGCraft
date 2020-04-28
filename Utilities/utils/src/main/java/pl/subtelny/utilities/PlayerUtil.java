package pl.subtelny.utilities;

import org.bukkit.entity.Player;

public final class PlayerUtil {

    public static void kick(Player player, String message) {
        player.kickPlayer(MessageUtil.color(message).replaceAll("/n", "\n"));
    }

}
