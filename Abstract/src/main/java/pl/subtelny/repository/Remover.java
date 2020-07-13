package pl.subtelny.repository;

import java.util.concurrent.CompletableFuture;

public abstract class Remover<ENTITY> {

    protected abstract void performAction(ENTITY entity);

    protected abstract CompletableFuture<Void> performActionAsync(ENTITY entity);


}
