package pl.subtelny.islands.island.crate.invites.reward;

import org.bukkit.entity.Player;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandMember;
import pl.subtelny.utilities.reward.Reward;

public class AcceptAskJoinReward implements Reward {

    private final Island island;

    private final IslandMember islandMember;

    public AcceptAskJoinReward(Island island, IslandMember islandMember) {
        this.island = island;
        this.islandMember = islandMember;
    }

    @Override
    public void admitReward(Player player) {
        island.acceptAskJoin(islandMember);
    }
}
