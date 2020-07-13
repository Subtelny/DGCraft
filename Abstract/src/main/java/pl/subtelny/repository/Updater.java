package pl.subtelny.repository;

import java.util.concurrent.CompletableFuture;

public abstract class Updater<ENTITY, RESULT> {

    protected abstract RESULT performAction(ENTITY entity);

    protected abstract CompletableFuture<RESULT> performActionAsync(ENTITY entity);


}
