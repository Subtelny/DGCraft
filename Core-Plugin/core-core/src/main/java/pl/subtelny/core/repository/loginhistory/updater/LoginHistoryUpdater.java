package pl.subtelny.core.repository.loginhistory.updater;

import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.repository.loginhistory.LoginHistoryAnemia;
import pl.subtelny.core.repository.loginhistory.entity.LoginHistoryId;
import pl.subtelny.repository.Updater;

import java.util.concurrent.CompletableFuture;

public class LoginHistoryUpdater extends Updater<LoginHistoryAnemia, LoginHistoryId> {

    private final DatabaseConnection databaseConnection;

    public LoginHistoryUpdater(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void updateLoginHistory(LoginHistoryAnemia anemia) {
        performAction(anemia);
    }

    public CompletableFuture<LoginHistoryId> updateLoginHistoryAsync(LoginHistoryAnemia anemia) {
        return performActionAsync(anemia);
    }

    @Override
    protected LoginHistoryId performAction(LoginHistoryAnemia anemia) {
        LoginHistoryAnemiaUpdateAction action = new LoginHistoryAnemiaUpdateAction(databaseConnection.getConfiguration());
        return action.perform(anemia);
    }

    @Override
    protected CompletableFuture<LoginHistoryId> performActionAsync(LoginHistoryAnemia anemia) {
        LoginHistoryAnemiaUpdateAction action = new LoginHistoryAnemiaUpdateAction(databaseConnection.getConfiguration());
        return action.performAsync(anemia).toCompletableFuture();
    }



}
