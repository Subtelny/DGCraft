package pl.subtelny.core.api.city;

import pl.subtelny.utilities.identity.BasicIdentity;

public class CityId extends BasicIdentity<String> {

    private CityId(String id) {
        super(id);
    }

    public static CityId of(String name) {
        return new CityId(name);
    }

}
