package pl.subtelny.core.api.repository;

import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;

import java.util.concurrent.CompletableFuture;

public abstract class Updater<T, R> extends TransactionAction {

    protected Updater(DatabaseConnection databaseConfiguration, TransactionProvider transactionProvider) {
        super(databaseConfiguration, transactionProvider);
    }

    protected abstract R performAction(T entity);

}
