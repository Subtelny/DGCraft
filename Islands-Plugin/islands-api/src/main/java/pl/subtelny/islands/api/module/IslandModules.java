package pl.subtelny.islands.api.module;

import org.bukkit.World;
import pl.subtelny.islands.api.Island;
import pl.subtelny.islands.api.IslandType;

import java.util.List;
import java.util.Optional;

public interface IslandModules {

    <T extends Island> Optional<IslandModule<T>> findIslandModule(Class<T> island);

    Optional<IslandModule<Island>> findIslandModule(IslandType islandType);

    Optional<IslandModule<Island>> findIslandModule(World world);

    List<IslandModule<Island>> getIslandModules();
}
