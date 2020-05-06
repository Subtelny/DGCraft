package pl.subtelny.core.repository.loginhistory.updater;

import org.jooq.Configuration;
import org.jooq.InsertReturningStep;
import org.jooq.impl.DSL;
import pl.subtelny.core.repository.loginhistory.LoginHistoryAnemia;
import pl.subtelny.core.repository.loginhistory.entity.LoginHistoryId;
import pl.subtelny.generated.tables.tables.LoginHistories;
import pl.subtelny.generated.tables.tables.records.LoginHistoriesRecord;
import pl.subtelny.repository.UpdateAction;

import java.sql.Timestamp;
import java.util.concurrent.CompletionStage;

public class LoginHistoryAnemiaUpdateAction implements UpdateAction<LoginHistoryAnemia> {

    private final Configuration configuration;

    public LoginHistoryAnemiaUpdateAction(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void perform(LoginHistoryAnemia anemia) {
        prepareExecute(anemia).execute();
    }

    @Override
    public CompletionStage<Integer> performAsync(LoginHistoryAnemia anemia) {
        return prepareExecute(anemia).executeAsync();
    }

    private InsertReturningStep<LoginHistoriesRecord> prepareExecute(LoginHistoryAnemia anemia) {
        LoginHistoriesRecord record = toRecord(anemia);
        return DSL.using(configuration)
                .insertInto(LoginHistories.LOGIN_HISTORIES)
                .set(record)
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
