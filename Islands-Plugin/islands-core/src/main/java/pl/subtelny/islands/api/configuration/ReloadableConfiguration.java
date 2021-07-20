package pl.subtelny.islands.api.configuration;

public interface ReloadableConfiguration<T> extends Configuration<T> {

    void reloadConfiguration();

}
