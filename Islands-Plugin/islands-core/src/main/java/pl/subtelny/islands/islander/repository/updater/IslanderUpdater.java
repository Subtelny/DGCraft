package pl.subtelny.islands.islander.repository.updater;

import org.jooq.Configuration;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.islands.islander.model.IslanderId;
import pl.subtelny.islands.islander.repository.anemia.IslanderAnemia;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.repository.Updater;

import java.util.concurrent.CompletableFuture;

public class IslanderUpdater extends Updater<Islander, IslanderId> {

    private final DatabaseConnection databaseConnection;

	public IslanderUpdater(DatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}

	@Override
    public IslanderId performAction(Islander entity) {
        Configuration configuration = databaseConnection.getConfiguration();
        IslanderAnemiaUpdateAction action = new IslanderAnemiaUpdateAction(configuration);
        IslanderAnemia islanderAnemia = domainToAnemia(entity);
        return action.perform(islanderAnemia);
    }

    @Override
    public CompletableFuture<IslanderId> performActionAsync(Islander islander) {

        return null;
    }

    private IslanderAnemia domainToAnemia(Islander islander) {
        IslanderAnemia islanderAnemia = new IslanderAnemia();
        islanderAnemia.setIslanderId(islander.getIslanderId());
        return islanderAnemia;
    }

}
