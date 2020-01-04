package pl.subtelny.islands.repository.synchronizer;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.jooq.Configuration;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.islands.model.Island;
import pl.subtelny.islands.model.IslandMemberType;
import pl.subtelny.islands.model.Islander;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.model.island.SkyblockIsland;
import pl.subtelny.islands.repository.loader.island.member.IslandMemberAnemia;
import pl.subtelny.islands.repository.loader.island.member.IslandMemberAnemiaLoader;
import pl.subtelny.islands.repository.loader.island.member.IslandMemberAnemiaLoaderRequest;
import pl.subtelny.islands.repository.loader.island.member.IslandMemberAnemiaLoaderResult;
import pl.subtelny.islands.storage.IslanderStorage;
import pl.subtelny.islands.storage.SkyblockIslandStorage;

public class SkyblockIslandSynchronizer {

	private final Configuration configuration;

	private final SkyblockIslandStorage skyblockIslandStorage;

	private final IslanderStorage islanderStorage;

	public SkyblockIslandSynchronizer(Configuration configuration,
			SkyblockIslandStorage skyblockIslandStorage, IslanderStorage islanderStorage) {
		this.configuration = configuration;
		this.skyblockIslandStorage = skyblockIslandStorage;
		this.islanderStorage = islanderStorage;
	}

	public synchronized void synchronizeIslander(Islander islander) {
		if(islander.isFullyLoaded()) {
			return;
		}
		Optional<SkyblockIsland> skyblockIslandOpt = skyblockIslandStorage.findSkyblockIslandByIslander(islander);
		if (skyblockIslandOpt.isPresent()) {
			SkyblockIsland skyblockIsland = skyblockIslandOpt.get();
			if (!skyblockIsland.isInIsland(islander)) {
				skyblockIsland.addMember(islander);
			}
			synchronizeIsland(skyblockIsland);
		}
		islander.setFullyLoaded(true);
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
				.filter(i -> !i.getAccount().getId().equals(owner))
				.forEach(island::addMember);

		loadedIslanders.get(owner).ifPresent(island::changeOwner);
		island.setFullyLoaded(true);
	}

	private List<IslandMemberAnemia> loadIslandMembersAnemia(Island island) {
		IslandId islandId = island.getIslandId();
		IslandMemberAnemiaLoaderRequest request = IslandMemberAnemiaLoaderRequest.newBuilder()
				.where(islandId)
				.build();
		IslandMemberAnemiaLoaderResult perform = new IslandMemberAnemiaLoader(configuration, request).perform();
		return perform.getIslandMembersAnemia();
	}

}
