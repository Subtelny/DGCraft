package pl.subtelny.islands.repository.island;

import java.util.Optional;
import org.jooq.Configuration;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.database.DatabaseConfiguration;
import pl.subtelny.islands.model.Island;
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

	public Optional<SkyblockIsland> findSkyblockIsland(IslandId islandId) {
		Optional<Island> islandOpt = islandStorage.getCache(islandId, islandId1 -> {
			SkyblockIslandLoadRequest request = SkyblockIslandLoadRequest.newBuilder()
					.where(islandId)
					.build();
			return loadSkyblockIsland(request).map(island -> island);
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

	public Optional<SkyblockIsland> findSkyblockIsland(IslandCoordinates islandCoordinates) {
		SkyblockIslandLoadRequest request = SkyblockIslandLoadRequest.newBuilder()
				.where(islandCoordinates)
				.build();
		return findSkyblockIsland(request);
	}

	private Optional<SkyblockIsland> findSkyblockIsland(SkyblockIslandLoadRequest request) {
		Optional<IslandId> islandIdOpt = islandStorage.getCache(request, request1 -> loadIslandAndGetId(request));
		if (islandIdOpt.isPresent()) {
			IslandId islandId = islandIdOpt.get();
			return findSkyblockIsland(islandId);
		}
		return Optional.empty();
	}

	private Optional<IslandId> loadIslandAndGetId(SkyblockIslandLoadRequest request) {
		Optional<SkyblockIsland> skyblockIslandOpt = loadSkyblockIsland(request);
		if (skyblockIslandOpt.isPresent()) {
			SkyblockIsland island = skyblockIslandOpt.get();
			IslandId islandId = island.getIslandId();
			islandStorage.getCache(islandId, islandId1 -> Optional.of(island));
			return Optional.of(islandId);
		}
		return Optional.empty();
	}

	private Optional<SkyblockIsland> loadSkyblockIsland(SkyblockIslandLoadRequest request) {
		return skyblockIslandLoader.loadIsland(request);
	}

	public void saveIsland(SkyblockIsland island) {

	}

	public Optional<IslandCoordinates> nextFreeIslandCoordinates() {
		return skyblockIslandStorage.nextFreeIslandCoordinates();
	}

}
