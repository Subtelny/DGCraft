package pl.subtelny.islands.island.skyblockisland.module;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.islands.island.*;
import pl.subtelny.islands.island.configuration.ConfigurationReloadable;
import pl.subtelny.islands.island.gui.IslandCrates;
import pl.subtelny.islands.island.membership.IslandMemberQueryService;
import pl.subtelny.islands.island.membership.IslandMembershipCommandService;
import pl.subtelny.islands.island.membership.IslandMembershipQueryService;
import pl.subtelny.islands.island.membership.model.IslandMembership;
import pl.subtelny.islands.island.module.IslandModule;
import pl.subtelny.islands.island.skyblockisland.IslandExtendCalculator;
import pl.subtelny.islands.island.skyblockisland.configuration.SkyblockIslandConfiguration;
import pl.subtelny.islands.island.skyblockisland.creator.SkyblockIslandCreateRequest;
import pl.subtelny.islands.island.skyblockisland.creator.SkyblockIslandCreator;
import pl.subtelny.islands.island.skyblockisland.crates.SkyblockIslandCrates;
import pl.subtelny.islands.island.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.island.skyblockisland.repository.SkyblockIslandRepository;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.messages.Messages;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SkyblockIslandModule implements IslandModule<SkyblockIsland> {

    private final IslandType islandType;

    private final SkyblockIslandCrates islandCrates;

    private final SkyblockIslandRepository repository;

    private final SkyblockIslandCreator islandCreator;

    private final ConfigurationReloadable<SkyblockIslandConfiguration> configuration;

    private final IslandMembershipCommandService islandMembershipCommandService;

    public SkyblockIslandModule(ConnectionProvider connectionProvider,
                                IslandType islandType,
                                ConfigurationReloadable<SkyblockIslandConfiguration> configuration,
                                SkyblockIslandCrates islandCrates,
                                IslandMemberQueryService islandMemberQueryService,
                                IslandMembershipQueryService islandMembershipLoader,
                                IslandMembershipCommandService islandMembershipCommandService,
                                Messages messages) {
        this.islandType = islandType;
        this.configuration = configuration;
        this.islandCrates = islandCrates;
        this.islandMembershipCommandService = islandMembershipCommandService;
        IslandExtendCalculator extendCalculator = new IslandExtendCalculator(configuration);
        this.repository = new SkyblockIslandRepository(islandType, connectionProvider, islandMemberQueryService, islandMembershipLoader, extendCalculator);
        this.islandCreator = new SkyblockIslandCreator(connectionProvider, repository, islandType, islandMembershipCommandService, extendCalculator, messages);
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

        SkyblockIslandCreateRequest skyblockRequest = toSkyblockCreateRequest(request);
        IslandId islandId = islandCreator.createIsland(skyblockRequest);
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
        saveIslandMembers(island);
    }

    @Override
    public void removeIsland(SkyblockIsland island) {
        repository.removeIsland(island);
    }

    @Override
    public void reloadConfiguration() {
        configuration.reloadConfiguration();
    }

    private SkyblockIslandCreateRequest toSkyblockCreateRequest(IslandCreateRequest request) {
        IslandMember owner = request.getOwner()
                .orElseThrow(() -> ValidationException.of("skyblockIslandModule.create_island_null_owner"));

        if (!owner.getIslandMemberId().getType().equals(Islander.ISLAND_MEMBER_TYPE)) {
            throw ValidationException.of("skyblockIslandModule.create_island_owner_only_islander");
        }

        File schematicFile = configuration.get().getSchematicFile();
        Islander islander = (Islander) owner;
        return new SkyblockIslandCreateRequest(islander, schematicFile);
    }

    private void saveIslandMembers(SkyblockIsland island) {
        List<IslandMemberChangedRequest> requests = island.getIslandMembersChangesRequests();
        requests.forEach(request -> updateIslandMember(island, request));
    }

    private void updateIslandMember(SkyblockIsland island, IslandMemberChangedRequest request) {
        IslandMembership member = new IslandMembership(request.getIslandMember(), island.getId(), request.isOwner());
        switch (request.getType()) {
            case ADDED:
            case UPDATE_OWNER:
                islandMembershipCommandService.saveIslandMembership(member);
                break;
            case REMOVED:
                islandMembershipCommandService.removeIslandMembership(member);
                break;
        }
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
