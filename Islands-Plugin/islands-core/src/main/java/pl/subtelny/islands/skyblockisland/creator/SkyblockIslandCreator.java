package pl.subtelny.islands.skyblockisland.creator;

import org.bukkit.Location;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.core.api.schematic.SchematicLoader;
import pl.subtelny.groups.api.GroupId;
import pl.subtelny.groups.api.GroupMemberId;
import pl.subtelny.groups.api.GroupsContextId;
import pl.subtelny.groups.api.GroupsContextService;
import pl.subtelny.islands.island.IslandGroupsContext;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.creator.IslandCreator;
import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.islandmembership.repository.IslandMembershipRepository;
import pl.subtelny.islands.skyblockisland.extendcuboid.SkyblockIslandExtendCuboidCalculator;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.skyblockisland.repository.SkyblockIslandRepository;
import pl.subtelny.islands.skyblockisland.schematic.SkyblockIslandSchematicOption;
import pl.subtelny.islands.skyblockisland.settings.SkyblockIslandSettings;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.cuboid.Cuboid;
import pl.subtelny.utilities.exception.ValidationException;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Component
public class SkyblockIslandCreator implements IslandCreator<SkyblockIsland, SkyblockIslandCreateRequest> {

    private final TransactionProvider transactionProvider;

    private final SkyblockIslandRepository repository;

    private final IslandMembershipRepository islandMembershipRepository;

    private final SkyblockIslandExtendCuboidCalculator cuboidCalculator;

    private final SkyblockIslandSettings settings;

    private final GroupsContextService groupsContextService;

    @Autowired
    public SkyblockIslandCreator(TransactionProvider transactionProvider,
                                 SkyblockIslandRepository repository,
                                 IslandMembershipRepository islandMembershipRepository,
                                 SkyblockIslandExtendCuboidCalculator cuboidCalculator,
                                 SkyblockIslandSettings settings,
                                 GroupsContextService groupsContextService) {
        this.transactionProvider = transactionProvider;
        this.repository = repository;
        this.islandMembershipRepository = islandMembershipRepository;
        this.cuboidCalculator = cuboidCalculator;
        this.settings = settings;
        this.groupsContextService = groupsContextService;
    }

    @Override
    public CompletableFuture<SkyblockIsland> create(SkyblockIslandCreateRequest request) {
        IslandCoordinates islandCoordinates = request.getCoordinates().orElse(getNextIslandCoordinates());
        SkyblockIslandSchematicOption schematicOption = request.getOption().orElse(settings.getDefaultSchematicOption());
        return createSkyblockIsland(request.getIslander(), islandCoordinates, schematicOption);
    }

    private CompletableFuture<SkyblockIsland> createSkyblockIsland(Islander islander, IslandCoordinates coordinates, SkyblockIslandSchematicOption schematic) {
        validateIslander(islander);
        validateIslandCoordinates(coordinates);
        Cuboid cuboid = cuboidCalculator.calculateCuboid(coordinates);
        return transactionProvider.transactionResultAsync(() -> {
            pasteSchematic(schematic, cuboid.getCenter());
            SkyblockIsland island = createSkyblockIsland(coordinates, cuboid);
            createOwner(islander, island);
            return island;
        }).toCompletableFuture();
    }

    private SkyblockIsland createSkyblockIsland(IslandCoordinates coordinates, Cuboid cuboid) {
        Location spawn = new SkyblockIslandSpawnCalculator(cuboid).calculate();
        return repository.createIsland(coordinates, spawn, cuboid);
    }

    private void createOwner(Islander islander, SkyblockIsland skyblockIsland) {
        IslandId skyblockIslandId = skyblockIsland.getId();
        createIslandMembership(islander, skyblockIslandId);
        createGroup(islander, skyblockIslandId);
    }

    private void createIslandMembership(Islander islander, IslandId skyblockIslandId) {
        islandMembershipRepository.createIslandMembership(islander, skyblockIslandId);
    }

    private void createGroup(Islander islander, IslandId skyblockIslandId) {
        GroupsContextId groupsContextId = GroupsContextId.of(skyblockIslandId.getInternal());
        GroupMemberId groupMemberId = GroupMemberId.of(islander.getId().getInternal());
        GroupId owner = IslandGroupsContext.OWNER;
        groupsContextService.createGroup(groupsContextId, owner);
        groupsContextService.addMember(groupsContextId, owner, groupMemberId);
    }

    private void pasteSchematic(SkyblockIslandSchematicOption schematic, Location center) {
        try {
            CountDownLatch latch = new CountDownLatch(1);
            File schematicFile = new File(schematic.getSchematicFilePath());
            Validation.isTrue(schematicFile.exists(), "creator.skyblockIsland.schema_file_not_found", schematic.getSchematicFilePath());
            SchematicLoader.pasteSchematic(center, schematicFile, aBoolean -> latch.countDown());
            latch.await(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw ValidationException.of("creator.skyblockIsland.error_while_pasting_island");
        }
    }

    private IslandCoordinates getNextIslandCoordinates() {
        return repository.nextFreeIslandCoordinates()
                .orElseThrow(() -> ValidationException.of("creator.skyblockIsland.no_free_island_coordinates"));
    }

    private void validateIslander(Islander islander) {
        Optional<SkyblockIsland> skyblockIsland = islander.getSkyblockIsland();
        Validation.isTrue(skyblockIsland.isEmpty(), "creator.skyblockIsland.already_has_island");
    }

    private void validateIslandCoordinates(IslandCoordinates islandCoordinates) {
        Validation.isTrue(repository.findSkyblockIsland(islandCoordinates).isEmpty(), "creator.skyblockIsland.coordinates_taken");
    }

}


