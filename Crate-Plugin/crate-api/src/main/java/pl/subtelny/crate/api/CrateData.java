package pl.subtelny.crate.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CrateData {

    private final Map<String, Object> data = new HashMap<>();

    public void addData(String key, Object data) {
        this.data.put(key, data);
    }

    public <T> T getData(String key) {
        return (T) data.get(key);
    }

    public <T> Optional<T> findData(String key) {
        Object object = data.get(key);
        if (object != null) {
            return Optional.of((T) object);
        }
        return Optional.empty();
    }

}
