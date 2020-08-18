package pl.subtelny.core.loginhistory.repository.updater;

import org.jooq.Configuration;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.core.loginhistory.repository.LoginHistoryAnemia;
import pl.subtelny.core.loginhistory.repository.entity.LoginHistoryId;
import pl.subtelny.repository.Updater;

import java.util.concurrent.CompletableFuture;

public class LoginHistoryUpdater extends Updater<LoginHistoryAnemia, LoginHistoryId> {

    public LoginHistoryUpdater(DatabaseConnection databaseConnection, TransactionProvider transactionProvider) {
        super(databaseConnection, transactionProvider);
    }

    public void updateLoginHistory(LoginHistoryAnemia anemia) {
        performAction(anemia);
    }

    public CompletableFuture<LoginHistoryId> updateLoginHistoryAsync(LoginHistoryAnemia anemia) {
        return performActionAsync(anemia);
    }

    @Override
    protected LoginHistoryId performAction(LoginHistoryAnemia anemia) {
        Configuration configuration = getConfiguration();
        LoginHistoryAnemiaUpdateAction action = new LoginHistoryAnemiaUpdateAction(configuration);
        return action.perform(anemia);
    }

    @Override
    protected CompletableFuture<LoginHistoryId> performActionAsync(LoginHistoryAnemia anemia) {
        Configuration configuration = getConfiguration();
        LoginHistoryAnemiaUpdateAction action = new LoginHistoryAnemiaUpdateAction(configuration);
        return action.performAsync(anemia).toCompletableFuture();
    }



}
