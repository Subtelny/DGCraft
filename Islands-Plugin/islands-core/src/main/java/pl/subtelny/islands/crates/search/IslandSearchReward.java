package pl.subtelny.islands.crates.search;

import org.bukkit.entity.Player;
import pl.subtelny.islands.crates.IslandReward;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.module.IslandModule;

public class IslandSearchReward implements IslandReward {

    private final IslandModule<? extends Island> islandModule;

    public IslandSearchReward(IslandModule<? extends Island> islandModule) {
        this.islandModule = islandModule;
    }

    @Override
    public void admitReward(Player player) {
        islandModule.getIslandCrates().openSearchCrate(player);
    }

}
