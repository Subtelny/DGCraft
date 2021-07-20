package pl.subtelny.core.api.database;

import org.jooq.DSLContext;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionStage;

public interface TransactionProvider {

    void transaction(Runnable runnable);

    CompletionStage<Void> transactionAsync(Runnable runnable);

    <T> T transactionResult(Callable<T> runnable);

    <T> CompletionStage<T> transactionResultAsync(Callable<T> runnable);

    Optional<DSLContext> getCurrentTransaction();

}
