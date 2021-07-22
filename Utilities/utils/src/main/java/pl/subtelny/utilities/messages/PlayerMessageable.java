package pl.subtelny.utilities.messages;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerMessageable implements Messageable {

    private final UUID uuid;

    public PlayerMessageable(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void sendMessage(String message) {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null && player.isOnline()) {
            MessageUtil.message(player, message);
        }
    }

    @Override
    public void sendMessage(BaseComponent component) {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null && player.isOnline()) {
            player.sendMessage(component);
        }
    }
}
