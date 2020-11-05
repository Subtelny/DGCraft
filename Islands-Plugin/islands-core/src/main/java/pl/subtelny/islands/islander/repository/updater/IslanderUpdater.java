package pl.subtelny.islands.islander.repository.updater;

import org.jooq.DSLContext;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.core.api.repository.Updater;
import pl.subtelny.islands.island.IslanderId;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.islander.repository.anemia.IslanderAnemia;

public class IslanderUpdater implements Updater<Islander, IslanderId> {

    private final ConnectionProvider connectionProvider;

    public IslanderUpdater(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public IslanderId performAction(Islander entity) {
        DSLContext connection = connectionProvider.getCurrentConnection();
        IslanderAnemiaUpdateAction action = new IslanderAnemiaUpdateAction(connection);
        IslanderAnemia islanderAnemia = domainToAnemia(entity);
        return action.perform(islanderAnemia);
    }

    private IslanderAnemia domainToAnemia(Islander islander) {
        IslanderAnemia islanderAnemia = new IslanderAnemia();
        islanderAnemia.setIslanderId(islander.getIslanderId());
        return islanderAnemia;
    }

}
