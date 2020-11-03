package pl.subtelny.islands.island.skyblockisland.module;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandIdToIslandTypeService;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.configuration.ConfigurationReloadable;
import pl.subtelny.islands.island.membership.repository.IslandMembershipLoader;
import pl.subtelny.islands.island.module.IslandModule;
import pl.subtelny.islands.island.skyblockisland.configuration.SkyblockIslandConfiguration;
import pl.subtelny.islands.island.skyblockisland.repository.SkyblockIslandRepository;
import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.islands.islandmember.IslandMemberQueryService;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;

import java.util.Objects;
import java.util.Optional;

public class SkyblockIslandModule implements IslandModule<SkyblockIsland> {

    private final IslandType islandType;

    private final SkyblockIslandRepository repository;

    private final ConfigurationReloadable<SkyblockIslandConfiguration> configuration;

    private final IslandIdToIslandTypeService islandIdToIslandTypeService;

    public SkyblockIslandModule(ConnectionProvider connectionProvider,
                                IslandType islandType,
                                ConfigurationReloadable<SkyblockIslandConfiguration> configuration,
                                IslandIdToIslandTypeService islandIdToIslandTypeService,
                                IslandMemberQueryService islandMemberQueryService,
                                IslandMembershipLoader islandMembershipLoader) {
        this.islandType = islandType;
        this.configuration = configuration;
        this.islandIdToIslandTypeService = islandIdToIslandTypeService;
        this.repository = new SkyblockIslandRepository(connectionProvider, configuration, islandMemberQueryService, islandMembershipLoader);
    }

    @Override
    public Optional<SkyblockIsland> findIsland(IslandId islandId) {
        Optional<SkyblockIsland> island = repository.findIsland(islandId, islandType);
        island.ifPresent(this::updateIslandCache);
        return island;
    }

    @Override
    public Optional<SkyblockIsland> findIsland(Location location) {
        IslandCoordinates islandCoordinates = configuration.get().toIslandCoords(location);
        Optional<SkyblockIsland> island = repository.findIsland(islandCoordinates, islandType);
        island.ifPresent(this::updateIslandCache);
        return island;
    }

    @Override
    public IslandType getType() {
        return islandType;
    }

    @Override
    public World getWorld() {
        return Bukkit.getWorld(configuration.get().getWorldName());
    }

    @Override
    public SkyblockIsland createIsland() {
        return null;
    }

    @Override
    public void saveIsland(SkyblockIsland island) {
        repository.saveIsland(island);
    }

    @Override
    public void removeIsland(SkyblockIsland island) {
        repository.removeIsland(island);
    }

    @Override
    public void reloadConfiguration() {
        configuration.reloadConfiguration();
    }

    private void updateIslandCache(SkyblockIsland island) {
        islandIdToIslandTypeService.update(island);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkyblockIslandModule that = (SkyblockIslandModule) o;
        return Objects.equals(islandType, that.islandType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(islandType);
    }
}
