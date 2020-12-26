package pl.subtelny.islands.island.crate.open;

import org.bukkit.entity.Player;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.crate.IslandCrates;
import pl.subtelny.islands.island.module.IslandModule;
import pl.subtelny.utilities.reward.Reward;

public class IslandOpenCrateReward implements Reward {

    private final IslandModule<? extends Island> islandModule;

    private final String rawCrateId;

    public IslandOpenCrateReward(IslandModule<? extends Island> islandModule, String rawCrateId) {
        this.islandModule = islandModule;
        this.rawCrateId = rawCrateId;
    }

    @Override
    public void admitReward(Player player) {
        IslandCrates islandCrates = islandModule.getIslandCrates();
        islandCrates.openCrate(player, rawCrateId);
    }
}
