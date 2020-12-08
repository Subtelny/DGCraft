package pl.subtelny.islands.island.skyblockisland.creator;

import org.bukkit.Location;
import org.jooq.DSLContext;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.core.api.worldedit.SchematicPasteSession;
import pl.subtelny.islands.island.IslandCoordinates;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMemberId;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.membership.IslandMembershipCommandService;
import pl.subtelny.islands.island.membership.model.IslandMembership;
import pl.subtelny.islands.island.skyblockisland.IslandExtendCalculator;
import pl.subtelny.islands.island.skyblockisland.repository.SkyblockIslandCoordinatesLoadAction;
import pl.subtelny.islands.island.skyblockisland.repository.SkyblockIslandRepository;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.cuboid.Cuboid;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.messages.Messages;

import java.io.File;
import java.io.IOException;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SkyblockIslandCreator {

    private final Deque<IslandCoordinates> freeCoords;

    private final ConnectionProvider connectionProvider;

    private final SkyblockIslandRepository repository;

    private final IslandMembershipCommandService islandMembershipCommandService;

    private final IslandExtendCalculator extendCalculator;

    private final Messages messages;

    public SkyblockIslandCreator(ConnectionProvider connectionProvider,
                                 SkyblockIslandRepository repository,
                                 IslandType islandType,
                                 IslandMembershipCommandService islandMembershipCommandService,
                                 IslandExtendCalculator extendCalculator,
                                 Messages messages) {
        this.connectionProvider = connectionProvider;
        this.repository = repository;
        this.islandMembershipCommandService = islandMembershipCommandService;
        this.extendCalculator = extendCalculator;
        this.messages = messages;
        this.freeCoords = new SimpleSeriesFreeIslandCoordinatesCalculator().generateIslandCoordinates();
        removeTakenCoordsFromQueue(islandType);
    }

    public IslandId createIsland(SkyblockIslandCreateRequest request) {
        IslandCoordinates coords = getFreeCoords();
        try {
            return createIsland(coords, request);
        } catch (ValidationException e) {
            freeCoords.offerFirst(coords);
            throw ValidationException.of("Could not create island, coords returned to poll", e);
        }
    }

    private IslandId createIsland(IslandCoordinates coords, SkyblockIslandCreateRequest request) {
        Cuboid islandCuboid = extendCalculator.calculateFullyCuboid(coords);
        Location center = islandCuboid.getCenter();
        center.setY(request.getSchematicHeight());

        Islander owner = request.getOwner();
        pasteSchematicWaitForComplete(request.getSchematicFile(), center, owner);
        IslandId islandId = repository.createIsland(center, coords);
        addOwnerToIsland(islandId, owner);
        return islandId;
    }

    private void addOwnerToIsland(IslandId islandId, Islander islander) {
        IslandMemberId islandMemberId = islander.getIslandMemberId();
        IslandMembership islandMembership = new IslandMembership(islandMemberId, islandId, true);
        islandMembershipCommandService.saveIslandMembership(islandMembership);
    }

    private void pasteSchematicWaitForComplete(File schematicFile, Location location, Islander islander) {
        Validation.isTrue(schematicFile.exists(), "skyblockIslandCreator.not_found_schematic_file");
        SchematicPasteSession pasteSession = new SchematicPasteSession(schematicFile, location);
        CountDownLatch latch = new CountDownLatch(1);

        pasteSession.setStateListener(operationStatus -> {
            if (islander != null) {
                messages.sendTo(islander.getPlayer(), "skyblockIslandCreator.creating_state_" + operationStatus.name().toLowerCase());
            }
            if (operationStatus.isEnded()) {
                latch.countDown();
            }
        });

        try {
            pasteSession.perform();
            latch.await(20, TimeUnit.SECONDS);
        } catch (IOException | InterruptedException e) {
            pasteSession.cancel();
            throw new ValidationException("skyblockIslandCreator.exception_while_pasting_schematic", e);
        }
    }

    private IslandCoordinates getFreeCoords() {
        IslandCoordinates coords = freeCoords.poll();
        if (coords == null) {
            throw ValidationException.of("skyblockIslandCreator.out_of_free_coords");
        }
        return coords;
    }

    private void removeTakenCoordsFromQueue(IslandType islandType) {
        List<IslandCoordinates> takenCoords = loadTakenCoordinates(islandType);
        takenCoords.forEach(freeCoords::remove);
    }

    private List<IslandCoordinates> loadTakenCoordinates(IslandType islandType) {
        DSLContext currentConnection = connectionProvider.getCurrentConnection();
        SkyblockIslandCoordinatesLoadAction action = new SkyblockIslandCoordinatesLoadAction(islandType, currentConnection);
        return action.performList();
    }

}
