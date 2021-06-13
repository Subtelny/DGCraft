package pl.subtelny.crate.prototype;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.prototype.CratePrototypeCreator;
import pl.subtelny.crate.api.prototype.CratePrototypeLoadRequest;
import pl.subtelny.crate.api.prototype.CratePrototypeLoader;
import pl.subtelny.crate.creator.DefaultCratePrototypeCreators;
import pl.subtelny.crate.DefaultParsersStrategies;
import pl.subtelny.crate.item.ItemCrateFileParserStrategy;
import pl.subtelny.utilities.Validation;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CratePrototypeLoaderImpl implements CratePrototypeLoader {

    private final DefaultParsersStrategies defaultParsersStrategies;

    @Autowired
    public CratePrototypeLoaderImpl(DefaultParsersStrategies defaultParsersStrategies) {
        this.defaultParsersStrategies = defaultParsersStrategies;
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

    private CratePrototype loadCratePrototype(File file, CratePrototypeLoadRequest request) {
        ItemCrateFileParserStrategy itemCrateStrategy = getItemCrateFileParserStrategy(file, request);
        CratePrototypeFileParserStrategy cratePrototypeStrategy = getCratePrototypeFileParserStrategy(file, itemCrateStrategy, request);
        return cratePrototypeStrategy.load("");
    }

    private CratePrototypeFileParserStrategy getCratePrototypeFileParserStrategy(File file, ItemCrateFileParserStrategy itemCrateFileParserStrategy, CratePrototypeLoadRequest request) {
        List<CratePrototypeCreator> defaultCreators = DefaultCratePrototypeCreators.getDefaultCratePrototypeCreators(itemCrateFileParserStrategy);
        List<CratePrototypeCreator> concatCreators = concat(request.getCratePrototypeCreators(), defaultCreators);
        return new CratePrototypeFileParserStrategy(file, concatCreators);
    }

    private ItemCrateFileParserStrategy getItemCrateFileParserStrategy(File file, CratePrototypeLoadRequest request) {
        return new ItemCrateFileParserStrategy(
                file,
                concat(request.getItemCrateWrapperParserStrategies(), defaultParsersStrategies.getDefaultItemCrateWrapperParsers()),
                concat(request.getRewardParsers(), defaultParsersStrategies.getDefaultRewardParsers(file)),
                concat(request.getCostConditionParsers(), defaultParsersStrategies.getDefaultCostConditionParsers(file)),
                concat(request.getConditionParsers(), defaultParsersStrategies.getDefaultConditionParsers(file))
        );
    }

    private <T> List<T> concat(List<T> listOne, List<T> listTwo) {
        List<T> newList = new ArrayList<>();
        newList.addAll(listOne);
        newList.addAll(listTwo);
        return newList;
    }

}
