package pl.subtelny.islands.island.events;

import pl.subtelny.islands.event.IslandEvent;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandMember;

public class IslandMemberLeftIslandEvent extends IslandMemberIslandEvent implements IslandEvent {

    public IslandMemberLeftIslandEvent(IslandMember islandMember, Island island) {
        super(islandMember, island);
    }

}
