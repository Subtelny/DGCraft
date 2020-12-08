package pl.subtelny.crate.api.query.request;

import pl.subtelny.crate.api.prototype.CratePrototype;

import java.util.Map;

public final class GetPredefinedCrateRequest {

    private final CratePrototype cratePrototype;

    private final Map<String, String> data;

    public GetPredefinedCrateRequest(CratePrototype cratePrototype, Map<String, String> data) {
        this.cratePrototype = cratePrototype;
        this.data = data;
    }

    public CratePrototype getCratePrototype() {
        return cratePrototype;
    }

    public Map<String, String> getData() {
        return data;
    }
}
