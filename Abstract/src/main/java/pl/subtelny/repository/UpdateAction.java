package pl.subtelny.repository;

import java.util.concurrent.CompletableFuture;

public interface UpdateAction<ANEMIA, ID> {

    ID perform(ANEMIA anemia);

    CompletableFuture<ID> performAsync(ANEMIA anemia);

}
