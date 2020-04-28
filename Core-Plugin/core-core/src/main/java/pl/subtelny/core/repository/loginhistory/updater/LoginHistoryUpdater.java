package pl.subtelny.core.repository.loginhistory.updater;

import org.jooq.Configuration;
import pl.subtelny.core.repository.loginhistory.LoginHistoryAnemia;
import pl.subtelny.repository.Updater;

import java.util.concurrent.CompletableFuture;

public class LoginHistoryUpdater extends Updater<LoginHistoryAnemia> {

    private final Configuration configuration;

    public LoginHistoryUpdater(Configuration configuration) {
        this.configuration = configuration;
    }

    public void updateLoginHistory(LoginHistoryAnemia anemia) {
        performAction(anemia);
    }

    public CompletableFuture<Integer> updateLoginHistoryAsync(LoginHistoryAnemia anemia) {
        return performActionAsync(anemia);
    }

    @Override
    protected void performAction(LoginHistoryAnemia anemia) {
        LoginHistoryAnemiaUpdateAction action = new LoginHistoryAnemiaUpdateAction(configuration);
        action.perform(anemia);
    }

    @Override
    protected CompletableFuture<Integer> performActionAsync(LoginHistoryAnemia anemia) {
        LoginHistoryAnemiaUpdateAction action = new LoginHistoryAnemiaUpdateAction(configuration);
        return action.performAsync(anemia).toCompletableFuture();
    }



}
