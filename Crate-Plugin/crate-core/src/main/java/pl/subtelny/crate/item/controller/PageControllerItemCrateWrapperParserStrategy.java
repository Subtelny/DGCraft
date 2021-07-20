package pl.subtelny.crate.item.controller;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.crate.api.item.ItemCrate;
import pl.subtelny.crate.api.item.ItemCrateWrapperParserStrategy;
import pl.subtelny.crate.api.item.paged.PageControllerItemCrate;

public class PageControllerItemCrateWrapperParserStrategy implements ItemCrateWrapperParserStrategy {

    @Override
    public ItemCrate create(ItemCrate itemCrate, YamlConfiguration config, String path) {
        String type = config.getString(path + ".page-controller");
        if (type != null) {
            if (type.equalsIgnoreCase("next")) {
                return new PageControllerItemCrate(itemCrate, true);
            }
            return new PageControllerItemCrate(itemCrate, false);
        }
        return itemCrate;
    }

}
