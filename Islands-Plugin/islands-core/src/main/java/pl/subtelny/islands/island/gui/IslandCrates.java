package pl.subtelny.islands.island.gui;

import org.bukkit.entity.Player;

public interface IslandCrates {

    void openMainCrate(Player player);

    void openCreateCrate(Player player);

    void openSearchCrate(Player player);

    void reloadCrates();

}
