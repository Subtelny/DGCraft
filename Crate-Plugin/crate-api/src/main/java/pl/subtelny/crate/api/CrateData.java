package pl.subtelny.crate.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CrateData {

    private Map<String, Object> data = new HashMap<>();

    private Map<String, String> values = new HashMap<>();

    public CrateData() {

    }

    public CrateData(Map<String, Object> data, Map<String, String> values) {
        this.data = data;
        this.values = values;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static CrateData empty() {
        return new CrateData();
    }

    public void addData(String key, Object data) {
        this.data.put(key, data);
    }

    public Optional<String> findValue(String key) {
        return Optional.ofNullable(values.get(key));
    }

    public Map<String, String> getValues() {
        return values;
    }

    public <T> Optional<T> findData(String key) {
        Object object = data.get(key);
        if (object != null) {
            return Optional.of((T) object);
        }
        return Optional.empty();
    }

    public <T> T getData(String key, Class<T> clazz) {
        return clazz.cast(data.get(key));
    }

    public static class Builder {

        private final Map<String, Object> data = new HashMap<>();

        private final Map<String, String> values = new HashMap<>();

        public Builder addData(String key, Object value) {
            this.data.put(key, value);
            return this;
        }

        public Builder addValue(String key, String value) {
            this.values.put(key, value);
            return this;
        }

        public CrateData build() {
            return new CrateData(data, values);
        }

    }

}
