package pl.subtelny.islands.island.module;

import org.bukkit.Location;
import org.bukkit.World;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandType;

import java.util.Optional;

public interface IslandModule<T extends Island> {

    Optional<T> findIsland(IslandId islandId);

    Optional<T> findIsland(Location location);

    IslandType getType();

    World getWorld();

    Island createIsland();

    void saveIsland(T island);

    void removeIsland(T island);

    void reloadConfiguration();
}
