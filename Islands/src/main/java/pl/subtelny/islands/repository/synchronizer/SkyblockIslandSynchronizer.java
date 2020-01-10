package pl.subtelny.islands.repository.synchronizer;

import org.jooq.Configuration;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.islands.model.Island;
import pl.subtelny.islands.model.IslandMember;
import pl.subtelny.islands.model.IslandMemberType;
import pl.subtelny.islands.model.Islander;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.model.island.SkyblockIsland;
import pl.subtelny.islands.repository.loader.island.member.IslandMemberAnemia;
import pl.subtelny.islands.repository.loader.island.member.IslandMemberAnemiaLoader;
import pl.subtelny.islands.repository.loader.island.member.IslandMemberAnemiaLoaderRequest;
import pl.subtelny.islands.repository.storage.IslanderStorage;
import pl.subtelny.repository.LoaderResult;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class SkyblockIslandSynchronizer {

    private final Configuration configuration;

    private final IslanderStorage islanderStorage;

    public SkyblockIslandSynchronizer(Configuration configuration,
                                      IslanderStorage islanderStorage) {
        this.configuration = configuration;
        this.islanderStorage = islanderStorage;
    }

    public synchronized void synchronizeIsland(SkyblockIsland island) {
        if (island.isFullyLoaded()) {
            return;
        }
        List<IslandMemberAnemia> islandMembersData = loadIslandMembersAnemia(island);
        List<AccountId> accountIds = islandMembersData.stream()
                .filter(i -> i.getIslandMemberType() == IslandMemberType.ISLANDER)
                .map(i -> AccountId.of(UUID.fromString(i.getId())))
                .collect(Collectors.toList());

        AccountId owner = island.getIslandAnemia().getOwner();
        accountIds.add(owner);

        Map<AccountId, Optional<Islander>> loadedIslanders = islanderStorage.getCache(accountIds);
        loadedIslanders.values().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(islander -> !island.isInIsland(islander))
                .filter(i -> !i.getAccount().getAccountId().equals(owner))
                .forEach(island::addMember);

        loadedIslanders.get(owner).ifPresent(island::changeOwner);
        island.setFullyLoaded(true);
    }

    private List<IslandMemberAnemia> loadIslandMembersAnemia(Island island) {
        IslandId islandId = island.getIslandId();
        IslandMemberAnemiaLoaderRequest request = IslandMemberAnemiaLoaderRequest.newBuilder()
                .where(islandId)
                .build();
        LoaderResult<IslandMemberAnemia> perform = new IslandMemberAnemiaLoader(configuration, request).perform();
        return perform.getLoadedData();
    }

}
