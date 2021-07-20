package pl.subtelny.islands.module.skyblock.organizer;

import org.jooq.DSLContext;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.islands.module.skyblock.IslandCoordinates;
import pl.subtelny.islands.api.IslandType;
import pl.subtelny.islands.module.skyblock.repository.SkyblockIslandCoordinatesLoadAction;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class SkyblockIslandCoordinates {

    private final IslandType islandType;

    private final Deque<IslandCoordinates> freeCoords;

    SkyblockIslandCoordinates(IslandType islandType) {
        this.islandType = islandType;
        this.freeCoords = new ConcurrentLinkedDeque<>();
    }

    void initialize(ConnectionProvider connectionProvider) {
        Validation.isTrue(freeCoords.isEmpty(), "skyblockIslandCoordinates.already_initialized");
        List<IslandCoordinates> takenCoordinates = loadTakenCoordinates(connectionProvider);

        SimpleSeriesFreeIslandCoordinatesCalculator calculator = new SimpleSeriesFreeIslandCoordinatesCalculator();
        Deque<IslandCoordinates> islandCoordinates = calculator.generateIslandCoordinates();
        islandCoordinates.removeAll(takenCoordinates);
        freeCoords.addAll(islandCoordinates);
    }

    public IslandCoordinates getFreeCoords() {
        IslandCoordinates coords = freeCoords.poll();
        if (coords == null) {
            throw ValidationException.of("skyblockIslandCreator.out_of_free_coords");
        }
        return coords;
    }

    public void addFreeCoords(IslandCoordinates islandCoordinates) {
        freeCoords.offerFirst(islandCoordinates);
    }

    private List<IslandCoordinates> loadTakenCoordinates(ConnectionProvider connectionProvider) {
        DSLContext currentConnection = connectionProvider.getCurrentConnection();
        SkyblockIslandCoordinatesLoadAction action = new SkyblockIslandCoordinatesLoadAction(islandType, currentConnection);
        return action.performList();
    }

}
