package pl.subtelny.crate;

import pl.subtelny.crate.prototype.CratePrototype;

public class CrateCreateRequest<T extends CratePrototype> {

    private final T cratePrototype;

    private final CrateData crateData;

    private CrateCreateRequest(T cratePrototype, CrateData crateData) {
        this.cratePrototype = cratePrototype;
        this.crateData = crateData;
    }

    public static <T extends CratePrototype> Builder builder(T cratePrototype) {
        return new Builder<T>(cratePrototype);
    }

    public T getCratePrototype() {
        return cratePrototype;
    }

    public CrateData getCrateData() {
        return crateData;
    }

    public static class Builder<T extends CratePrototype> {

        private final T cratePrototype;

        private CrateData crateData = CrateData.EMPTY;

        public Builder(T cratePrototype) {
            this.cratePrototype = cratePrototype;
        }

        public Builder crateData(CrateData crateData) {
            this.crateData = crateData;
            return this;
        }

        public CrateCreateRequest<T> build() {
            return new CrateCreateRequest<T>(cratePrototype, crateData);
        }

    }

}
