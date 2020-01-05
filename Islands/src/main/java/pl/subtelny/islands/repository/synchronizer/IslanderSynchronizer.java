package pl.subtelny.islands.repository.synchronizer;

import org.jooq.Configuration;
import pl.subtelny.islands.model.Islander;
import pl.subtelny.islands.model.island.SkyblockIsland;
import pl.subtelny.islands.repository.storage.SkyblockIslandStorage;

import java.util.Optional;

public class IslanderSynchronizer {

    private final Configuration configuration;

    private final SkyblockIslandStorage skyblockIslandStorage;

    public IslanderSynchronizer(Configuration configuration,
                                SkyblockIslandStorage skyblockIslandStorage) {
        this.configuration = configuration;
        this.skyblockIslandStorage = skyblockIslandStorage;
    }

    public synchronized void synchronizeIslander(Islander islander) {
        if (islander.isFullyLoaded()) {
            return;
        }

        Optional<SkyblockIsland> islandOpt = skyblockIslandStorage.findSkyblockIslandByIslander(islander);
        if (islandOpt.isPresent()) {
            SkyblockIsland island = islandOpt.get();
            if (!island.isInIsland(islander)) {
                island.addMember(islander);
            }
        }
    }

    private void recalculateIslanderIsland(Islander islander, SkyblockIsland foundIslanderIsland) {
        Optional<SkyblockIsland> islandOpt = islander.getIsland();
        if (islandOpt.isEmpty()) {
            foundIslanderIsland.addMember(islander);
            return;
        }
        SkyblockIsland island = islandOpt.get();
        if (!island.equals(foundIslanderIsland)) {
            island.removeMember(islander);
            foundIslanderIsland.addMember(islander);
        }
    }
}
