package pl.subtelny.islands.island.membership.model;

import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMember;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.query.IslandQueryService;
import pl.subtelny.utilities.Validation;

import java.util.List;
import java.util.Optional;

public abstract class AbstractIslandMember implements IslandMember {

    private final List<IslandId> islandIds;

    private final IslandQueryService islandQueryService;

    protected AbstractIslandMember(List<IslandId> islandIds, IslandQueryService islandQueryService) {
        this.islandIds = islandIds;
        this.islandQueryService = islandQueryService;
    }

    @Override
    public boolean hasIsland(IslandType islandType) {
        return islandIds.stream()
                .anyMatch(islandId -> islandQueryService.getIslandType(islandId).equals(islandType));
    }

    @Override
    public void addIsland(Island island) {
        Validation.isTrue(island.isMemberOfIsland(this), "islandMember.cannot_add_island_not_member");
        islandIds.add(island.getId());
    }

    @Override
    public List<Island> getIslands() {
        return islandQueryService.getIslands(islandIds);
    }

    @Override
    public Optional<Island> findIsland(IslandType islandType) {
        return getIslands().stream()
                .filter(island -> island.getIslandType().equals(islandType))
                .findFirst();
    }

}
