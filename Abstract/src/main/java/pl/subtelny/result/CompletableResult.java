package pl.subtelny.result;

import java.util.concurrent.CompletableFuture;

public abstract class CompletableResult<RESULT> {

    private CompletableFuture<RESULT> result;

    public CompletableResult(CompletableFuture<RESULT> result) {
        this.result = result;
    }

    public boolean isLoaded() {
        return result.isDone();
    }

    public boolean isLoading() {
        return !isLoaded();
    }

    public CompletableFuture<RESULT> getResult() {
        return result;
    }
}
