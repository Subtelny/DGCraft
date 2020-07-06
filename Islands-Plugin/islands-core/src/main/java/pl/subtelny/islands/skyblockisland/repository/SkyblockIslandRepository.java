package pl.subtelny.islands.skyblockisland.repository;

import java.util.Optional;
import org.jooq.Configuration;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.skyblockisland.extendcuboid.SkyblockIslandExtendCuboidCalculator;
import pl.subtelny.islands.skyblockisland.repository.loader.SkyblockIslandLoadRequest;
import pl.subtelny.islands.skyblockisland.repository.loader.SkyblockIslandLoader;
import pl.subtelny.islands.skyblockisland.repository.storage.SkyblockIslandStorage;
import pl.subtelny.islands.repository.islander.IslanderRepository;
import pl.subtelny.islands.skyblockisland.repository.updater.SkyblockIslandUpdater;

@Component
public class SkyblockIslandRepository {

	private final SkyblockIslandLoader loader;

	private final SkyblockIslandStorage storage;

	private final SkyblockIslandUpdater updater;

	@Autowired
	public SkyblockIslandRepository(DatabaseConnection databaseConfiguration,
									IslanderRepository islanderRepository,
									SkyblockIslandExtendCuboidCalculator extendCuboidCalculator) {
		Configuration configuration = databaseConfiguration.getConfiguration();
		this.storage = new SkyblockIslandStorage();
		this.loader = new SkyblockIslandLoader(configuration, islanderRepository, extendCuboidCalculator);
		this.updater = new SkyblockIslandUpdater(configuration);
	}

	public void saveIslandAsync(SkyblockIsland skyblockIsland) {
		updater.updateAsync(skyblockIsland);
	}

	public Optional<SkyblockIsland> findSkyblockIsland(IslandCoordinates islandCoordinates) {
		if (!storage.isIslandCoordinatesFree(islandCoordinates)) {
			Optional<SkyblockIslandId> cache = storage.getCache(islandCoordinates);
			return cache.flatMap(this::findSkyblockIsland).or(Optional::empty);
		}
		return Optional.empty();
	}

	public Optional<SkyblockIsland> findSkyblockIsland(SkyblockIslandId islandId) {
		SkyblockIsland cachedIsland = storage.getCache(islandId, islandId1 -> {
			SkyblockIslandLoadRequest request = SkyblockIslandLoadRequest.newBuilder()
					.where(islandId)
					.build();
			return loader.loadIsland(request).orElse(null);
		});
		return Optional.ofNullable(cachedIsland);
	}

	public Optional<IslandCoordinates> nextFreeIslandCoordinates() {
		return storage.nextFreeIslandCoordinates();
	}

}
