package pl.subtelny.islands.repository.synchronizer;

import org.jooq.Configuration;
import pl.subtelny.beans.Component;
import pl.subtelny.database.DatabaseConfiguration;
import pl.subtelny.islands.model.Island;
import pl.subtelny.islands.model.IslandType;
import pl.subtelny.islands.model.island.SkyblockIsland;
import pl.subtelny.islands.repository.storage.IslanderStorage;
import pl.subtelny.islands.repository.storage.SkyblockIslandStorage;

@Component
public class IslandSynchronizer {

	private final Configuration configuration;

	private final SkyblockIslandSynchronizer skyblockIslandSynchronizer;

	private final SkyblockIslandStorage skyblockIslandStorage;

	private final IslanderStorage islanderStorage;

	public IslandSynchronizer(DatabaseConfiguration databaseConfiguration,
							  SkyblockIslandStorage skyblockIslandStorage,
							  IslanderStorage islanderStorage) {
		this.skyblockIslandStorage = skyblockIslandStorage;
		this.islanderStorage = islanderStorage;
		this.configuration = databaseConfiguration.getConfiguration();
		this.skyblockIslandSynchronizer = buildSkyblockIslandSynchronizer();
	}

	public void synchronizeIsland(Island island) {
		if (island.getIslandType() == IslandType.SKYBLOCK) {
			skyblockIslandSynchronizer.synchronizeIsland((SkyblockIsland) island);
		}
	}

	private SkyblockIslandSynchronizer buildSkyblockIslandSynchronizer() {
		return new SkyblockIslandSynchronizer(
				configuration,
				islanderStorage);
	}

}
