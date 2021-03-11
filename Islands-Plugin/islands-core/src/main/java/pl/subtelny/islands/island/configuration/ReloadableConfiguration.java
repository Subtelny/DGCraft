package pl.subtelny.islands.island.configuration;

public interface ReloadableConfiguration<T extends IslandConfiguration> extends Configuration<T> {

    void reloadConfiguration();

}
