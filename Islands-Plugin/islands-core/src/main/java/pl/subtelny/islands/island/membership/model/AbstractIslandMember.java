package pl.subtelny.islands.island.membership.model;

import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMember;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.utilities.Validation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractIslandMember implements IslandMember {

    private final List<IslandId> islandIds;

    protected AbstractIslandMember(List<IslandId> islandIds) {
        this.islandIds = islandIds;
    }

    @Override
    public boolean hasIsland(IslandType islandType) {
        return islandIds.stream()
                .anyMatch(islandId -> islandId.getIslandType().equals(islandType));
    }

    @Override
    public void addIsland(Island island) {
        Validation.isTrue(island.isMemberOfIsland(this), "islandMember.cannot_add_island_not_member");
        islandIds.add(island.getId());
    }

    @Override
    public void removeIsland(Island island) {
        //todo
    }

    @Override
    public List<IslandId> getIslands() {
        return new ArrayList<>(islandIds);
    }

    @Override
    public List<IslandId> getIslands(IslandType islandType) {
        return getIslands().stream()
                .filter(islandId -> islandId.getIslandType().equals(islandType))
                .collect(Collectors.toList());
    }
}
