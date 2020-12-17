package pl.subtelny.islands.islander.repository;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.api.account.Accounts;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.islands.island.IslanderId;
import pl.subtelny.islands.island.membership.IslandMembershipQueryService;
import pl.subtelny.islands.island.query.IslandQueryService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.islander.repository.storage.IslanderCacheLoader;
import pl.subtelny.islands.islander.repository.storage.IslanderStorage;
import pl.subtelny.islands.islander.repository.updater.IslanderUpdater;
import pl.subtelny.utilities.NullObject;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class IslanderRepository {

    private final IslanderStorage islanderStorage;

    private final IslanderUpdater islanderUpdater;

    private final IslandQueryService islandQueryService;

    private final Accounts accounts;

    @Autowired
    public IslanderRepository(ConnectionProvider connectionProvider,
                              IslandQueryService islandQueryService,
                              IslandMembershipQueryService islandMembershipQueryService,
                              Accounts accounts) {
        this.islandQueryService = islandQueryService;
        this.accounts = accounts;
        this.islanderStorage = new IslanderStorage(new IslanderCacheLoader(connectionProvider, islandQueryService, islandMembershipQueryService, accounts));
        this.islanderUpdater = new IslanderUpdater(connectionProvider);
    }

    public Optional<Islander> getIslanderIfPresent(IslanderId islanderId) {
        return islanderStorage.getCacheIfPresent(islanderId).flatMap(NullObject::get);
    }

    public Optional<Islander> findIslander(IslanderId islanderId) {
        return islanderStorage.getCache(islanderId).get();
    }

    public void createIslander(IslanderId islanderId) {
        Account account = accounts.findAccount(AccountId.of(islanderId.getInternal()))
                .orElseThrow(() -> ValidationException.of("islander-repository.account_not_found", islanderId));
        Islander islander = new Islander(islanderId, new ArrayList<>(), islandQueryService, account);
        updateIslander(islander);
    }

    public void updateIslander(Islander islander) {
        islanderStorage.put(islander.getIslanderId(), NullObject.of(islander));
        islanderUpdater.performAction(islander);
    }

}
