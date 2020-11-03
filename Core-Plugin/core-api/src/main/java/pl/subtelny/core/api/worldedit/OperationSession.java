package pl.subtelny.core.api.worldedit;

import pl.subtelny.utilities.Callback;

import java.util.concurrent.CompletableFuture;

public interface OperationSession {

    CompletableFuture<Void> performAsync();

    void cancel();

    void setStateListener(Callback<Integer> state);

}
