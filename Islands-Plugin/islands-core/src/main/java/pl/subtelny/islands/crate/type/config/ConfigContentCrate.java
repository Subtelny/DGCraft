package pl.subtelny.islands.crate.type.config;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.ComponentProvider;
import pl.subtelny.crate.api.ContentCrate;
import pl.subtelny.crate.api.CrateKey;
import pl.subtelny.crate.api.InventoryInfo;
import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.cqrs.command.IslandConfigurationService;
import pl.subtelny.utilities.Validation;

import java.util.Map;

public class ConfigContentCrate extends ContentCrate {

    private final ComponentProvider componentProvider;

    private final Island island;

    public ConfigContentCrate(CrateKey crateKey,
                              String permission,
                              InventoryInfo inventory,
                              Map<Integer, ItemCrate> content,
                              ComponentProvider componentProvider,
                              Island island) {
        super(crateKey, permission, inventory, content);
        this.componentProvider = componentProvider;
        this.island = island;
    }

    @Override
    public void open(Player player) {
        Validation.isTrue(viewers.isEmpty(), "crate.only_one_viewer");
        super.open(player);
    }

    @Override
    public void close() {
        componentProvider.getComponent(IslandConfigurationService.class).updateIslandConfiguration(island);
    }
}
