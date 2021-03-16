package pl.subtelny.utilities;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;
import pl.subtelny.utilities.messages.Messageable;

import java.util.Objects;

public class ImprovedPlayer implements Messageable {

    private final Player player;

    private ImprovedPlayer(Player player) {
        this.player = player;
    }

    public static ImprovedPlayer of(Player player) {
        return new ImprovedPlayer(player);
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    @Override
    public void sendMessage(BaseComponent component) {
        player.sendMessage(component);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImprovedPlayer that = (ImprovedPlayer) o;
        return Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player);
    }

}
