package pl.subtelny.islands.island.skyblockisland.module;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import pl.subtelny.crate.api.command.CrateCommandService;
import pl.subtelny.islands.island.IslandCoordinates;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMember;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.configuration.ConfigurationReloadable;
import pl.subtelny.islands.island.cqrs.command.IslandCreateRequest;
import pl.subtelny.islands.island.crate.IslandCrateQueryService;
import pl.subtelny.islands.island.crate.IslandCrates;
import pl.subtelny.islands.island.module.IslandModuleInitable;
import pl.subtelny.islands.island.skyblockisland.configuration.SkyblockIslandConfiguration;
import pl.subtelny.islands.island.skyblockisland.crate.SkyblockIslandCrates;
import pl.subtelny.islands.island.skyblockisland.crate.SkyblockIslandCratesLoader;
import pl.subtelny.islands.island.skyblockisland.creator.SkyblockIslandCreator;
import pl.subtelny.islands.island.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.island.skyblockisland.remover.SkyblockIslandRemover;
import pl.subtelny.islands.island.skyblockisland.repository.SkyblockIslandRepository;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public class SkyblockIslandModule implements IslandModuleInitable<SkyblockIsland> {

    private final IslandType islandType;

    private final SkyblockIslandCrates islandCrates;

    private final SkyblockIslandRepository repository;

    private final SkyblockIslandCreator islandCreator;

    private final SkyblockIslandRemover islandRemover;

    private final ConfigurationReloadable<SkyblockIslandConfiguration> configuration;

    public SkyblockIslandModule(IslandType islandType,
                                ConfigurationReloadable<SkyblockIslandConfiguration> configuration,
                                SkyblockIslandCratesLoader islandCrates,
                                SkyblockIslandRepository repository,
                                SkyblockIslandCreator islandCreator,
                                SkyblockIslandRemover islandRemover,
                                CrateCommandService crateCommandService,
                                IslandCrateQueryService islandCrateQueryService) {
        this.islandType = islandType;
        this.configuration = configuration;
        this.islandRemover = islandRemover;
        this.repository = repository;
        this.islandCreator = islandCreator;
        this.islandCrates = new SkyblockIslandCrates(crateCommandService, islandCrateQueryService, islandCrates, this);
    }

    @Override
    public void initialize() {
        islandCrates.reloadCrates();
    }

    @Override
    public Collection<SkyblockIsland> getAllLoadedIslands() {
        return repository.getAllLoadedIslands();
    }

    @Override
    public Optional<SkyblockIsland> findIsland(IslandId islandId) {
        return repository.findIsland(islandId, islandType);
    }

    @Override
    public Optional<SkyblockIsland> findIsland(Location location) {
        IslandCoordinates islandCoordinates = configuration.get().toIslandCoords(location);
        return repository.findIsland(islandCoordinates, islandType);
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
    public SkyblockIsland createIsland(IslandCreateRequest request) {
        if (!configuration.get().isCreateEnabled()) {
            throw ValidationException.of("skyblockIslandModule.create_island_disabled");
        }

        IslandMember owner = request.getOwner()
                .orElseThrow(() -> ValidationException.of("skyblockIslandModule.create_island_null_owner"));

        if (!owner.getIslandMemberId().getType().equals(Islander.ISLAND_MEMBER_TYPE)) {
            throw ValidationException.of("skyblockIslandModule.create_island_owner_only_islander");
        }

        IslandId islandId = islandCreator.createIsland((Islander) owner);
        return findIsland(islandId)
                .orElseThrow(() -> ValidationException.of("skyblockIslandModule.not_found_island_after_create"));
    }

    @Override
    public IslandCrates getIslandCrates() {
        return islandCrates;
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
