package pl.subtelny.islands.island.repository.loader;

import org.jooq.Configuration;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.islands.island.model.AbstractIsland;
import pl.subtelny.islands.island.repository.anemia.IslandAnemia;

import java.util.Optional;

public abstract class IslandLoader<ANEMIA extends IslandAnemia, DOMAIN extends AbstractIsland> {

	private final DatabaseConnection databaseConfiguration;

	private final TransactionProvider transactionProvider;

	protected IslandLoader(DatabaseConnection databaseConfiguration, TransactionProvider transactionProvider) {
		this.databaseConfiguration = databaseConfiguration;
		this.transactionProvider = transactionProvider;
	}

	public Optional<DOMAIN> loadIsland(IslandAnemiaLoadAction<ANEMIA> loadAction) {
		Optional<ANEMIA> account = performAction(loadAction);
		if (account.isPresent()) {
			ANEMIA anemia = account.get();
			DOMAIN island = mapAnemiaToDomain(anemia);
			return Optional.of(island);
		}
		return Optional.empty();
	}

	private Optional<ANEMIA> performAction(IslandAnemiaLoadAction<ANEMIA> loadAction) {
		ANEMIA loadedData = loadAction.perform();
		return Optional.ofNullable(loadedData);
	}

	protected Configuration getConfiguration() {
		return transactionProvider.getCurrentTransaction().orElse(databaseConfiguration.getConfiguration());
	}

	protected abstract DOMAIN mapAnemiaToDomain(ANEMIA anemia);
}
