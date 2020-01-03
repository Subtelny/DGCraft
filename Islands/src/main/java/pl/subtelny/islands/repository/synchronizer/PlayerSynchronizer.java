package pl.subtelny.islands.repository.synchronizer;

import org.bukkit.entity.Player;
import org.jooq.Configuration;
import pl.subtelny.beans.Component;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.database.DatabaseConfiguration;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.repository.IslandRepository;
import pl.subtelny.islands.repository.IslanderRepository;
import pl.subtelny.islands.repository.loader.island.IslandIdLoader;
import pl.subtelny.islands.repository.loader.island.IslandIdLoaderRequest;
import pl.subtelny.islands.repository.loader.island.IslandIdLoaderResult;

@Component
public class PlayerSynchronizer {

	private final Configuration configuration;

	private final IslandRepository islandRepository;

	private final IslanderRepository islanderRepository;

	public PlayerSynchronizer(DatabaseConfiguration databaseConfiguration,
			IslandRepository islandRepository,
			IslanderRepository islanderRepository) {
		this.islandRepository = islandRepository;
		this.islanderRepository = islanderRepository;
		this.configuration = databaseConfiguration.getConfiguration();
	}

	public void synchronizeData(Player player) {
		AccountId accountId = AccountId.of(player.getUniqueId());

	}

	private IslandId findIsland(AccountId accountId) {
		IslandIdLoaderRequest requestOwner = IslandIdLoaderRequest.newSkyblockBuilder().where(accountId.getId()).build();
		IslandIdLoaderResult performOwner = new IslandIdLoader(configuration, requestOwner).perform();

		if (performOwner.getIslandIds().size() > 0) {
			return performOwner.getIslandIds().get(0);
		}

		IslandIdLoaderRequest requestMember = IslandIdLoaderRequest.newIslandMemberBuilder().where(accountId).build();
		IslandIdLoaderResult performMember = new IslandIdLoader(configuration, requestMember).perform();

		if (performMember.getIslandIds().size() > 0) {
			return performMember.getIslandIds().get(0);
		}
		return null;
	}

}
