package pl.subtelny.crate.item;

import org.bukkit.configuration.file.YamlConfiguration;

public interface ItemCrateWrapperParserStrategy {

    ItemCrate create(ItemCrate itemCrate, YamlConfiguration config, String path);

}
