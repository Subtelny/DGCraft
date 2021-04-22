package pl.subtelny.islands.island.configuration;

public interface ReloadableConfiguration<T> extends Configuration<T> {

    void reloadConfiguration();

}
