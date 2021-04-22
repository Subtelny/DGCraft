package pl.subtelny.islands.module.skyblock.remover;

import pl.subtelny.islands.module.skyblock.model.SkyblockIsland;
import pl.subtelny.islands.module.skyblock.organizer.SkyblockIslandOrganizer;
import pl.subtelny.islands.module.skyblock.repository.SkyblockIslandRepository;
import pl.subtelny.utilities.job.JobsProvider;

public class SkyblockIslandRemover {

    private final SkyblockIslandRepository repository;

    private final SkyblockIslandOrganizer islandOrganizer;

    public SkyblockIslandRemover(SkyblockIslandRepository repository,
                                 SkyblockIslandOrganizer islandOrganizer) {
        this.repository = repository;
        this.islandOrganizer = islandOrganizer;
    }

    public void removeIsland(SkyblockIsland island) {
        repository.removeIsland(island);
        JobsProvider.runAsync(() -> islandOrganizer.cleanIsland(island.getIslandCoordinates()));
    }

}
