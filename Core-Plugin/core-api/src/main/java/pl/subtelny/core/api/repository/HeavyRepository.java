package pl.subtelny.core.api.repository;

import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.core.api.database.TransactionalCallable;
import pl.subtelny.core.api.database.TransactionalCallbackable;

public abstract class HeavyRepository {

    private final ConnectionProvider connectionProvider;

    public HeavyRepository(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    protected void session(TransactionalCallable runnable) {
        runnable.call(connectionProvider.getCurrentConnection());
    }

    protected  <T> T session(TransactionalCallbackable<T> runnable) {
        return runnable.call(connectionProvider.getCurrentConnection());
    }

}
