package pl.subtelny.islands.skyblockisland.repository.loader;

import org.bukkit.Location;
import org.jooq.Configuration;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.islands.island.skyblockisland.repository.SkyblockIslandAnemiaLoadAction;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslanderId;
import pl.subtelny.islands.islandold.repository.loader.IslandLoader;
import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.islander.repository.IslanderRepository;
import pl.subtelny.islands.islandmembership.repository.IslandMembershipRepository;
import pl.subtelny.islands.islandmembership.repository.loader.IslandMembership;
import pl.subtelny.islands.skyblockisland.extendcuboid.SkyblockIslandExtendCuboidCalculator;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.island.skyblockisland.repository.anemia.SkyblockIslandAnemia;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class SkyblockIslandLoader extends IslandLoader<SkyblockIslandAnemia, SkyblockIsland> {

    private final SkyblockIslandExtendCuboidCalculator extendCuboidCalculator;

    private final IslandMembershipRepository islandMembershipRepository;

    private final IslanderRepository islanderRepository;

    public SkyblockIslandLoader(DatabaseConnection databaseConfiguration,
                                SkyblockIslandExtendCuboidCalculator extendCuboidCalculator,
                                TransactionProvider transactionProvider,
                                IslandMembershipRepository islandMembershipRepository,
                                IslanderRepository islanderRepository) {
        super(databaseConfiguration, transactionProvider);
        this.extendCuboidCalculator = extendCuboidCalculator;
        this.islandMembershipRepository = islandMembershipRepository;
        this.islanderRepository = islanderRepository;
    }

    public Optional<SkyblockIsland> loadIsland(IslandId skyblockIslandId) {
        SkyblockIslandLoadRequest request = SkyblockIslandLoadRequest.newBuilder()
                .where(skyblockIslandId)
                .build();

        Configuration configuration = getConfiguration();
        SkyblockIslandAnemiaLoadAction loader = new SkyblockIslandAnemiaLoadAction(configuration, request);
        return loadIsland(loader);
    }

    @Override
    protected SkyblockIsland mapAnemiaToDomain(SkyblockIslandAnemia anemia) {
        List<IslandMembership> islandMembers = getIslandMembers(anemia.getIslandId());
        Islander owner = findOwnerFromList(islandMembers).orElse(null);
        List<Islander> members = findMembersFromList(islandMembers);

        Location spawn = anemia.getSpawn();
        LocalDateTime createdDate = anemia.getCreatedDate();
        IslandId islandId = anemia.getIslandId();
        IslandCoordinates islandCoordinates = anemia.getIslandCoordinates();
        int extendLevel = anemia.getExtendLevel();
        int points = anemia.getPoints();

        SkyblockIsland skyblockIsland = new SkyblockIsland(islandId,
                spawn,
                createdDate,
                calculateCuboid(islandCoordinates, extendLevel),
                islandCoordinates,
                extendLevel,
                points,
                owner,
                members);

        members.forEach(islander -> islander.addIsland(skyblockIsland));
        return skyblockIsland;
    }

    private Optional<Islander> findOwnerFromList(List<IslandMembership> islandMembers) {
        return islandMembers.stream()
                .filter(IslandMembership::isOwner)
                .map(this::mapIntoIslanderId)
                .flatMap(islanderId -> islanderRepository.findIslander(islanderId).stream())
                .findFirst();
    }

    private List<Islander> findMembersFromList(List<IslandMembership> islandMembers) {
        return islandMembers.stream()
                .map(this::mapIntoIslanderId)
                .flatMap(islanderId -> islanderRepository.findIslander(islanderId).stream())
                .collect(Collectors.toList());
    }

    private IslanderId mapIntoIslanderId(IslandMembership islandMembership) {
        String value = islandMembership.getIslandMemberId().getValue();
        UUID uuid = UUID.fromString(value);
        return IslanderId.of(uuid);
    }

    private List<IslandMembership> getIslandMembers(IslandId islandId) {
        return islandMembershipRepository.loadIslandMemberships(islandId);
    }

    private Cuboid calculateCuboid(IslandCoordinates islandCoordinates, int extendLevel) {
        return extendCuboidCalculator.calculateCuboid(islandCoordinates, extendLevel);
    }
}
