package pl.subtelny.islands.island.configuration;

public interface ConfigurationReloadable<T extends IslandConfiguration> extends Configuration<T> {

    void reloadConfiguration();

}
