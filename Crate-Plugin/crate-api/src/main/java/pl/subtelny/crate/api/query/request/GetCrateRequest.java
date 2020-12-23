package pl.subtelny.crate.api.query.request;

import pl.subtelny.crate.api.CrateId;

import java.util.HashMap;
import java.util.Map;

public class GetCrateRequest {

    private final CrateId crateId;

    private final Map<String, String> data;

    public GetCrateRequest(CrateId crateId, Map<String, String> data) {
        this.crateId = crateId;
        this.data = data;
    }

    public static GetCrateRequest of(CrateId crateId) {
        return new GetCrateRequest(crateId, new HashMap<>());
    }

    public CrateId getCrateId() {
        return crateId;
    }

    public Map<String, String> getData() {
        return data;
    }
}
