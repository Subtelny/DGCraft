package pl.subtelny.islands.api.events;

import pl.subtelny.islands.event.IslandEvent;
import pl.subtelny.islands.api.Island;
import pl.subtelny.islands.api.IslandMember;

public class IslandMemberJoinedIslandEvent extends IslandMemberIslandEvent implements IslandEvent {

    public IslandMemberJoinedIslandEvent(IslandMember islandMember, Island island) {
        super(islandMember, island);
    }

}
