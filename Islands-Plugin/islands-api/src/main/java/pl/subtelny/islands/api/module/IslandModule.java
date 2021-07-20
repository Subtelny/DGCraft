package pl.subtelny.islands.api.module;

import org.bukkit.Location;
import org.bukkit.World;
import pl.subtelny.islands.api.Island;
import pl.subtelny.islands.api.IslandCreateRequest;
import pl.subtelny.islands.api.IslandId;
import pl.subtelny.islands.api.IslandType;
import pl.subtelny.islands.api.module.component.IslandComponent;

import java.util.Collection;
import java.util.Optional;

public interface IslandModule<T extends Island> {

    Collection<T> getAllLoadedIslands();

    Optional<T> findIsland(IslandId islandId);

    Optional<T> findIsland(Location location);

    <C extends IslandComponent> C getComponent(Class<C> clazz);

    IslandType getIslandType();

    World getWorld();

    Island createIsland(IslandCreateRequest request);

    IslandModuleConfiguration getConfiguration();

    void saveIsland(T island);

    void removeIsland(T island);

    void reloadConfiguration();

    void reloadAll();

}
