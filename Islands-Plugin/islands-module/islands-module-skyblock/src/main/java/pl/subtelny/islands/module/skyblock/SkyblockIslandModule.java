package pl.subtelny.islands.module.skyblock;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import pl.subtelny.islands.api.IslandId;
import pl.subtelny.islands.api.IslandType;
import pl.subtelny.islands.api.configuration.ReloadableConfiguration;
import pl.subtelny.islands.api.module.IslandModuleConfiguration;
import pl.subtelny.islands.api.module.component.CratesComponent;
import pl.subtelny.islands.api.module.component.IslandComponent;
import pl.subtelny.islands.module.InitiableIslandModule;
import pl.subtelny.islands.module.skyblock.component.SkyblockIslandComponentsBuilder;
import pl.subtelny.islands.module.skyblock.configuration.SkyblockIslandModuleConfiguration;
import pl.subtelny.islands.module.skyblock.model.SkyblockIsland;
import pl.subtelny.islands.module.skyblock.repository.SkyblockIslandRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class SkyblockIslandModule implements InitiableIslandModule<SkyblockIsland> {

    private final IslandType islandType;

    private final SkyblockIslandRepository repository;

    private final ReloadableConfiguration<SkyblockIslandModuleConfiguration> configuration;

    private final Map<Class<? extends IslandComponent>, IslandComponent> islandComponents;

    public SkyblockIslandModule(IslandType islandType,
                                ReloadableConfiguration<SkyblockIslandModuleConfiguration> configuration,
                                SkyblockIslandRepository repository,
                                SkyblockIslandComponentsBuilder skyblockIslandComponentsBuilder) {
        this.islandType = islandType;
        this.configuration = configuration;
        this.repository = repository;
        this.islandComponents = skyblockIslandComponentsBuilder.module(this).build();
    }

    @Override
    public void initialize() {
        reloadCrates();
    }

    @Override
    public Collection<SkyblockIsland> getAllLoadedIslands() {
        return repository.getAllLoadedIslands();
    }

    @Override
    public Optional<SkyblockIsland> findIsland(IslandId islandId) {
        return repository.findIsland(islandId);
    }

    @Override
    public Optional<SkyblockIsland> findIsland(Location location) {
        IslandCoordinates islandCoordinates = configuration.get().toIslandCoords(location);
        return repository.findIsland(islandCoordinates);
    }

    @Override
    public <C extends IslandComponent> C getComponent(Class<C> clazz) {
        return (C) islandComponents.get(clazz);
    }

    @Override
    public IslandType getIslandType() {
        return islandType;
    }

    @Override
    public World getWorld() {
        return Bukkit.getWorld(configuration.get().getWorldName());
    }

    @Override
    public void reloadConfiguration() {
        configuration.reloadConfiguration();
    }

    @Override
    public void reloadAll() {
        reloadConfiguration();
        reloadCrates();
    }

    @Override
    public IslandModuleConfiguration getConfiguration() {
        return configuration.get();
    }

    private void reloadCrates() {
        getComponent(CratesComponent.class).reload();
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
