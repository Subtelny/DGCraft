package pl.subtelny.repository;

import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;

import java.util.concurrent.CompletableFuture;

public abstract class Remover<ENTITY> extends TransactionAction {

    protected Remover(DatabaseConnection databaseConfiguration, TransactionProvider transactionProvider) {
        super(databaseConfiguration, transactionProvider);
    }

    protected abstract void performAction(ENTITY entity);

    protected abstract CompletableFuture<Integer> performActionAsync(ENTITY entity);


}
