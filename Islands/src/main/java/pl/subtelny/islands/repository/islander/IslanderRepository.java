package pl.subtelny.islands.repository.islander;

import org.jooq.Configuration;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.database.DatabaseConfiguration;
import pl.subtelny.islands.model.islander.Islander;
import pl.subtelny.islands.repository.islander.loader.IslanderLoadRequest;
import pl.subtelny.islands.repository.islander.loader.IslanderLoader;
import pl.subtelny.islands.repository.islander.storage.IslanderStorage;

import java.util.Optional;
import pl.subtelny.islands.repository.islander.updater.IslanderUpdater;

@Component
public class IslanderRepository {

	private final IslanderStorage islanderStorage;

	private final IslanderLoader islanderLoader;

	private final IslanderUpdater islanderUpdater;

	@Autowired
	public IslanderRepository(DatabaseConfiguration databaseConfiguration, IslanderStorage islanderStorage) {
		this.islanderStorage = islanderStorage;
		Configuration configuration = databaseConfiguration.getConfiguration();
		this.islanderLoader = new IslanderLoader(configuration);
		this.islanderUpdater = new IslanderUpdater(configuration);
	}

	public Optional<Islander> getIslanderIfPresent(AccountId accountId) {
		return islanderStorage.getCacheIfPresent(accountId);
	}

	public Optional<Islander> findIslander(AccountId accountId) {
		return islanderStorage.getCache(accountId, accountId1 -> {
			IslanderLoadRequest request = IslanderLoadRequest.newBuilder()
					.where(accountId)
					.build();
			return islanderLoader.loadIslander(request);
		});
	}

	public void updateIslander(Islander islander) {
		islanderStorage.put(islander.getAccount(), Optional.of(islander));
		islanderUpdater.addToQueue(islander);
	}

}
