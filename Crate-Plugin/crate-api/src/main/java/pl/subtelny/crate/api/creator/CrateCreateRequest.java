package pl.subtelny.crate.api.creator;


import pl.subtelny.crate.api.CrateData;
import pl.subtelny.crate.api.listener.CrateListener;
import pl.subtelny.crate.api.prototype.CratePrototype;

public class CrateCreateRequest<T extends CratePrototype> {

    private final T cratePrototype;

    private final CrateData crateData;

    private final CrateListener closeCrateListener;

    private CrateCreateRequest(T cratePrototype, CrateData crateData, CrateListener closeCrateListener) {
        this.cratePrototype = cratePrototype;
        this.crateData = crateData;
        this.closeCrateListener = closeCrateListener;
    }

    public static <T extends CratePrototype> Builder<T> builder(T cratePrototype) {
        return new Builder<T>(cratePrototype);
    }

    public T getCratePrototype() {
        return cratePrototype;
    }

    public CrateData getCrateData() {
        return crateData;
    }

    public CrateListener getCloseCrateListener() {
        return closeCrateListener;
    }

    public static class Builder<T extends CratePrototype> {

        private final T cratePrototype;

        private CrateData crateData = CrateData.empty();

        private CrateListener closeCrateListener;

        public Builder(T cratePrototype) {
            this.cratePrototype = cratePrototype;
        }

        public Builder<T> closeCrateListener(CrateListener closeCrateListener) {
            this.closeCrateListener = closeCrateListener;
            return this;
        }

        public Builder<T> crateData(CrateData crateData) {
            this.crateData = crateData;
            return this;
        }

        public CrateCreateRequest<T> build() {
            return new CrateCreateRequest<T>(cratePrototype, crateData, closeCrateListener);
        }

    }

}
