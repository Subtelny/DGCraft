package pl.subtelny.islands.module.skyblock.repository.load;

import org.bukkit.Location;
import org.jooq.DSLContext;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.islands.api.IslandConfiguration;
import pl.subtelny.islands.api.IslandId;
import pl.subtelny.islands.api.IslandMemberId;
import pl.subtelny.islands.api.IslandType;
import pl.subtelny.islands.api.membership.model.IslandMembership;
import pl.subtelny.islands.api.membership.repository.IslandMembershipRepository;
import pl.subtelny.islands.api.repository.IslandConfigurationRepository;
import pl.subtelny.islands.api.repository.anemia.IslandAnemia;
import pl.subtelny.islands.module.skyblock.IslandExtendCalculator;
import pl.subtelny.islands.module.skyblock.repository.anemia.SkyblockIslandAnemia;
import pl.subtelny.islands.module.skyblock.IslandCoordinates;
import pl.subtelny.islands.module.skyblock.model.SkyblockIsland;
import pl.subtelny.utilities.NullObject;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SkyblockIslandLoader {

    private final ConnectionProvider connectionProvider;

    private final IslandMembershipRepository membershipRepository;

    private final IslandConfigurationRepository islandConfigurationRepository;

    private final IslandExtendCalculator extendCalculator;

    public SkyblockIslandLoader(ConnectionProvider connectionProvider,
                                IslandMembershipRepository membershipRepository,
                                IslandConfigurationRepository islandConfigurationRepository,
                                IslandExtendCalculator extendCalculator) {
        this.connectionProvider = connectionProvider;
        this.membershipRepository = membershipRepository;
        this.islandConfigurationRepository = islandConfigurationRepository;
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
        IslandAnemia islandAnemia = anemia.getIslandAnemia();
        Location spawn = islandAnemia.getSpawn();
        LocalDateTime creationDate = islandAnemia.getCreatedDate();
        IslandId islandId = islandAnemia.getIslandId();
        IslandType islandType = islandAnemia.getIslandType();
        IslandCoordinates islandCoordinates = anemia.getIslandCoordinates();
        int extendLevel = anemia.getExtendLevel();
        int points = islandAnemia.getPoints();
        Cuboid cuboid = calculateCuboid(islandCoordinates, extendLevel);

        IslandConfiguration configuration = islandConfigurationRepository.loadConfiguration(islandId);
        List<IslandMembership> islandMemberships = membershipRepository.loadIslandMemberships(islandId);
        List<IslandMemberId> members = getMembers(islandMemberships);
        IslandMemberId owner = getOwner(islandMemberships);
        return new SkyblockIsland(extendCalculator,
                islandId,
                islandType,
                creationDate,
                cuboid,
                spawn,
                points,
                members,
                owner,
                islandCoordinates,
                extendLevel,
                configuration);
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
