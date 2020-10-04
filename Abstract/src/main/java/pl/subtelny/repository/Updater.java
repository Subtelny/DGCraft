package pl.subtelny.repository;

import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;

import java.util.concurrent.CompletableFuture;

public abstract class Updater<ENTITY, RESULT> extends TransactionAction {

    protected Updater(DatabaseConnection databaseConfiguration, TransactionProvider transactionProvider) {
        super(databaseConfiguration, transactionProvider);
    }

    protected abstract RESULT performAction(ENTITY entity);

    protected abstract CompletableFuture<RESULT> performActionAsync(ENTITY entity);

}
