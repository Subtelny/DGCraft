package pl.subtelny.core.loginhistory.repository.updater;

import org.jooq.Configuration;
import org.jooq.InsertReturningStep;
import org.jooq.impl.DSL;
import pl.subtelny.core.loginhistory.repository.LoginHistoryAnemia;
import pl.subtelny.generated.tables.tables.LoginHistories;
import pl.subtelny.generated.tables.tables.records.LoginHistoriesRecord;
import pl.subtelny.repository.UpdateAction;

import java.sql.Timestamp;
import java.util.concurrent.CompletableFuture;

public class LoginHistoryAnemiaUpdateAction implements UpdateAction<LoginHistoryAnemia, Integer> {

    private final Configuration configuration;

    public LoginHistoryAnemiaUpdateAction(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Integer perform(LoginHistoryAnemia anemia) {
        prepareExecute(anemia).execute();
        return 1;
    }

    @Override
    public CompletableFuture<Integer> performAsync(LoginHistoryAnemia anemia) {
        return prepareExecute(anemia).executeAsync()
                .toCompletableFuture();
    }

    private InsertReturningStep<LoginHistoriesRecord> prepareExecute(LoginHistoryAnemia anemia) {
        LoginHistoriesRecord record = toRecord(anemia);
        return DSL.using(configuration)
                .insertInto(LoginHistories.LOGIN_HISTORIES)
                .set(record);
    }

    public LoginHistoriesRecord toRecord(LoginHistoryAnemia anemia) {
        LoginHistoriesRecord record = DSL.using(configuration).newRecord(LoginHistories.LOGIN_HISTORIES);
        record.setAccount(anemia.getAccountId().getInternal());
        record.setLoginTime(Timestamp.valueOf(anemia.getLoginTime()));
        record.setLogoutTime(Timestamp.valueOf(anemia.getLogoutTime()));
        return record;
    }

}
