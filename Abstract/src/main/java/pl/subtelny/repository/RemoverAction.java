package pl.subtelny.repository;

import java.util.concurrent.CompletableFuture;

public interface RemoverAction<T> {

    void perform(T t);

    CompletableFuture<Integer> performAsync(T t);

}
