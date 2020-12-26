package pl.subtelny.islands.island.crate.create;

import org.bukkit.entity.Player;
import pl.subtelny.islands.island.crate.IslandReward;
import pl.subtelny.islands.island.IslandCreateService;
import pl.subtelny.islands.island.IslandType;

public class IslandCreateReward implements IslandReward {

    private final IslandCreateService islandCreateService;

    private final IslandType islandType;

    public IslandCreateReward(IslandCreateService islandCreateService, IslandType islandType) {
        this.islandCreateService = islandCreateService;
        this.islandType = islandType;
    }

    @Override
    public void admitReward(Player player) {
        islandCreateService.createIsland(player, islandType);
    }

}
