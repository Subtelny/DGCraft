package pl.subtelny.islands.repository.islander;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.model.islander.Islander;
import pl.subtelny.islands.model.islander.IslanderId;
import pl.subtelny.islands.repository.islander.loader.IslanderLoadRequest;
import pl.subtelny.islands.repository.islander.loader.IslanderLoader;
import pl.subtelny.islands.repository.islander.storage.IslanderStorage;
import pl.subtelny.islands.repository.islander.updater.IslanderUpdater;
import org.jooq.Configuration;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.api.database.DatabaseConnection;

import java.util.Optional;

@Component
public class IslanderRepository {

	private final IslanderStorage islanderStorage;

	private final IslanderLoader islanderLoader;

	private final IslanderUpdater islanderUpdater;

	@Autowired
	public IslanderRepository(DatabaseConnection databaseConfiguration, IslanderStorage islanderStorage) {
		this.islanderStorage = islanderStorage;
		Configuration configuration = databaseConfiguration.getConfiguration();
		this.islanderLoader = new IslanderLoader(configuration);
		this.islanderUpdater = new IslanderUpdater(configuration);
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
