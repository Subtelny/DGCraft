package pl.subtelny.islands.islander;

import org.bukkit.entity.Player;
import pl.subtelny.islands.api.IslanderId;

public abstract class IslanderService {

    protected IslanderId getIslanderId(Player player) {
        return IslanderId.of(player.getUniqueId());
    }

}
