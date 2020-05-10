package pl.subtelny.core.api.account;

import java.util.Arrays;

public enum CityType {

    RED,
    BLUE;

    public static CityType of(String name) {
        return CityType.valueOf(name);
    }

    public static boolean isCityType(String name) {
        return Arrays.stream(values()).anyMatch(cityType -> cityType.name().equals(name));
    }

}
