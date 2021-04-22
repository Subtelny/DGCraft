package pl.subtelny.islands.island.configuration;

import java.util.function.Supplier;

public class ConfigurationReloadableImpl<T> implements ReloadableConfiguration<T> {

    private final Supplier<T> supplier;

    private T configuration;

    public ConfigurationReloadableImpl(Supplier<T> supplier) {
        this.supplier = supplier;
        this.configuration = supplier.get();
    }

    @Override
    public void reloadConfiguration() {
        this.configuration = supplier.get();
    }

    @Override
    public T get() {
        return configuration;
    }

}
