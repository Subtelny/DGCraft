package pl.subtelny.islands.crate.type.config.creator;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.ComponentProvider;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.InventoryInfo;
import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.creator.CrateCreatorStrategy;
import pl.subtelny.islands.crate.type.config.*;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ConfigCrateCreatorStrategy implements CrateCreatorStrategy<ConfigCrateCreatorRequest> {

    private final ComponentProvider componentProvider;

    @Autowired
    public ConfigCrateCreatorStrategy(ComponentProvider componentProvider) {
        this.componentProvider = componentProvider;
    }

    @Override
    public Crate create(ConfigCrateCreatorRequest request) {
        InventoryInfo inventory = createInventory(request);
        return new ConfigContentCrate(
                request.getCrateKey(),
                request.getPermission(),
                inventory,
                prepareContent(request),
                componentProvider,
                request.getIsland());
    }

    @Override
    public CrateType getType() {
        return ConfigCratePrototype.TYPE;
    }

    private Map<Integer, ItemCrate> prepareContent(ConfigCrateCreatorRequest request) {
        Map<Integer, ItemCrate> content = new HashMap<>(request.getContent());
        Island island = request.getIsland();
        IslandConfiguration configuration = island.getConfiguration();
        Map<Integer, ItemCrate> configContent = toConfigContent(request, configuration);
        content.putAll(configContent);
        return content;
    }

    private Map<Integer, ItemCrate> toConfigContent(ConfigCrateCreatorRequest request, IslandConfiguration configuration) {
        return request.getConfigContent()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> toItemCrate(entry.getValue(), configuration)));
    }

    private ItemCrate toItemCrate(ConfigItemCratePrototype prototype, IslandConfiguration configuration) {
        return new ConfigItemCrate(
                prototype.getItemCrate(),
                prototype.getConfigurationKey(),
                configuration,
                prototype.getConfigurationOptions()
        );
    }

    private InventoryInfo createInventory(ConfigCrateCreatorRequest request) {
        return InventoryInfo.of(request.getTitle(), request.getInventorySize());
    }
}
