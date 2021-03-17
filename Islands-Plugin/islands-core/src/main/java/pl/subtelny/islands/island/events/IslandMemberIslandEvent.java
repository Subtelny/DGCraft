package pl.subtelny.islands.island.events;

import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandMember;

import java.util.Objects;

public abstract class IslandMemberIslandEvent {

    private final IslandMember islandMember;

    private final Island island;

    protected IslandMemberIslandEvent(IslandMember islandMember, Island island) {
        this.islandMember = islandMember;
        this.island = island;
    }

    public IslandMember getIslandMember() {
        return islandMember;
    }

    public Island getIsland() {
        return island;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IslandMemberIslandEvent that = (IslandMemberIslandEvent) o;
        return Objects.equals(islandMember, that.islandMember) && Objects.equals(island, that.island);
    }

    @Override
    public int hashCode() {
        return Objects.hash(islandMember, island);
    }
}
