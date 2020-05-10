package pl.subtelny.core.repository.loginhistory.updater;

import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.repository.loginhistory.LoginHistoryAnemia;
import pl.subtelny.repository.Updater;

import java.util.concurrent.CompletableFuture;

public class LoginHistoryUpdater extends Updater<LoginHistoryAnemia> {

    private final DatabaseConnection databaseConnection;

    public LoginHistoryUpdater(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void updateLoginHistory(LoginHistoryAnemia anemia) {
        performAction(anemia);
    }

    public CompletableFuture<Integer> updateLoginHistoryAsync(LoginHistoryAnemia anemia) {
        return performActionAsync(anemia);
    }

    @Override
    protected void performAction(LoginHistoryAnemia anemia) {
        LoginHistoryAnemiaUpdateAction action = new LoginHistoryAnemiaUpdateAction(databaseConnection.getConfiguration());
        action.perform(anemia);
    }

    @Override
    protected CompletableFuture<Integer> performActionAsync(LoginHistoryAnemia anemia) {
        LoginHistoryAnemiaUpdateAction action = new LoginHistoryAnemiaUpdateAction(databaseConnection.getConfiguration());
        return action.performAsync(anemia).toCompletableFuture();
    }



}
