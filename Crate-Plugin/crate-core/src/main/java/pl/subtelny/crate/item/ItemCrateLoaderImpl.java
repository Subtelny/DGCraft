package pl.subtelny.crate.item;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.GlobalParsersStrategies;
import pl.subtelny.crate.api.item.ItemCrate;
import pl.subtelny.crate.api.item.ItemCrateLoadRequest;
import pl.subtelny.crate.api.item.ItemCrateLoader;
import pl.subtelny.utilities.collection.CollectionUtil;
import pl.subtelny.utilities.file.FileParserStrategy;

import java.io.File;

@Component
public class ItemCrateLoaderImpl implements ItemCrateLoader {

    private final GlobalParsersStrategies defaultParsersStrategies;

    @Autowired
    public ItemCrateLoaderImpl(GlobalParsersStrategies defaultParsersStrategies) {
        this.defaultParsersStrategies = defaultParsersStrategies;
    }

    @Override
    public FileParserStrategy<ItemCrate> getItemCrateFileParserStrategy(ItemCrateLoadRequest request) {
        File file = request.getFile();
        return new ItemCrateFileParserStrategy(
                file,
                CollectionUtil.concat(request.getItemCrateWrapperParserStrategies(), defaultParsersStrategies.getGlobalItemCrateWrapperParsers()),
                CollectionUtil.concat(request.getRewardParsers(), defaultParsersStrategies.getGlobalRewardParsers(file)),
                CollectionUtil.concat(request.getCostConditionParsers(), defaultParsersStrategies.getGlobalCostConditionParsers(file)),
                CollectionUtil.concat(request.getConditionParsers(), defaultParsersStrategies.getGlobalConditionParsers(file))
        );
    }


}
