package pl.subtelny.result;

import java.util.Optional;

public abstract class OptionalResult<RESULT> {

    private final RESULT result;

    protected OptionalResult(RESULT result) {
        this.result = result;
    }

    public Optional<RESULT> getResult() {
        return Optional.ofNullable(result);
    }
}
