package pl.subtelny.utilities.configuration;

import pl.subtelny.utilities.identity.BasicIdentity;

public class ConfigurationKey extends BasicIdentity<String> {

    public ConfigurationKey(String id) {
        super(id);
    }

    public static ConfigurationKey of(String id) {
        return new ConfigurationKey(id);
    }

}
