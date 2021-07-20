package pl.subtelny.islands.api;

import pl.subtelny.utilities.messages.Messageable;

import java.util.List;
import java.util.Optional;

public interface IslandMember extends Messageable {

    IslandMemberId getIslandMemberId();

    List<IslandId> getIslands();

    List<IslandId> getIslands(IslandType islandType);

    default Optional<IslandId> getIsland(IslandType islandType) {
        return getIslands(islandType).stream().findFirst();
    }

    String getName();

    boolean hasIsland(IslandType islandType);

    boolean isOnline();

    void addIsland(Island island);

    void leaveIsland(Island island);

}
