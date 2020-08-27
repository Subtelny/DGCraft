package pl.subtelny.core.loginhistory.repository;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.core.api.loginhistory.LoginHistory;
import pl.subtelny.core.loginhistory.repository.loader.LoginHistoryLoader;
import pl.subtelny.core.loginhistory.repository.storage.LoginHistoryStorage;
import pl.subtelny.core.loginhistory.repository.entity.LoginHistoryEntity;
import pl.subtelny.core.loginhistory.repository.loader.LoginHistoryLoadRequest;
import pl.subtelny.core.loginhistory.repository.storage.LoginHistoryCacheKey;
import pl.subtelny.core.loginhistory.repository.updater.LoginHistoryUpdater;
import pl.subtelny.utilities.Period;
import pl.subtelny.utilities.query.OrderBy;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class LoginHistoryRepository {

    private final LoginHistoryStorage storage;

    private final LoginHistoryLoader loader;

    private final LoginHistoryUpdater updater;

    @Autowired
    public LoginHistoryRepository(DatabaseConnection databaseConfiguration, TransactionProvider transactionProvider) {
        this.storage = new LoginHistoryStorage();
        this.loader = new LoginHistoryLoader(databaseConfiguration);
        this.updater = new LoginHistoryUpdater(databaseConfiguration, transactionProvider);
    }

    public Optional<LoginHistory> findLastLoginHistoryByAccountId(AccountId accountId) {
        List<LoginHistory> last = storage.getCache(LoginHistoryCacheKey.of(accountId, "last"), key -> {
            LoginHistoryLoadRequest request = LoginHistoryLoadRequest.newBuilder()
                    .where(accountId)
                    .where(1)
                    .where(OrderBy.DESC)
                    .build();
            Optional<LoginHistory> loginHistoryOpt = loader.loadLoginHistory(request);
            return loginHistoryOpt.map(Arrays::asList).orElseGet(ArrayList::new);
        });
        if (last.size() > 0) {
            return Optional.of(last.get(0));
        }
        return Optional.empty();
    }

    public void createNewLoginHistory(AccountId accountId, LocalDateTime loginTime) {
        Period period = Period.of(loginTime, LocalDateTime.now());
        LoginHistoryEntity entity = new LoginHistoryEntity(period, accountId);
        storage.put(LoginHistoryCacheKey.of(accountId, "last"), Collections.singletonList(entity));
        updater.updateLoginHistoryAsync(toAnemia(entity)).handle((integer, throwable) -> {
            throwable.printStackTrace();
            return throwable;
        });
    }

    private LoginHistoryAnemia toAnemia(LoginHistoryEntity entity) {
        return new LoginHistoryAnemia(entity.getAccountId(), entity.getLoginTime(), entity.getLogoutTime());
    }


}
