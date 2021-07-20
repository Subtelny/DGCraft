package pl.subtelny.islands.module.crates.reward.open;

import org.bukkit.entity.Player;
import pl.subtelny.islands.api.module.component.CratesComponent;
import pl.subtelny.utilities.reward.Reward;

public class OpenIslandCrateReward implements Reward {

    private final CratesComponent islandCrates;

    private final String crateName;

    public OpenIslandCrateReward(CratesComponent islandCrates, String crateName) {
        this.islandCrates = islandCrates;
        this.crateName = crateName;
    }

    @Override
    public void admitReward(Player player) {
        islandCrates.openCrate(player, crateName);
    }

}
