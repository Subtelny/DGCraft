package pl.subtelny.utilities.messages;

import java.util.Arrays;
import java.util.Objects;

public class MessageKey {

    private final String key;

    private final Object[] objects;

    public MessageKey(String key, Object... objects) {
        this.key = key;
        this.objects = objects;
    }

    public String getKey() {
        return key;
    }

    public Object[] getObjects() {
        return objects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageKey that = (MessageKey) o;
        return Objects.equals(key, that.key) &&
                Arrays.equals(objects, that.objects);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(key);
        result = 31 * result + Arrays.hashCode(objects);
        return result;
    }
}
