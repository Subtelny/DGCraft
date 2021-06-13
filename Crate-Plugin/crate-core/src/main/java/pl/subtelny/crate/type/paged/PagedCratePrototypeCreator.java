package pl.subtelny.crate.type.paged;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.crate.creator.AbstractCratePrototypeCreator;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.item.ItemCrate;
import pl.subtelny.crate.item.ItemCrateFileParserStrategy;
import pl.subtelny.crate.item.controller.PageControllerItemCrate;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.type.basic.BasicCratePrototypeCreator;
import pl.subtelny.crate.type.personal.PersonalCratePrototype;
import pl.subtelny.utilities.ConfigUtil;
import pl.subtelny.utilities.IntegerUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PagedCratePrototypeCreator extends AbstractCratePrototypeCreator {

    private final ItemCrateFileParserStrategy itemCrateFileParserStrategy;

    public PagedCratePrototypeCreator(ItemCrateFileParserStrategy itemCrateFileParserStrategy) {
        this.itemCrateFileParserStrategy = itemCrateFileParserStrategy;
    }

    @Override
    public CratePrototype create(File file, YamlConfiguration config, String path) {
        return new PagedCratePrototype(
                getCrateId(file),
                getContent(config, "static-content"),
                getPages(file, config, path),
                getPageControllerItemCrate("page-controllers.next"),
                getPageControllerItemCrate("page-controllers.previous"));
    }

    @Override
    public CrateType getCrateType() {
        return PersonalCratePrototype.TYPE;
    }

    @Override
    protected ItemCrate getItemCrate(String path) {
        return itemCrateFileParserStrategy.load(path);
    }

    private PageControllerItemCrate getPageControllerItemCrate(String path) {
        ItemCrate itemCrate = getItemCrate(path);
        boolean next = path.endsWith(".next");
        return new PageControllerItemCrate(itemCrate, next);
    }

    private List<CratePrototype> getPages(File file, YamlConfiguration configuration, String path) {
        String newPath = path(path, "pages");
        Set<String> pages = ConfigUtil.getSectionKeys(configuration, newPath)
                .orElse(new HashSet<>());

        return new ArrayList<>(pages)
                .stream()
                .filter(IntegerUtil::isInt)
                .map(Integer::parseInt)
                .sorted(Integer::compareTo)
                .map(page -> getCratePrototype(file, configuration, newPath + "." + page))
                .collect(Collectors.toList());
    }

    private CratePrototype getCratePrototype(File file, YamlConfiguration configuration, String path) {
        return new BasicCratePrototypeCreator(itemCrateFileParserStrategy)
                .create(file, configuration, path);
    }

}
