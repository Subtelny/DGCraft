package pl.subtelny.islands.repository.island;

import java.util.Optional;
import org.jooq.Configuration;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.database.DatabaseConfiguration;
import pl.subtelny.islands.model.island.Island;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.model.island.SkyblockIsland;
import pl.subtelny.islands.repository.island.loader.SkyblockIslandLoadRequest;
import pl.subtelny.islands.repository.island.loader.SkyblockIslandLoader;
import pl.subtelny.islands.repository.island.storage.IslandStorage;
import pl.subtelny.islands.repository.island.storage.SkyblockIslandStorage;

@Component
public class SkyblockIslandRepository {

	private final IslandStorage islandStorage;

	private final SkyblockIslandLoader skyblockIslandLoader;

	private final SkyblockIslandStorage skyblockIslandStorage;

	@Autowired
	public SkyblockIslandRepository(DatabaseConfiguration databaseConfiguration,
			IslandStorage islandStorage) {
		this.islandStorage = islandStorage;
		Configuration configuration = databaseConfiguration.getConfiguration();
		this.skyblockIslandStorage = new SkyblockIslandStorage();
		this.skyblockIslandLoader = new SkyblockIslandLoader(configuration);
	}

	public Optional<SkyblockIsland> findSkyblockIsland(IslandCoordinates islandCoordinates) {
		if (!skyblockIslandStorage.isIslandCoordinatesFree(islandCoordinates)) {
			Optional<IslandId> cache = skyblockIslandStorage.getCache(islandCoordinates, islandCoordinates1 -> {
				SkyblockIslandLoadRequest request = SkyblockIslandLoadRequest.newBuilder()
						.where(islandCoordinates)
						.build();
				return loadSkyblockIsland(request).map(Island::getIslandId);
			});
			if (cache.isPresent()) {
				return findSkyblockIsland(cache.get());
			}
		}
		return Optional.empty();
	}

	public Optional<SkyblockIsland> findSkyblockIsland(IslandId islandId) {
		Optional<Island> islandOpt = islandStorage.getCache(islandId, islandId1 -> {
			SkyblockIslandLoadRequest request = SkyblockIslandLoadRequest.newBuilder()
					.where(islandId)
					.build();
			return skyblockIslandLoader.loadIsland(request).map(island -> island);
		});
		return determineSkyblockIslandFromOptional(islandOpt);
	}

	private Optional<SkyblockIsland> determineSkyblockIslandFromOptional(Optional<Island> islandOpt) {
		if (islandOpt.isPresent()) {
			Island island = islandOpt.get();
			if (island.getIslandType().isSkyblock()) {
				return Optional.of((SkyblockIsland) island);
			}
		}
		return Optional.empty();
	}

	private Optional<SkyblockIsland> loadSkyblockIsland(SkyblockIslandLoadRequest request) {
		Optional<SkyblockIsland> skyblockIslandOpt = skyblockIslandLoader.loadIsland(request);
		if (skyblockIslandOpt.isPresent()) {
			SkyblockIsland island = skyblockIslandOpt.get();
			Optional<Island> cache = islandStorage.getCache(island.getIslandId(), islandId -> Optional.of(island));
			return determineSkyblockIslandFromOptional(cache);
		}
		return Optional.empty();
	}

	public void saveIsland(SkyblockIsland island) {

	}

	public Optional<IslandCoordinates> nextFreeIslandCoordinates() {
		return skyblockIslandStorage.nextFreeIslandCoordinates();
	}

}
