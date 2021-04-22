package pl.subtelny.islands.island.events;

import pl.subtelny.islands.event.IslandEvent;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandMember;

import java.util.Objects;

public class DeletedIslandEvent implements IslandEvent {

    private final Island island;

    private final IslandMember initiator;

    public DeletedIslandEvent(Island island, IslandMember initiator) {
        this.island = island;
        this.initiator = initiator;
    }

    public Island getIsland() {
        return island;
    }

    public IslandMember getInitiator() {
        return initiator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeletedIslandEvent that = (DeletedIslandEvent) o;
        return Objects.equals(island, that.island) && Objects.equals(initiator, that.initiator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(island, initiator);
    }
}
