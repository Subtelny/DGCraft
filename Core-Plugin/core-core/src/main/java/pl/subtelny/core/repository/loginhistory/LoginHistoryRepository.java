package pl.subtelny.core.repository.loginhistory;

import org.jooq.Configuration;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.api.database.DatabaseConfiguration;
import pl.subtelny.core.api.loginhistory.LoginHistory;
import pl.subtelny.core.repository.loginhistory.entity.LoginHistoryEntity;
import pl.subtelny.core.repository.loginhistory.entity.LoginHistoryId;
import pl.subtelny.core.repository.loginhistory.loader.LoginHistoryLoadRequest;
import pl.subtelny.core.repository.loginhistory.loader.LoginHistoryLoader;
import pl.subtelny.core.repository.loginhistory.storage.LoginHistoryCacheKey;
import pl.subtelny.core.repository.loginhistory.storage.LoginHistoryStorage;
import pl.subtelny.core.repository.loginhistory.updater.LoginHistoryUpdater;
import pl.subtelny.utilities.Period;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class LoginHistoryRepository {

    private final LoginHistoryStorage storage;

    private final LoginHistoryLoader loader;

    private final LoginHistoryUpdater updater;

    @Autowired
    public LoginHistoryRepository(DatabaseConfiguration databaseConfiguration) {
        this.storage = new LoginHistoryStorage();
        Configuration configuration = databaseConfiguration.getConfiguration();
        this.loader = new LoginHistoryLoader(configuration);
        this.updater = new LoginHistoryUpdater(configuration);
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
        LoginHistoryEntity entity = new LoginHistoryEntity(new LoginHistoryId(0), period, accountId);
        storage.put(LoginHistoryCacheKey.of(accountId, "last"), Collections.singletonList(entity));
        updater.updateLoginHistoryAsync(toAnemia(entity)).handle((integer, throwable) -> {
            throwable.printStackTrace();;
            return throwable;
        });
    }

    private LoginHistoryAnemia toAnemia(LoginHistoryEntity entity) {
        return new LoginHistoryAnemia(entity.getId(), entity.getAccountId(), entity.getLoginTime(), entity.getLogoutTime());
    }


}
