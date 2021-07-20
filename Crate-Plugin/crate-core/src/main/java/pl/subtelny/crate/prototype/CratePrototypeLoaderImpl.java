package pl.subtelny.crate.prototype;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.item.ItemCrate;
import pl.subtelny.crate.api.item.ItemCrateLoader;
import pl.subtelny.crate.api.prototype.*;
import pl.subtelny.crate.creator.DefaultCratePrototypeCreators;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.file.FileParserStrategy;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static pl.subtelny.utilities.collection.CollectionUtil.concat;

@Component
public class CratePrototypeLoaderImpl implements CratePrototypeLoader {

    private final ItemCrateLoader itemCrateLoader;

    @Autowired
    public CratePrototypeLoaderImpl(ItemCrateLoader itemCrateLoader) {
        this.itemCrateLoader = itemCrateLoader;
    }

    @Override
    public List<CratePrototype> loadCratePrototypes(CratePrototypeLoadRequest request) {
        File dir = request.getFile();
        Validation.isTrue(dir.isDirectory(), "File is not directory, " + dir.getName());

        File[] array = dir.listFiles();
        if (array != null) {
            return Arrays.stream(array)
                    .filter(File::isFile)
                    .map(file -> loadCratePrototype(file, request))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public CratePrototype loadCratePrototype(CratePrototypeLoadRequest request) {
        File file = request.getFile();
        Validation.isTrue(file.isFile(), "File is not has not configuration extension, " + file.getName());
        return loadCratePrototype(file, request);
    }

    @Override
    public CratePrototype loadCratePrototype(CratePrototypeLoadGlobalRequest request) {
        File file = request.getFile();
        Validation.isTrue(file.isFile(), "File is not has not configuration extension, " + file.getName());

        FileParserStrategy<ItemCrate> itemCrateFileParserStrategy = itemCrateLoader.getItemCrateFileParserStrategy(request.getItemCrateLoadRequest());
        CratePrototypeCreator<?> creator = DefaultCratePrototypeCreators.getDefaultCratePrototypeCreator(request.getCrateType(), itemCrateFileParserStrategy);
        return creator.create(file);
    }

    private CratePrototype loadCratePrototype(File file, CratePrototypeLoadRequest request) {
        FileParserStrategy<ItemCrate> itemCrateStrategy = itemCrateLoader.getItemCrateFileParserStrategy(request.getItemCrateLoadRequest());
        CratePrototypeFileParserStrategy cratePrototypeStrategy = getCratePrototypeFileParserStrategy(file, itemCrateStrategy, request);
        return cratePrototypeStrategy.load("");
    }

    private CratePrototypeFileParserStrategy getCratePrototypeFileParserStrategy(File file, FileParserStrategy<ItemCrate> itemCrateFileParserStrategy, CratePrototypeLoadRequest request) {
        List<CratePrototypeCreator> defaultCreators = DefaultCratePrototypeCreators.getDefaultCratePrototypeCreators(itemCrateFileParserStrategy);
        List<CratePrototypeCreator> concatCreators = concat(request.getCratePrototypeCreators(), defaultCreators);
        return new CratePrototypeFileParserStrategy(file, concatCreators);
    }

}
