package pl.subtelny.islands.repository.synchronizer;

import org.jooq.Configuration;
import pl.subtelny.beans.Component;
import pl.subtelny.database.DatabaseConfiguration;
import pl.subtelny.islands.model.Island;
import pl.subtelny.islands.model.IslandMember;
import pl.subtelny.islands.model.IslandMemberType;
import pl.subtelny.islands.model.IslandType;
import pl.subtelny.islands.model.Islander;
import pl.subtelny.islands.model.island.SkyblockIsland;
import pl.subtelny.islands.repository.IslanderRepository;
import pl.subtelny.islands.repository.SkyblockIslandRepository;

@Component
public class IslandSynchronizer {

	private final SkyblockIslandSynchronizer skyblockIslandSynchronizer;

	private final Configuration configuration;

	private final SkyblockIslandRepository skyblockIslandRepository;

	private final IslanderRepository islanderRepository;

	public IslandSynchronizer(DatabaseConfiguration databaseConfiguration,
			SkyblockIslandRepository skyblockIslandRepository,
			IslanderRepository islanderRepository) {
		this.skyblockIslandRepository = skyblockIslandRepository;
		this.islanderRepository = islanderRepository;
		this.configuration = databaseConfiguration.getConfiguration();

		this.skyblockIslandSynchronizer = buildSkyblockIslandSynchronizer();
	}

	public void synchronizeIslandMember(IslandMember islandMember) {
		if (islandMember.getIslandMemberType() == IslandMemberType.ISLANDER) {
			skyblockIslandSynchronizer.synchronizeIslander((Islander) islandMember);
		}
	}

	public void synchronizeIsland(Island island) {
		if (island.getIslandType() == IslandType.SKYBLOCK) {
			skyblockIslandSynchronizer.synchronizeIsland((SkyblockIsland) island);
		}
	}

	private SkyblockIslandSynchronizer buildSkyblockIslandSynchronizer() {
		return new SkyblockIslandSynchronizer(
				configuration,
				skyblockIslandStorage, islanderStorage);
	}

}
