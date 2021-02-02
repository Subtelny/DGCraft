package pl.subtelny.islands.island.crate.search.reward;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.ComponentProvider;
import pl.subtelny.islands.Islands;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.action.AskJoinIslandAction;
import pl.subtelny.utilities.reward.Reward;

public class AskJoinReward implements Reward {

    private final ComponentProvider componentProvider;

    private final Island island;

    public AskJoinReward(ComponentProvider componentProvider,
                         Island island) {
        this.componentProvider = componentProvider;
        this.island = island;
    }

    @Override
    public void admitReward(Player player) {
        AskJoinIslandAction action = componentProvider.createComponent(Islands.plugin, AskJoinIslandAction.class);
        action.perform(island, player);
    }
}
