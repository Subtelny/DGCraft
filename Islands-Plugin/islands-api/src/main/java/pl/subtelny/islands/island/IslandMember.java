package pl.subtelny.islands.island;

import java.util.List;
import java.util.Optional;

public interface IslandMember {

    IslandMemberId getId();

    void addIsland(Island island);

    List<Island> getIslands();

    Optional<Island> findIsland(IslandType islandType);

}
