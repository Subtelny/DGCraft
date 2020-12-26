package pl.subtelny.islands.island.skyblockisland.crate.search;

import org.bukkit.entity.Player;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.message.IslandMessages;
import pl.subtelny.utilities.reward.Reward;

public class IslandSearchReward implements Reward {

    private final IslanderQueryService islanderQueryService;

    private final Island island;

    public IslandSearchReward(IslanderQueryService islanderQueryService,
                              Island island) {
        this.islanderQueryService = islanderQueryService;
        this.island = island;
    }

    @Override
    public void admitReward(Player player) {
        Islander islander = islanderQueryService.getIslander(player);
        island.askJoin(islander);

        IslandMessages.get().sendTo(player, "reward.island_search.sent_to", island.getName());
    }
}
