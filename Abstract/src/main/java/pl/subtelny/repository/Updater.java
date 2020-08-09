package pl.subtelny.repository;

import org.jooq.Configuration;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;

import java.util.concurrent.CompletableFuture;

public abstract class Updater<ENTITY, RESULT> {

    private final DatabaseConnection databaseConfiguration;

    private final TransactionProvider transactionProvider;

    protected Updater(DatabaseConnection databaseConfiguration, TransactionProvider transactionProvider) {
        this.databaseConfiguration = databaseConfiguration;
        this.transactionProvider = transactionProvider;
    }

    protected abstract RESULT performAction(ENTITY entity);

    protected abstract CompletableFuture<RESULT> performActionAsync(ENTITY entity);

    protected Configuration getConfiguration() {
        return transactionProvider.getCurrentTransaction().orElse(databaseConfiguration.getConfiguration());
    }

}
