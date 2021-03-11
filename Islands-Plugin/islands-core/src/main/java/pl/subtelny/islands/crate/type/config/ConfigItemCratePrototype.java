package pl.subtelny.islands.crate.type.config;

import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.utilities.configuration.ConfigurationKey;
import pl.subtelny.utilities.configuration.ConfigurationValue;

import java.util.List;
import java.util.Objects;

public class ConfigItemCratePrototype {

    private final ItemCrate itemCrate;

    private final ConfigurationKey configurationKey;

    private final List<ConfigurationValue> configurationOptions;

    public ConfigItemCratePrototype(ItemCrate itemCrate, ConfigurationKey configurationKey, List<ConfigurationValue> configurationOptions) {
        this.itemCrate = itemCrate;
        this.configurationKey = configurationKey;
        this.configurationOptions = configurationOptions;
    }

    public ItemCrate getItemCrate() {
        return itemCrate;
    }

    public ConfigurationKey getConfigurationKey() {
        return configurationKey;
    }

    public List<ConfigurationValue> getConfigurationOptions() {
        return configurationOptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigItemCratePrototype that = (ConfigItemCratePrototype) o;
        return Objects.equals(itemCrate, that.itemCrate) && Objects.equals(configurationKey, that.configurationKey) && Objects.equals(configurationOptions, that.configurationOptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemCrate, configurationKey, configurationOptions);
    }
}
