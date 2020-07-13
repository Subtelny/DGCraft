package pl.subtelny.core.repository.loginhistory.updater;

import org.jooq.Configuration;
import org.jooq.InsertReturningStep;
import org.jooq.InsertSetMoreStep;
import org.jooq.Record1;
import org.jooq.impl.DSL;
import pl.subtelny.core.repository.loginhistory.LoginHistoryAnemia;
import pl.subtelny.core.repository.loginhistory.entity.LoginHistoryId;
import pl.subtelny.generated.tables.tables.LoginHistories;
import pl.subtelny.generated.tables.tables.records.LoginHistoriesRecord;
import pl.subtelny.jobs.JobsProvider;
import pl.subtelny.repository.UpdateAction;

import java.sql.Timestamp;
import java.util.concurrent.CompletableFuture;

public class LoginHistoryAnemiaUpdateAction implements UpdateAction<LoginHistoryAnemia, LoginHistoryId> {

    private final Configuration configuration;

    public LoginHistoryAnemiaUpdateAction(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public LoginHistoryId perform(LoginHistoryAnemia anemia) {
        LoginHistoryId id = anemia.getId();
        if (id.getId() == null) {
            return executeMissingId(anemia);
        }
        prepareExecute(anemia).execute();
        return id;
    }

    @Override
    public CompletableFuture<LoginHistoryId> performAsync(LoginHistoryAnemia anemia) {
        LoginHistoryId id = anemia.getId();
        if (id.getId() == null) {
            JobsProvider.supplyAsync(() -> executeMissingId(anemia));
        }
        return prepareExecute(anemia).executeAsync()
                .toCompletableFuture()
                .thenApply(integer -> id);
    }

    public LoginHistoryId executeMissingId(LoginHistoryAnemia anemia) {
        Record1<Integer> record = prepareExecute(anemia).returningResult(LoginHistories.LOGIN_HISTORIES.ID).fetchOne();
        return LoginHistoryId.of(record.component1());
    }

    private InsertReturningStep<LoginHistoriesRecord> prepareExecute(LoginHistoryAnemia anemia) {
        LoginHistoriesRecord record = toRecord(anemia);
        InsertSetMoreStep<LoginHistoriesRecord> set = DSL.using(configuration)
                .insertInto(LoginHistories.LOGIN_HISTORIES)
                .set(record);
        if (record.getId() == null) {
            return set;
        }
        return set
                .onDuplicateKeyUpdate()
                .set(record);
    }

    public LoginHistoriesRecord toRecord(LoginHistoryAnemia anemia) {
        LoginHistoriesRecord record = DSL.using(configuration).newRecord(LoginHistories.LOGIN_HISTORIES);
        if (!anemia.getId().equals(LoginHistoryId.empty())) {
            record.setId(anemia.getId().getId());
        }
        record.setAccount(anemia.getAccountId().getId());
        record.setLoginTime(Timestamp.valueOf(anemia.getLoginTime()));
        record.setLogoutTime(Timestamp.valueOf(anemia.getLogoutTime()));
        return record;
    }

}
