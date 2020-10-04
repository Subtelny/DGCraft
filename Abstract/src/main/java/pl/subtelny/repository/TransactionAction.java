package pl.subtelny.repository;

import org.jooq.Configuration;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;

public abstract class TransactionAction {

    private final DatabaseConnection databaseConfiguration;

    protected final TransactionProvider transactionProvider;

    protected TransactionAction(DatabaseConnection databaseConfiguration, TransactionProvider transactionProvider) {
        this.databaseConfiguration = databaseConfiguration;
        this.transactionProvider = transactionProvider;
    }

    protected Configuration getConfiguration() {
        return transactionProvider.getCurrentTransaction().orElse(databaseConfiguration.getConfiguration());
    }
}
