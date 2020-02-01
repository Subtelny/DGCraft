package pl.subtelny.islands.repository.islander;

import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.islands.model.islander.Islander;
import pl.subtelny.islands.repository.islander.storage.IslanderStorage;

import java.util.Optional;

@Component
public class IslanderRepository {

    private final IslanderStorage islanderStorage;

    @Autowired
    public IslanderRepository(IslanderStorage islanderStorage) {
        this.islanderStorage = islanderStorage;
    }

    public Optional<Islander> findIslander(AccountId accountId) {
        return islanderStorage.getCache(accountId, accountId1 -> {

            //TODO
            return null;
        });
    }

}
