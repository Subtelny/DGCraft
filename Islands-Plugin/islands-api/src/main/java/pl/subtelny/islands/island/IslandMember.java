package pl.subtelny.islands.island;

import java.util.List;
import java.util.Optional;

public interface IslandMember {

    IslandMemberId getIslandMemberId();

    List<IslandId> getIslands();

    List<IslandId> getIslands(IslandType islandType);

    default Optional<IslandId> getIsland(IslandType islandType) {
        return getIslands(islandType).stream().findFirst();
    }

    String getName();

    boolean hasIsland(IslandType islandType);

    void addIsland(Island island);

    void leaveIsland(Island island);

}
