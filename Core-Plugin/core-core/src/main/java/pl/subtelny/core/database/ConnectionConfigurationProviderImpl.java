package pl.subtelny.core.database;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;

@Component
public class ConnectionConfigurationProviderImpl implements ConnectionProvider {

    private final DatabaseConnection databaseConnection;

    private final TransactionProvider transactionProvider;

    @Autowired
    public ConnectionConfigurationProviderImpl(DatabaseConnection databaseConnection, TransactionProvider transactionProvider) {
        this.databaseConnection = databaseConnection;
        this.transactionProvider = transactionProvider;
    }

    @Override
    public DSLContext getCurrentConnection() {
        Configuration configuration = transactionProvider.getCurrentTransaction()
                .orElseGet(databaseConnection::getConfiguration);
        return DSL.using(configuration);
    }
}
