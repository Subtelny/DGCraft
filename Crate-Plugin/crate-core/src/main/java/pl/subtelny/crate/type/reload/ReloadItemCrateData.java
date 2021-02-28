package pl.subtelny.crate.type.reload;

import java.util.Map;

public final class ReloadItemCrateData {

    private final Map<String, String> data;

    public ReloadItemCrateData(Map<String, String> data) {
        this.data = data;
    }

    public Map<String, String> getData() {
        return data;
    }
}
