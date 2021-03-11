package pl.subtelny.islands.island.crate;

import org.bukkit.entity.Player;
import pl.subtelny.islands.island.Island;

public interface IslandCrates {

    void openInvites(Player player, Island island);

    void openSettings(Player player, Island island);

    void openCreateIsland(Player player);

    void openMain(Player player);

    void reload();

}
