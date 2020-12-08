package pl.subtelny.crate.service;

import pl.subtelny.crate.model.CrateId;

import java.util.Map;

public final class GetCrateRequest {

    private final CrateId crateId;

    private final Map<String, String> data;

    public GetCrateRequest(CrateId crateId, Map<String, String> data) {
        this.crateId = crateId;
        this.data = data;
    }

    public CrateId getCrateId() {
        return crateId;
    }

    public Map<String, String> getData() {
        return data;
    }
}
