package pl.subtelny.islands.skyblockisland.repository.loader;

import org.bukkit.Location;
import org.jooq.Configuration;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.islands.island.repository.loader.IslandLoader;
import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.island.IslanderId;
import pl.subtelny.islands.islander.repository.IslanderRepository;
import pl.subtelny.islands.skyblockisland.extendcuboid.SkyblockIslandExtendCuboidCalculator;
import pl.subtelny.islands.islandmembership.IslandMembershipRepository;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.skyblockisland.repository.SkyblockIslandId;
import pl.subtelny.islands.skyblockisland.repository.anemia.SkyblockIslandAnemia;
import pl.subtelny.utilities.cuboid.Cuboid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SkyblockIslandLoader extends IslandLoader<SkyblockIslandAnemia, SkyblockIsland> {

    private final IslanderRepository islanderRepository;

    private final IslandMembershipRepository islandMembershipRepository;

    private final SkyblockIslandExtendCuboidCalculator extendCuboidCalculator;

    public SkyblockIslandLoader(DatabaseConnection databaseConfiguration,
                                IslanderRepository islanderRepository,
                                IslandMembershipRepository islandMembershipRepository,
                                SkyblockIslandExtendCuboidCalculator extendCuboidCalculator,
                                TransactionProvider transactionProvider) {
        super(databaseConfiguration, transactionProvider);
        this.islandMembershipRepository = islandMembershipRepository;
        this.islanderRepository = islanderRepository;
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
        //Map<Islander, MembershipType> islanders = loadIslandMembers(anemia.getIslandId());
        IslandCoordinates islandCoordinates = anemia.getIslandCoordinates();
        int extendLevel = anemia.getExtendLevel();
        int points = anemia.getPoints();
        Cuboid cuboid = extendCuboidCalculator.calculateCuboid(islandCoordinates, extendLevel);
        LocalDateTime createdDate = anemia.getCreatedDate();
        Location spawn = anemia.getSpawn();
        return null;//new SkyblockIsland(anemia.getIslandId(), spawn, cuboid, createdDate, islanders, islandCoordinates, extendLevel, points);
    }

}
