package pl.subtelny.islands.island.skyblockisland.repository.load;

import org.bukkit.Location;
import org.jooq.DSLContext;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMemberId;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.membership.IslandMembershipQueryService;
import pl.subtelny.islands.island.membership.model.IslandMembership;
import pl.subtelny.islands.island.skyblockisland.IslandExtendCalculator;
import pl.subtelny.islands.island.skyblockisland.repository.anemia.SkyblockIslandAnemia;
import pl.subtelny.islands.island.IslandCoordinates;
import pl.subtelny.islands.island.membership.IslandMemberQueryService;
import pl.subtelny.islands.island.skyblockisland.model.SkyblockIsland;
import pl.subtelny.utilities.NullObject;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SkyblockIslandLoader {

    private final ConnectionProvider connectionProvider;

    private final IslandMemberQueryService islandMemberQueryService;

    private final IslandMembershipQueryService islandMembershipLoader;

    private final IslandExtendCalculator extendCalculator;

    public SkyblockIslandLoader(ConnectionProvider connectionProvider,
                                IslandMemberQueryService islandMemberQueryService,
                                IslandMembershipQueryService islandMembershipLoader,
                                IslandExtendCalculator extendCalculator) {
        this.connectionProvider = connectionProvider;
        this.islandMemberQueryService = islandMemberQueryService;
        this.islandMembershipLoader = islandMembershipLoader;
        this.extendCalculator = extendCalculator;
    }

    public NullObject<SkyblockIsland> load(SkyblockIslandLoadRequest request) {
        Optional<SkyblockIslandAnemia> skyblockIslandAnemia = performAction(request);
        return skyblockIslandAnemia
                .map(this::mapAnemiaToDomain)
                .map(NullObject::of)
                .orElseGet(NullObject::empty);
    }

    private Optional<SkyblockIslandAnemia> performAction(SkyblockIslandLoadRequest request) {
        DSLContext currentConnection = connectionProvider.getCurrentConnection();
        SkyblockIslandAnemiaLoadAction action = new SkyblockIslandAnemiaLoadAction(currentConnection, request);
        SkyblockIslandAnemia anemia = action.perform();
        return Optional.ofNullable(anemia);
    }

    private SkyblockIsland mapAnemiaToDomain(SkyblockIslandAnemia anemia) {
        Location spawn = anemia.getSpawn();
        LocalDateTime creationDate = anemia.getCreatedDate();
        IslandId islandId = anemia.getIslandId();
        IslandType islandType = anemia.getIslandType();
        IslandCoordinates islandCoordinates = anemia.getIslandCoordinates();
        int extendLevel = anemia.getExtendLevel();
        int points = anemia.getPoints();
        Cuboid cuboid = calculateCuboid(islandCoordinates, extendLevel);

        List<IslandMembership> islandMemberships = islandMembershipLoader.loadIslandMemberships(islandId);
        List<IslandMemberId> members = getMembers(islandMemberships);
        IslandMemberId owner = getOwner(islandMemberships);
        return new SkyblockIsland(extendCalculator,
                islandMemberQueryService,
                islandId,
                islandType,
                creationDate,
                cuboid,
                spawn,
                points,
                members,
                owner,
                islandCoordinates,
                extendLevel);
    }

    private Cuboid calculateCuboid(IslandCoordinates islandCoordinates, int extendLevel) {
        if (extendLevel == 0) {
            return extendCalculator.calculateDefaultCuboid(islandCoordinates);
        }
        return extendCalculator.calculateExtendedCuboid(islandCoordinates, extendLevel - 1);
    }

    private IslandMemberId getOwner(List<IslandMembership> islandMemberships) {
        return islandMemberships.stream()
                .filter(IslandMembership::isOwner)
                .map(IslandMembership::getIslandMemberId)
                .findFirst()
                .orElse(null);
    }

    private List<IslandMemberId> getMembers(List<IslandMembership> islandMemberships) {
        return islandMemberships.stream()
                .map(IslandMembership::getIslandMemberId)
                .collect(Collectors.toList());
    }
}
