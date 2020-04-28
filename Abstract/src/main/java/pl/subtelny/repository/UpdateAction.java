package pl.subtelny.repository;

import java.util.concurrent.CompletionStage;

public interface UpdateAction<ANEMIA> {

    void perform(ANEMIA anemia);

    CompletionStage<Integer> performAsync(ANEMIA anemia);

}
