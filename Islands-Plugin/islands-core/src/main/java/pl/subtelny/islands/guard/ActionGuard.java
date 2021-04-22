package pl.subtelny.islands.guard;

import pl.subtelny.islands.island.cqrs.query.IslandQueryService;
import pl.subtelny.islands.islander.IslanderQueryService;

public abstract class ActionGuard {

    protected final IslandQueryService islandQueryService;

    protected final IslanderQueryService islanderQueryService;

    ActionGuard(IslandQueryService islandQueryService, IslanderQueryService islanderQueryService) {
        this.islandQueryService = islandQueryService;
        this.islanderQueryService = islanderQueryService;
    }
}
