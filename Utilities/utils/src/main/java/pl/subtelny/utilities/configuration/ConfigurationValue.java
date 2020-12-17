package pl.subtelny.utilities.configuration;

import java.io.Serializable;

public interface ConfigurationValue<T extends Serializable> {

    T get();

}
