package pl.subtelny.crate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CrateData {

    public static CrateData EMPTY = new CrateData(Collections.unmodifiableMap(new HashMap<>()), new HashMap<>());

    private final Map<String, Object> data;

    private final Map<String, String> values;

    public CrateData(Map<String, Object> data, Map<String, String> values) {
        this.data = data;
        this.values = values;
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

    public <T> T getData(String key) {
        return (T) data.get(key);
    }

}
