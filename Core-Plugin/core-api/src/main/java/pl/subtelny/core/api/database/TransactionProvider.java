package pl.subtelny.core.api.database;

import org.jooq.Configuration;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionStage;

public interface TransactionProvider {

    void transaction(Callable<Void> runnable);

    void transactionAsync(Callable<Void> runnable);

    <T> T transactionResult(Callable<T> runnable);

    <T> CompletionStage<T> transactionResultAsync(Callable<T> runnable);

    Optional<Configuration> getCurrentTransaction();

}
