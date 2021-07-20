package pl.subtelny.islands.api.events;

import pl.subtelny.islands.event.IslandEvent;
import pl.subtelny.islands.api.Island;
import pl.subtelny.islands.api.IslandMember;

public class IslandMemberLeftIslandEvent extends IslandMemberIslandEvent implements IslandEvent {

    public IslandMemberLeftIslandEvent(IslandMember islandMember, Island island) {
        super(islandMember, island);
    }

}
