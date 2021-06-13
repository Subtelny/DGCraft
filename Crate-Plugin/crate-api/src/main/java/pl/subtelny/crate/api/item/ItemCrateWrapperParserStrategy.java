package pl.subtelny.crate.api.item;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.crate.api.item.ItemCrate;

public interface ItemCrateWrapperParserStrategy {

    ItemCrate create(ItemCrate itemCrate, YamlConfiguration config, String path);

}
