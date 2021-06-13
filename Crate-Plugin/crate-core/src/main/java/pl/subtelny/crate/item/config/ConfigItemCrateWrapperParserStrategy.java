package pl.subtelny.crate.item.config;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.crate.api.item.ItemCrate;
import pl.subtelny.crate.api.item.ItemCrateWrapperParserStrategy;

import java.util.List;

public class ConfigItemCrateWrapperParserStrategy implements ItemCrateWrapperParserStrategy {

    @Override
    public ItemCrate create(ItemCrate itemCrate, YamlConfiguration config, String path) {
        String configPath = path + ".configuration";
        if (config.isConfigurationSection(configPath)) {
            String key = config.getString(configPath + ".key");
            String color = config.getString(configPath + ".color");
            String fadedColor = config.getString(configPath + ".faded-color");
            List<String> options = config.getStringList(configPath + ".options");
            return new ConfigItemCrate(itemCrate, key, color, fadedColor, options);
        }
        return itemCrate;
    }

}
