package pl.subtelny.islands.islander.repository;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.islander.repository.loader.IslanderLoadRequest;
import pl.subtelny.islands.islander.repository.loader.IslanderLoader;
import pl.subtelny.islands.islander.repository.storage.IslanderStorage;
import pl.subtelny.islands.islander.repository.updater.IslanderUpdater;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.islander.model.IslanderId;
import org.jooq.Configuration;
import pl.subtelny.core.api.database.DatabaseConnection;

import java.util.Optional;

@Component
public class IslanderRepository {

	private final IslanderStorage islanderStorage;

	private final IslanderLoader islanderLoader;

	private final IslanderUpdater islanderUpdater;

	@Autowired
	public IslanderRepository(DatabaseConnection databaseConfiguration) {
		this.islanderStorage = new IslanderStorage();
		this.islanderLoader = new IslanderLoader(databaseConfiguration);
		this.islanderUpdater = new IslanderUpdater(databaseConfiguration);
	}

	public Optional<Islander> getIslanderIfPresent(IslanderId islanderId) {
		return islanderStorage.getCacheIfPresent(islanderId);
	}

	public Optional<Islander> findIslander(IslanderId islanderId) {
		return islanderStorage.getCache(islanderId, islanderId1 -> {
			IslanderLoadRequest request = IslanderLoadRequest.newBuilder()
					.where(islanderId1)
					.build();
			return islanderLoader.loadIslander(request);
		});
	}

	public void updateIslander(Islander islander) {
		islanderStorage.put(islander.getIslanderId(), Optional.of(islander));
		islanderUpdater.performActionAsync(islander);
	}

}
