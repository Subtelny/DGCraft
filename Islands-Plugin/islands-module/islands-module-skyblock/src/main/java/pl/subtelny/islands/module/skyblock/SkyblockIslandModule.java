package pl.subtelny.islands.module.skyblock;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.islands.api.IslandCreateRequest;
import pl.subtelny.islands.api.IslandId;
import pl.subtelny.islands.api.IslandMember;
import pl.subtelny.islands.api.IslandType;
import pl.subtelny.islands.api.configuration.ReloadableConfiguration;
import pl.subtelny.islands.api.module.IslandModuleConfiguration;
import pl.subtelny.islands.api.module.component.CratesComponent;
import pl.subtelny.islands.api.module.component.IslandComponent;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.module.InitiableIslandModule;
import pl.subtelny.islands.module.skyblock.component.SkyblockIslandComponentsBuilder;
import pl.subtelny.islands.module.skyblock.configuration.SkyblockIslandModuleConfiguration;
import pl.subtelny.islands.module.skyblock.creator.SkyblockIslandCreator;
import pl.subtelny.islands.module.skyblock.model.SkyblockIsland;
import pl.subtelny.islands.module.skyblock.organizer.SkyblockIslandOrganizer;
import pl.subtelny.islands.module.skyblock.remover.SkyblockIslandRemover;
import pl.subtelny.islands.module.skyblock.repository.SkyblockIslandRepository;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class SkyblockIslandModule implements InitiableIslandModule<SkyblockIsland> {

    private final IslandType islandType;

    private final SkyblockIslandRepository repository;

    private final SkyblockIslandCreator islandCreator;

    private final SkyblockIslandRemover islandRemover;

    private final ReloadableConfiguration<SkyblockIslandModuleConfiguration> configuration;

    private final Map<Class<? extends IslandComponent>, IslandComponent> islandComponents;

    public SkyblockIslandModule(IslandType islandType,
                                ReloadableConfiguration<SkyblockIslandModuleConfiguration> configuration,
                                SkyblockIslandRepository repository,
                                SkyblockIslandOrganizer organizer,
                                SkyblockIslandComponentsBuilder skyblockIslandComponentsBuilder) {
        this.islandType = islandType;
        this.configuration = configuration;
        this.repository = repository;
        this.islandRemover = new SkyblockIslandRemover(repository, organizer);
        this.islandCreator = new SkyblockIslandCreator(repository, organizer);
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
    public SkyblockIsland createIsland(IslandCreateRequest request) {
        if (!configuration.get().isCreateEnabled()) {
            throw ValidationException.of("skyblockIslandModule.create_island_disabled");
        }

        IslandMember owner = request.getOwner()
                .orElseThrow(() -> ValidationException.of("skyblockIslandModule.create_island_null_owner"));

        if (!Islander.TYPE.equals(owner.getIslandMemberId().getType())) {
            throw ValidationException.of("skyblockIslandModule.create_island_owner_only_islander");
        }

        IslandId islandId = islandCreator.createIsland((Islander) owner);
        return findIsland(islandId)
                .orElseThrow(() -> ValidationException.of("skyblockIslandModule.not_found_island_after_create"));
    }

    @Override
    public void saveIsland(SkyblockIsland island) {
        repository.saveIsland(island);
    }

    @Override
    public void removeIsland(SkyblockIsland island) {
        islandRemover.removeIsland(island);
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
