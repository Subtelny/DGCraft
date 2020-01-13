package pl.subtelny.islands.repository.island.synchronizer;

import org.jooq.Configuration;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.islands.model.Island;
import pl.subtelny.islands.model.IslandMemberType;
import pl.subtelny.islands.model.Islander;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.model.island.SkyblockIsland;
import pl.subtelny.islands.repository.island.IslandMemberAnemia;
import pl.subtelny.islands.repository.island.loader.IslandMemberAnemiaLoader;
import pl.subtelny.islands.repository.island.loader.IslandMemberAnemiaLoaderRequest;
import pl.subtelny.islands.repository.island.storage.IslanderStorage;
import pl.subtelny.repository.LoaderResult;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class SkyblockIslandSynchronizer extends Synchronizer<SkyblockIsland> {

	private final Configuration configuration;

	private final IslanderStorage islanderStorage;

	public SkyblockIslandSynchronizer(Configuration configuration,
			IslanderStorage islanderStorage) {
		this.configuration = configuration;
		this.islanderStorage = islanderStorage;
	}

	public void synchronizeIsland(SkyblockIsland island) {
		synchronizeIsland(island, false);
	}

	public void synchronizeIsland(SkyblockIsland island, boolean force) {
		if (preventLock(island, force)) {
			return;
		}
		lock(island);
		try {
			List<IslandMemberAnemia> islandMembersData = loadIslandMembersAnemia(island);
			List<AccountId> accountIds = islandMembersData.stream()
					.filter(i -> i.getIslandMemberType() == IslandMemberType.ISLANDER)
					.map(i -> AccountId.of(UUID.fromString(i.getId())))
					.collect(Collectors.toList());

			AccountId owner = island.getIslandAnemia().getOwner();
			accountIds.add(owner);

			Map<AccountId, Optional<Islander>> loadedIslanders = null; //TODO //islanderStorage.getAllCache(accountIds);
			loadedIslanders.values().stream()
					.filter(Optional::isPresent)
					.map(Optional::get)
					.filter(islander -> !island.isInIsland(islander))
					.filter(i -> !i.getAccount().getAccountId().equals(owner))
					.forEach(island::addMember);

			loadedIslanders.get(owner).ifPresent(island::changeOwner);
			island.setFullyLoaded(true);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			unlock(island);
		}
	}

	private boolean preventLock(SkyblockIsland island, boolean force) {
		if (force) {
			return false;
		}
		return island.isFullyLoaded();
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
