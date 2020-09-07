package pl.subtelny.islands.skyblockisland.repository.loader;

import org.bukkit.Location;
import org.jooq.Configuration;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.islands.island.repository.loader.IslandLoader;
import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.islands.skyblockisland.extendcuboid.SkyblockIslandExtendCuboidCalculator;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.skyblockisland.repository.SkyblockIslandId;
import pl.subtelny.islands.skyblockisland.repository.anemia.SkyblockIslandAnemia;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.time.LocalDateTime;
import java.util.Optional;

public class SkyblockIslandLoader extends IslandLoader<SkyblockIslandAnemia, SkyblockIsland> {

    private final SkyblockIslandExtendCuboidCalculator extendCuboidCalculator;

    public SkyblockIslandLoader(DatabaseConnection databaseConfiguration,
                                SkyblockIslandExtendCuboidCalculator extendCuboidCalculator,
                                TransactionProvider transactionProvider) {
        super(databaseConfiguration, transactionProvider);
        this.extendCuboidCalculator = extendCuboidCalculator;
    }

    public Optional<SkyblockIsland> loadIsland(SkyblockIslandId skyblockIslandId) {
        SkyblockIslandLoadRequest request = SkyblockIslandLoadRequest.newBuilder()
                .where(skyblockIslandId)
                .build();

        Configuration configuration = getConfiguration();
        SkyblockIslandAnemiaLoadAction loader = new SkyblockIslandAnemiaLoadAction(configuration, request);
        return loadIsland(loader);
    }

    @Override
    protected SkyblockIsland mapAnemiaToDomain(SkyblockIslandAnemia anemia) {
        Location spawn = anemia.getSpawn();
        LocalDateTime createdDate = anemia.getCreatedDate();
        SkyblockIslandId islandId = anemia.getIslandId();
        IslandCoordinates islandCoordinates = anemia.getIslandCoordinates();
        int extendLevel = anemia.getExtendLevel();
        int points = anemia.getPoints();
        return new SkyblockIsland(islandId,
                spawn,
                createdDate,
                calculateCuboid(islandCoordinates, extendLevel),
                islandCoordinates,
                extendLevel,
                points);
    }

    private Cuboid calculateCuboid(IslandCoordinates islandCoordinates, int extendLevel) {
        return extendCuboidCalculator.calculateCuboid(islandCoordinates, extendLevel);
    }
}
