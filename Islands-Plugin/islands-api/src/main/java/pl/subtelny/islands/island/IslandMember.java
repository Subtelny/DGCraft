package pl.subtelny.islands.island;

import java.util.List;
import java.util.Optional;

public interface IslandMember {

    IslandMemberId getIslandMemberId();

    List<IslandId> getIslands();

    List<IslandId> getIslands(IslandType islandType);

    String getName();

    boolean hasIsland(IslandType islandType);

    void addIsland(Island island);

    void leaveIsland(Island island);

}
