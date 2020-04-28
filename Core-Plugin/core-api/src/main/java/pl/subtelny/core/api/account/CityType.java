package pl.subtelny.core.api.account;

public enum CityType {

    RED,
    BLUE;

    public static CityType of(String name) {
        return CityType.valueOf(name);
    }

}
