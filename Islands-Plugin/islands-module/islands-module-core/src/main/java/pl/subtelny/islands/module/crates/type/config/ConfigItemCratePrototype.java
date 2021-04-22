package pl.subtelny.islands.module.crates.type.config;

import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.utilities.Validation;

import java.util.List;
import java.util.Objects;

public class ConfigItemCratePrototype {

    private final ItemCrate itemCrate;

    private final String configurationKey;

    private final String color;

    private final String fadedColor;

    private final List<String> configurationOptions;

    public ConfigItemCratePrototype(ItemCrate itemCrate, String configurationKey, String color, String fadedColor, List<String> configurationOptions) {
        Validation.isFalse(configurationKey == null, "ConfigurationKey cannot be null");
        Validation.isFalse(configurationOptions.isEmpty(), "Configuration options cannot be null");
        this.color = color;
        this.fadedColor = fadedColor;
        this.itemCrate = itemCrate;
        this.configurationKey = configurationKey;
        this.configurationOptions = configurationOptions;
    }

    public ItemCrate getItemCrate() {
        return itemCrate;
    }

    public String getConfigurationKey() {
        return configurationKey;
    }

    public List<String> getConfigurationOptions() {
        return configurationOptions;
    }

    public String getColor() {
        return color;
    }

    public String getFadedColor() {
        return fadedColor;
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
