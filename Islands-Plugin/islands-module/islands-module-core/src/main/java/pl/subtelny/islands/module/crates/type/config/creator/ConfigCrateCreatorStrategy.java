package pl.subtelny.islands.module.crates.type.config.creator;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.ComponentProvider;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.InventoryInfo;
import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.islands.module.crates.IslandCrateCreatorStrategy;
import pl.subtelny.islands.module.crates.type.config.ConfigContentCrate;
import pl.subtelny.islands.module.crates.type.config.ConfigCratePrototype;
import pl.subtelny.islands.module.crates.type.config.ConfigItemCrate;
import pl.subtelny.islands.module.crates.type.config.ConfigItemCratePrototype;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ConfigCrateCreatorStrategy implements IslandCrateCreatorStrategy<ConfigCratePrototype> {

    private final ComponentProvider componentProvider;

    @Autowired
    public ConfigCrateCreatorStrategy(ComponentProvider componentProvider) {
        this.componentProvider = componentProvider;
    }

    @Override
    public Crate create(ConfigCratePrototype cratePrototype, Island island) {
        InventoryInfo inventory = createInventory(cratePrototype);
        return new ConfigContentCrate(
                cratePrototype.getCrateKey(),
                cratePrototype.getPermission(),
                inventory,
                prepareContent(cratePrototype, island),
                componentProvider,
                island);
    }

    @Override
    public CrateType getType() {
        return ConfigCratePrototype.TYPE;
    }

    private Map<Integer, ItemCrate> prepareContent(ConfigCratePrototype prototype, Island island) {
        Map<Integer, ItemCrate> content = new HashMap<>(prototype.getContent());
        IslandConfiguration configuration = island.getConfiguration();
        Map<Integer, ItemCrate> configContent = toConfigContent(prototype, configuration);
        content.putAll(configContent);
        return content;
    }

    private Map<Integer, ItemCrate> toConfigContent(ConfigCratePrototype prototype, IslandConfiguration configuration) {
        return prototype.getConfigContent()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> toItemCrate(entry.getValue())));
    }

    private ItemCrate toItemCrate(ConfigItemCratePrototype prototype) {
        return new ConfigItemCrate(
                prototype.getItemCrate(),
                prototype.getConfigurationKey(),
                prototype.getColor(),
                prototype.getFadedColor(),
                prototype.getConfigurationOptions()
        );
    }

    private InventoryInfo createInventory(ConfigCratePrototype prototype) {
        return InventoryInfo.of(prototype.getTitle(), prototype.getInventorySize());
    }
}
