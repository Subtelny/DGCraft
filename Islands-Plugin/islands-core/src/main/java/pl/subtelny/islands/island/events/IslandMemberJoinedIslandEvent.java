package pl.subtelny.islands.island.events;

import pl.subtelny.islands.event.IslandEvent;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandMember;

public class IslandMemberJoinedIslandEvent extends IslandMemberIslandEvent implements IslandEvent {

    public IslandMemberJoinedIslandEvent(IslandMember islandMember, Island island) {
        super(islandMember, island);
    }

}
