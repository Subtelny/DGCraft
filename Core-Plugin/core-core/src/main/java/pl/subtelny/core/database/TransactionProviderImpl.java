package pl.subtelny.core.database;

import org.jooq.Configuration;
import org.jooq.impl.DSL;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionStage;

@Component
public class TransactionProviderImpl implements TransactionProvider {

    private final ThreadLocal<Configuration> currentTransaction = new ThreadLocal<>();

    private final DatabaseConnection databaseConnection;

    @Autowired
    public TransactionProviderImpl(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void transaction(Runnable callable) {
        DSL.using(databaseConnection.getConfiguration())
                .transaction(configuration -> {
                    currentTransaction.set(configuration);
                    callable.run();
                });
    }

    @Override
    public CompletionStage<Void> transactionAsync(Runnable callable) {
        return DSL.using(databaseConnection.getConfiguration())
                .transactionAsync(configuration -> {
                    currentTransaction.set(configuration);
                    callable.run();
                });
    }

    @Override
    public <T> T transactionResult(Callable<T> callable) {
        return DSL.using(databaseConnection.getConfiguration())
                .transactionResult(configuration -> {
                    currentTransaction.set(configuration);
                    return callable.call();
                });
    }

    @Override
    public <T> CompletionStage<T> transactionResultAsync(Callable<T> callable) {
        return DSL.using(databaseConnection.getConfiguration())
                .transactionResultAsync(configuration -> {
                    currentTransaction.set(configuration);
                    return callable.call();
                });
    }

    @Override
    public Optional<Configuration> getCurrentTransaction() {
        return Optional.ofNullable(currentTransaction.get());
    }

}
