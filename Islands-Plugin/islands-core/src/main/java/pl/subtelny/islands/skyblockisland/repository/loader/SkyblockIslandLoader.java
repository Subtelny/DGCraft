package pl.subtelny.islands.skyblockisland.repository.loader;

import org.jooq.Configuration;
import org.jooq.impl.DSL;
import pl.subtelny.generated.tables.tables.IslandsMembership;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.model.islander.Islander;
import pl.subtelny.islands.model.islander.IslanderId;
import pl.subtelny.islands.repository.island.loader.IslandLoader;
import pl.subtelny.islands.repository.islander.IslanderRepository;
import pl.subtelny.islands.skyblockisland.extendcuboid.SkyblockIslandExtendCuboidCalculator;
import pl.subtelny.islands.skyblockisland.model.MembershipType;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.skyblockisland.repository.SkyblockIslandId;
import pl.subtelny.islands.skyblockisland.repository.anemia.SkyblockIslandAnemia;
import pl.subtelny.utilities.Pair;
import pl.subtelny.utilities.cuboid.Cuboid;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SkyblockIslandLoader extends IslandLoader<SkyblockIslandAnemia, SkyblockIsland> {

    private final IslanderRepository islanderRepository;

    private final SkyblockIslandExtendCuboidCalculator extendCuboidCalculator;

    public SkyblockIslandLoader(Configuration configuration,
                                IslanderRepository islanderRepository,
                                SkyblockIslandExtendCuboidCalculator extendCuboidCalculator) {
        super(configuration);
        this.islanderRepository = islanderRepository;
        this.extendCuboidCalculator = extendCuboidCalculator;
    }

    public Optional<SkyblockIsland> loadIsland(SkyblockIslandLoadRequest request) {
        SkyblockIslandAnemiaLoadAction loader = new SkyblockIslandAnemiaLoadAction(configuration, request);
        return loadIsland(loader);
    }

    @Override
    protected SkyblockIsland mapAnemiaToDomain(SkyblockIslandAnemia anemia) {
        Map<Islander, MembershipType> islanders = loadIslandMembers(anemia.getIslandId());
        IslandCoordinates islandCoordinates = anemia.getIslandCoordinates();
        int extendLevel = anemia.getExtendLevel();
        int points = anemia.getPoints();
        Cuboid cuboid = extendCuboidCalculator.calculateCuboid(islandCoordinates, extendLevel);
        return new SkyblockIsland(anemia.getIslandId(), cuboid, islanders, islandCoordinates, extendLevel, points);
    }

    protected Map<Islander, MembershipType> loadIslandMembers(SkyblockIslandId islandId) {
        Map<IslanderId, MembershipType> membership = DSL.using(configuration)
                .select(IslandsMembership.ISLANDS_MEMBERSHIP.ISLANDER_ID, IslandsMembership.ISLANDS_MEMBERSHIP.MEMBERSHIP_TYPE)
                .from(IslandsMembership.ISLANDS_MEMBERSHIP)
                .where(IslandsMembership.ISLANDS_MEMBERSHIP.ISLAND_ID.eq(islandId.getId()))
                .fetch(uuidRecord1 -> new Pair<>(IslanderId.of(uuidRecord1.component1()), MembershipType.valueOf(uuidRecord1.component2().name())))
                .stream()
                .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));

        Map<IslanderId, Islander> islanders = membership.keySet().stream()
                .map(islanderRepository::findIslander)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toMap(Islander::getIslanderId, islander -> islander));

        return islanders.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getValue, entry -> membership.get(entry.getKey())));
    }
}
