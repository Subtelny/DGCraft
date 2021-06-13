package pl.subtelny.islands.island.crates;

import org.bukkit.entity.Player;
import pl.subtelny.islands.island.IslandId;

public interface IslandCrates {

    void openCrate(Player player, String crateName);

    void openCrate(Player player, IslandId islandId, String crate);

    void reload();

}
