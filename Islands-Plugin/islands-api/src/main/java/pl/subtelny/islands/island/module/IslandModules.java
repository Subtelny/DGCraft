package pl.subtelny.islands.island.module;

import org.bukkit.World;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandType;

import java.util.List;
import java.util.Optional;

public interface IslandModules {

    <T extends Island> Optional<IslandModule<T>> findIslandModule(Class<T> island);

    Optional<IslandModule<Island>> findIslandModule(IslandType islandType);

    Optional<IslandModule<Island>> findIslandModule(World world);

    List<IslandModule<Island>> getIslandModules();
}
