package pl.subtelny.islands.module.crates.type.config.parser;

import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.parser.CratePrototypeParserStrategy;
import pl.subtelny.crate.api.parser.ItemCrateParserStrategy;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.islands.module.crates.type.config.ConfigCratePrototype;
import pl.subtelny.islands.module.crates.type.config.ConfigItemCratePrototype;
import pl.subtelny.utilities.ConfigUtil;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class ConfigCratePrototypeParserStrategy extends CratePrototypeParserStrategy {

    public ConfigCratePrototypeParserStrategy(File file,
                                              String crateKeyPrefix,
                                              ItemCrateParserStrategy itemCrateParserStrategy) {
        super(file, crateKeyPrefix, itemCrateParserStrategy);
    }

    @Override
    public CratePrototype load(String path) {
        BasicInformation basicInformation = loadBasicInformation("configuration");
        Map<Integer, ItemCrate> content = loadContent("content");
        Map<Integer, ConfigItemCratePrototype> configContent = loadConfigContent();
        return new ConfigCratePrototype(
                basicInformation.crateKey,
                basicInformation.title,
                basicInformation.permission,
                basicInformation.inventorySize,
                content,
                configContent
        );
    }

    @Override
    protected Map<Integer, ItemCrate> loadContent(String path) {
        Set<String> sections = ConfigUtil.getSectionKeys(configuration, path).orElseGet(HashSet::new);
        return sections.stream()
                .map(this::parseToInt)
                .filter(slot -> !configuration.contains(path + "." + slot + ".configuration"))
                .collect(Collectors.toMap(slot -> slot, slot -> loadItemCrate(path + "." + slot)));
    }

    protected Map<Integer, ConfigItemCratePrototype> loadConfigContent() {
        Set<String> sections = ConfigUtil.getSectionKeys(configuration, "content").orElseGet(HashSet::new);
        return sections.stream()
                .map(this::parseToInt)
                .filter(slot -> configuration.contains("content" + "." + slot + ".configuration"))
                .collect(Collectors.toMap(slot -> slot, slot -> loadConfigItemCratePrototype("content" + "." + slot)));
    }

    private ConfigItemCratePrototype loadConfigItemCratePrototype(String path) {
        ItemCrate itemCrate = loadItemCrate(path);
        String configurationKey = loadFromPath(path + ".configuration.key");
        String color = loadFromPath(path + ".configuration.color");
        String fadedColor = loadFromPath(path + ".configuration.faded-color");
        List<String> options = loadConfigurationOptions(path + ".configuration.options");
        return new ConfigItemCratePrototype(itemCrate, configurationKey, color, fadedColor, options);
    }

    private String loadFromPath(String path) {
        return configuration.getString(path);
    }

    private List<String> loadConfigurationOptions(String path) {
        List<String> optionKeys = configuration.getStringList(path);
        return new ArrayList<>(optionKeys);
    }

}
