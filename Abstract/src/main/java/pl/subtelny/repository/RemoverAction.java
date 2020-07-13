package pl.subtelny.repository;

import java.util.concurrent.CompletableFuture;

public interface RemoverAction<T> {

    void perform();

    CompletableFuture<Integer> performAsync();

}
