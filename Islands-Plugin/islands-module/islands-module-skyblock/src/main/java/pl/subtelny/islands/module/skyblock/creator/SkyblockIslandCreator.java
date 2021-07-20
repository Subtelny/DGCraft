package pl.subtelny.islands.module.skyblock.creator;

import pl.subtelny.core.api.worldedit.OperationStatus;
import pl.subtelny.islands.api.IslandId;
import pl.subtelny.islands.api.message.IslandMessages;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.module.skyblock.organizer.GenerateResult;
import pl.subtelny.islands.module.skyblock.organizer.SkyblockIslandOrganizer;
import pl.subtelny.islands.module.skyblock.repository.SkyblockIslandRepository;
import pl.subtelny.utilities.Callback;
import pl.subtelny.utilities.exception.ValidationException;

public class SkyblockIslandCreator {

    private final SkyblockIslandOrganizer islandOrganizer;

    private final SkyblockIslandRepository repository;

    public SkyblockIslandCreator(SkyblockIslandRepository repository,
                                 SkyblockIslandOrganizer islandOrganizer) {
        this.repository = repository;
        this.islandOrganizer = islandOrganizer;
    }

    public IslandId createIsland(Islander owner) {
        try {
            GenerateResult result = islandOrganizer.generateIsland(getStatusCallback(owner));
            return repository.createIsland(result.getSpawn(), result.getCoordinates(), owner);
        } catch (ValidationException e) {
            throw ValidationException.of("Could not create island, coords returned to poll", e);
        }
    }

    private Callback<OperationStatus> getStatusCallback(Islander islander) {
        return operationStatus -> IslandMessages.get().sendTo(
                islander.getPlayer(),
                "skyblockIslandCreator.creating_state_" + operationStatus.name().toLowerCase()
        );
    }

}
