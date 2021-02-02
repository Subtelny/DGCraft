package pl.subtelny.islands.island.crate;

import org.bukkit.entity.Player;
import pl.subtelny.islands.island.Island;

public interface IslandCrates {

    void openMainCrate(Player player);

    void openCreateCrate(Player player);

    void openInvitesCrate(Player player, Island island);

    void openSearchCrate(Player player);

    void openCrate(Player player, String rawCrateId);

    void reloadCrates();

}
