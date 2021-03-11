package pl.subtelny.islands.crate.type.config.creator;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.*;
import pl.subtelny.crate.api.creator.CrateCreatorStrategy;
import pl.subtelny.islands.crate.type.config.ConfigCrateCreatorRequest;
import pl.subtelny.islands.crate.type.config.ConfigCratePrototype;
import pl.subtelny.islands.crate.type.config.ConfigItemCrate;
import pl.subtelny.islands.crate.type.config.ConfigItemCratePrototype;
import pl.subtelny.islands.island.Island;
import pl.subtelny.utilities.ColorUtil;
import pl.subtelny.utilities.configuration.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ConfigCrateCreatorStrategy implements CrateCreatorStrategy<ConfigCrateCreatorRequest> {

    @Override
    public Crate create(ConfigCrateCreatorRequest request) {
        InventoryInfo inventory = createInventory(request);
        return new ContentCrate(
                request.getCrateKey(),
                request.getPermission(),
                inventory,
                prepareContent(request)
        );
    }

    @Override
    public CrateType getType() {
        return ConfigCratePrototype.TYPE;
    }

    private Map<Integer, ItemCrate> prepareContent(ConfigCrateCreatorRequest request) {
        Map<Integer, ItemCrate> content = new HashMap<>(request.getContent());
        Island island = request.getIsland();
        Configuration configuration = island.getConfiguration();
        Map<Integer, ItemCrate> configContent = toConfigContent(request, configuration);
        content.putAll(configContent);
        return content;
    }

    private Map<Integer, ItemCrate> toConfigContent(ConfigCrateCreatorRequest request, Configuration configuration) {
        return request.getConfigContent()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> toItemCrate(entry.getValue(), configuration)));
    }

    private ItemCrate toItemCrate(ConfigItemCratePrototype prototype, Configuration configuration) {
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
