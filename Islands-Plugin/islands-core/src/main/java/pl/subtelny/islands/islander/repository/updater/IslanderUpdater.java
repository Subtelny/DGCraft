package pl.subtelny.islands.islander.repository.updater;

import org.jooq.Configuration;
import pl.subtelny.islands.islander.repository.anemia.IslanderAnemia;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.repository.Updater;

import java.util.concurrent.CompletableFuture;

public class IslanderUpdater extends Updater<Islander> {

    private final Configuration configuration;

	public IslanderUpdater(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
    public void performAction(Islander entity) {
        IslanderAnemiaUpdateAction action = new IslanderAnemiaUpdateAction(configuration);
        IslanderAnemia islanderAnemia = domainToAnemia(entity);
        action.perform(islanderAnemia);
    }

    @Override
    public CompletableFuture<Integer> performActionAsync(Islander islander) {

        return null;
    }

    private IslanderAnemia domainToAnemia(Islander islander) {
        IslanderAnemia islanderAnemia = new IslanderAnemia();
        islanderAnemia.setIslanderId(islander.getIslanderId());
        return islanderAnemia;
    }

}
