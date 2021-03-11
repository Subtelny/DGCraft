package pl.subtelny.crate.type.paged.parser;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.parser.CratePrototypeParserStrategy;
import pl.subtelny.crate.api.parser.ItemCrateParserStrategy;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.type.paged.PagedCratePrototype;
import pl.subtelny.utilities.item.ItemStackFileParserStrategy;

import java.io.File;
import java.util.Map;

public class PagedCratePrototypeParserStrategy extends CratePrototypeParserStrategy {

    public PagedCratePrototypeParserStrategy(File file, Plugin plugin, ItemCrateParserStrategy itemCrateParserStrategy, String crateKeyPrefix) {
        super(file, plugin, crateKeyPrefix, itemCrateParserStrategy);
    }

    @Override
    public CratePrototype load(String path) {
        CratePrototypeParserStrategy.BasicInformation basicInformation = loadBasicInformation(path + ".configuration");
        Map<Integer, ItemCrate> content = loadContent(path + ".content");
        Map<Integer, ItemCrate> staticContent = loadContent(path + ".static-content");
        ItemStack previousPageItemStack = loadPageSwitcherItemStack("page-switcher.previous");
        ItemStack nextPageItemStack = loadPageSwitcherItemStack("page-switcher.next");
        return new PagedCratePrototype(
                basicInformation.crateKey,
                basicInformation.title,
                basicInformation.permission,
                basicInformation.inventorySize,
                content,
                staticContent,
                previousPageItemStack,
                nextPageItemStack
        );
    }

    private ItemStack loadPageSwitcherItemStack(String path) {
        return new ItemStackFileParserStrategy(configuration, file).load(path);
    }
}
