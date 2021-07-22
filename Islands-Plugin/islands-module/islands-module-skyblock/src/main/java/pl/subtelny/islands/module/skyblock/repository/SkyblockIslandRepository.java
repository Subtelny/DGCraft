package pl.subtelny.islands.module.skyblock.repository;

import org.bukkit.Location;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.core.api.repository.HeavyRepository;
import pl.subtelny.generated.tables.tables.IslandConfigurations;
import pl.subtelny.generated.tables.tables.Islands;
import pl.subtelny.generated.tables.tables.SkyblockIslands;
import pl.subtelny.generated.tables.tables.records.IslandsRecord;
import pl.subtelny.islands.api.IslandId;
import pl.subtelny.islands.api.IslandType;
import pl.subtelny.islands.api.membership.model.IslandMembership;
import pl.subtelny.islands.api.membership.repository.IslandMembershipRepository;
import pl.subtelny.islands.api.repository.IslandConfigurationRepository;
import pl.subtelny.islands.api.repository.anemia.IslandAnemia;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.module.skyblock.IslandCoordinates;
import pl.subtelny.islands.module.skyblock.IslandExtendCalculator;
import pl.subtelny.islands.module.skyblock.model.SkyblockIsland;
import pl.subtelny.islands.module.skyblock.repository.anemia.SkyblockIslandAnemia;
import pl.subtelny.islands.module.skyblock.repository.load.SkyblockIslandLoadRequest;
import pl.subtelny.islands.module.skyblock.repository.load.SkyblockIslandLoader;
import pl.subtelny.utilities.NullObject;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class SkyblockIslandRepository extends HeavyRepository {

    private final IslandType islandType;

    private final SkyblockIslandLoader islandLoader;

    private final SkyblockIslandStorage islandStorage;

    private final IslandMembershipRepository membershipRepository;

    private final IslandConfigurationRepository configurationRepository;

    public SkyblockIslandRepository(IslandType islandType,
                                    ConnectionProvider connectionProvider,
                                    IslandExtendCalculator extendCalculator,
                                    IslandMembershipRepository membershipRepository,
                                    IslandConfigurationRepository configurationRepository) {
        super(connectionProvider);
        this.islandType = islandType;
        this.membershipRepository = membershipRepository;
        this.configurationRepository = configurationRepository;
        this.islandStorage = new SkyblockIslandStorage();
        this.islandLoader = new SkyblockIslandLoader(connectionProvider, membershipRepository, configurationRepository, extendCalculator);
    }

    public Optional<SkyblockIsland> findIsland(IslandId islandId) {
        SkyblockIslandLoadRequest request = SkyblockIslandLoadRequest.newBuilder(islandType)
                .where(islandId)
                .build();
        Optional<SkyblockIsland> islandOpt = islandStorage.getCache(islandId, islandId1 -> islandLoader.load(request)).get();
        islandOpt.ifPresent(island -> islandStorage.put(island.getIslandCoordinates(), NullObject.of(islandId)));
        return islandOpt;
    }

    public Optional<SkyblockIsland> findIsland(IslandCoordinates islandCoordinates) {
        SkyblockIslandLoadRequest request = SkyblockIslandLoadRequest.newBuilder(islandType)
                .where(islandCoordinates)
                .build();
        return islandStorage.getCache(islandCoordinates, islandCoordinates1 -> islandLoader.load(request)).get();
    }

    public Collection<SkyblockIsland> getAllLoadedIslands() {
        return islandStorage.getAllCache().values()
                .stream()
                .map(NullObject::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public void removeIsland(SkyblockIsland island) {
        session((context) -> {
            Integer id = island.getId().getId();
            context.deleteFrom(IslandConfigurations.ISLAND_CONFIGURATIONS).where(IslandConfigurations.ISLAND_CONFIGURATIONS.ISLAND_ID.eq(id)).execute();
            context.deleteFrom(SkyblockIslands.SKYBLOCK_ISLANDS).where(SkyblockIslands.SKYBLOCK_ISLANDS.ISLAND_ID.eq(id)).execute();
            context.deleteFrom(Islands.ISLANDS).where(Islands.ISLANDS.ID.eq(id)).execute();

            membershipRepository.removeIslandMemberships(island.getId());
            configurationRepository.removeConfiguration(island.getId());
            islandStorage.invalidate(island);
        });
    }

    public IslandId createIsland(Location spawn, IslandCoordinates coords, Islander owner) {
        IslandAnemia islandAnemia = new IslandAnemia(spawn, islandType);
        SkyblockIslandAnemia anemia = new SkyblockIslandAnemia(islandAnemia, coords);
        return session(context -> {
            IslandsRecord islandsRecord = islandAnemia.toRecord(context);
            islandsRecord.insert();

            IslandId islandId = IslandId.of(islandsRecord.getId(), islandType);
            islandAnemia.setIslandId(islandId);
            anemia.toRecord(context).insert();

            IslandMembership islandMembership = IslandMembership.owner(owner.getIslandMemberId(), islandId);
            membershipRepository.saveIslandMembership(islandMembership);
            return islandId;
        });
    }

    public void saveIsland(SkyblockIsland island) {
        session(context -> {
            SkyblockIslandAnemia anemia = SkyblockIslandAnemia.toAnemia(island);
            anemia.getIslandAnemia().toRecord(context).update();
            anemia.toRecord(context).update();
            configurationRepository.saveConfiguration(island);
        });
    }
}
