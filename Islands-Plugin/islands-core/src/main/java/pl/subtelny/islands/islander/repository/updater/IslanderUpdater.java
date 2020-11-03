package pl.subtelny.islands.islander.repository.updater;

import org.jooq.Configuration;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.islands.island.IslanderId;
import pl.subtelny.islands.islander.repository.anemia.IslanderAnemia;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.core.api.repository.Updater;

import java.util.concurrent.CompletableFuture;

public class IslanderUpdater extends Updater<Islander, IslanderId> {

	public IslanderUpdater(DatabaseConnection databaseConnection, TransactionProvider transactionProvider) {
        super(databaseConnection, transactionProvider);
	}

	@Override
    public IslanderId performAction(Islander entity) {
        Configuration configuration = getConfiguration();
        IslanderAnemiaUpdateAction action = new IslanderAnemiaUpdateAction(configuration);
        IslanderAnemia islanderAnemia = domainToAnemia(entity);
        return action.perform(islanderAnemia);
    }

    @Override
    public CompletableFuture<IslanderId> performActionAsync(Islander islander) {
        Configuration configuration = getConfiguration();
        IslanderAnemiaUpdateAction action = new IslanderAnemiaUpdateAction(configuration);
        IslanderAnemia islanderAnemia = domainToAnemia(islander);
        return action.performAsync(islanderAnemia);
    }

    private IslanderAnemia domainToAnemia(Islander islander) {
        IslanderAnemia islanderAnemia = new IslanderAnemia();
        islanderAnemia.setIslanderId(islander.getIslanderId());
        return islanderAnemia;
    }

}
