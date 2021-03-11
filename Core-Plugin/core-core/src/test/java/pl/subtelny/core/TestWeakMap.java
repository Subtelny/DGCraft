package pl.subtelny.core;

import com.google.common.collect.MapMaker;
import org.junit.Test;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;

public class TestWeakMap {

    @Test
    public void testWeakMap() {
        Map<User, Confirmation> test = new HashMap<>();
        test.put(new User("Subtelny"), new Confirmation(true));

        Map<String, Confirmation> weakMap = new MapMaker()
                .weakValues()
                .makeMap();

        weakMap.put("Test", test.get(new User("Subtelny")));
        System.out.println(weakMap.get("Test"));
        test.remove(new User("Subtelny"));
        System.gc();
        System.out.println(weakMap.get("Test"));
        System.out.println(weakMap.toString());
    }

    private class Confirmation {

        private final boolean value;

        private Confirmation(boolean value) {
            this.value = value;
        }

        public boolean isValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Confirmation that = (Confirmation) o;
            return value == that.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    private class User {

        private final String userName;

        private User(String userName) {
            this.userName = userName;
        }

        public String getUserName() {
            return userName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return Objects.equals(userName, user.userName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userName);
        }
    }

}
