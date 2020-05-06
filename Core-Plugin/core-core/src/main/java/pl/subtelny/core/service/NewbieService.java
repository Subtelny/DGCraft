package pl.subtelny.core.service;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class NewbieService {

    private Set<Player> newbies = new HashSet<>();

    public void unmarkNewbie(Player player) {
        newbies.remove(player);
    }

    public void markAsNewbie(Player player) {
        newbies.add(player);
    }

    public boolean isNewbie(Player player) {
        return newbies.contains(player);
    }

}
