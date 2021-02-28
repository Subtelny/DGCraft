package pl.subtelny.crate.type.paged.parser;

import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.ItemCrate;
import pl.subtelny.crate.parser.CratePrototypeParserStrategy;
import pl.subtelny.crate.parser.ItemCrateParserStrategy;
import pl.subtelny.crate.prototype.CratePrototype;
import pl.subtelny.crate.type.paged.PagedCratePrototype;
import pl.subtelny.utilities.item.ItemStackFileParserStrategy;

import java.io.File;
import java.util.Map;

public class PagedCratePrototypeParserStrategy extends CratePrototypeParserStrategy {

    public PagedCratePrototypeParserStrategy(File file, ItemCrateParserStrategy itemCrateParserStrategy, String crateKeyPrefix) {
        super(file, crateKeyPrefix, itemCrateParserStrategy);
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
