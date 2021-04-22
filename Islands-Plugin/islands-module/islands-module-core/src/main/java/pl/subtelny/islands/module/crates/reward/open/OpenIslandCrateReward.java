package pl.subtelny.islands.module.crates.reward.open;

import org.bukkit.entity.Player;
import pl.subtelny.islands.island.crates.IslandCrates;
import pl.subtelny.utilities.reward.Reward;

public class OpenIslandCrateReward implements Reward {

    private final IslandCrates islandCrates;

    private final String crateName;

    public OpenIslandCrateReward(IslandCrates islandCrates, String crateName) {
        this.islandCrates = islandCrates;
        this.crateName = crateName;
    }

    @Override
    public void admitReward(Player player) {
        islandCrates.openCrate(player, crateName);
    }

}
