package pl.subtelny.islands.island.crate.search.condition;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.ComponentProvider;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.messages.MessageKey;

public class AskJoinIslandCondition implements Condition {

    private final Island island;

    private final ComponentProvider componentProvider;

    public AskJoinIslandCondition(ComponentProvider componentProvider, Island island) {
        this.island = island;
        this.componentProvider = componentProvider;
    }

    @Override
    public boolean satisfiesCondition(Player player) {
        Islander islander = componentProvider.getComponent(IslanderQueryService.class).getIslander(player);
        return island.canAskJoin(islander);
    }

    @Override
    public MessageKey getMessageKey() {
        return new MessageKey("condition.ask_join.not_satisfied");
    }
}
