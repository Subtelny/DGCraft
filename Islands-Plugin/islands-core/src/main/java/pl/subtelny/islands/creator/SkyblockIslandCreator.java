package pl.subtelny.islands.creator;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.message.IslandMessages;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.model.islander.Islander;
import pl.subtelny.islands.skyblockisland.repository.SkyblockIslandRepository;
import pl.subtelny.islands.schematic.SchematicLoader;
import pl.subtelny.jobs.JobsProvider;
import pl.subtelny.utilities.exception.ValidationException;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class SkyblockIslandCreator {

    private final SkyblockIslandRepository repository;

    private final SchematicLoader schematicLoader;

    private final IslandMessages islandMessages;

    @Autowired
    public SkyblockIslandCreator(SkyblockIslandRepository repository, SchematicLoader schematicLoader,
                                 IslandMessages islandMessages) {
        this.repository = repository;
        this.schematicLoader = schematicLoader;
        this.islandMessages = islandMessages;
    }

    public CompletableFuture<SkyblockIsland> createIsland(Islander islander, @Nullable String schematic) {
        validateIslander(islander);
        IslandCoordinates nextIslandCoordinates = getNextIslandCoordinates();

        SkyblockIsland island = new SkyblockIsland();
        return JobsProvider.supplyAsync(() -> {
            return null;
        });
    }

    private IslandCoordinates getNextIslandCoordinates() {
        return repository.nextFreeIslandCoordinates()
                .orElseThrow(() -> ValidationException.of(
                        islandMessages.getRawMessage("islands.creator.next_island_coordinates_is_empty"))
                );
    }

    private void validateIslander(Islander islander) {
        Optional<IslandId> skyblockIsland = islander.getSkyblockIsland();
        if (skyblockIsland.isPresent()) {
            String rawMessage = islandMessages.getRawMessage("islands.creator.islander_already_has_skyblock_island");
            throw ValidationException.of(rawMessage);
        }
    }

}


