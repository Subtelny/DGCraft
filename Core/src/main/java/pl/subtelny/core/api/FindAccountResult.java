package pl.subtelny.core.api;

import pl.subtelny.core.model.Account;
import pl.subtelny.result.CompletableResult;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class FindAccountResult extends CompletableResult<Optional<Account>> {

    public FindAccountResult(CompletableFuture<Optional<Account>> result) {
        super(result);
    }
}
