package pl.subtelny.repository;

import java.util.concurrent.CompletableFuture;

public abstract class Updater<ENTITY> {

    protected abstract void performAction(ENTITY entity);

    protected abstract CompletableFuture<Integer> performActionAsync(ENTITY entity);


}
