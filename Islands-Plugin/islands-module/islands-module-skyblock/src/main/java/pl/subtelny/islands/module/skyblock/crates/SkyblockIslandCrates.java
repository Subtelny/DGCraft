package pl.subtelny.islands.module.skyblock.crates;

import org.bukkit.entity.Player;
import pl.subtelny.crate.api.CrateService;
import pl.subtelny.crate.api.prototype.CratePrototypeLoader;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.crates.IslandCrates;
import pl.subtelny.islands.island.repository.IslandConfigurationRepository;
import pl.subtelny.islands.module.skyblock.SkyblockIslandModule;
import pl.subtelny.islands.module.skyblock.crates.config.ConfigCrateManager;
import pl.subtelny.islands.module.skyblock.crates.main.MainCrateManager;
import pl.subtelny.islands.module.skyblock.model.SkyblockIsland;
import pl.subtelny.utilities.Preconditions;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SkyblockIslandCrates implements IslandCrates {

    private final SkyblockIslandModule islandModule;

    private final Map<String, CrateManager> crateManagers = new HashMap<>();

    public SkyblockIslandCrates(SkyblockIslandModule islandModule,
                                CrateService crateService,
                                CratePrototypeLoader cratePrototypeLoader,
                                IslandConfigurationRepository islandConfigurationRepository) {
        this.islandModule = Preconditions.notNull(islandModule, "IslandModule cannot be null");
        crateManagers.put("config", new ConfigCrateManager(islandModule, crateService, cratePrototypeLoader, islandConfigurationRepository));
        crateManagers.put("main", new MainCrateManager(islandModule, crateService, cratePrototypeLoader));
    }

    @Override
    public void openCrate(Player player, String crate) {
        getCrateManager(crate).open(player);
    }

    @Override
    public void openCrate(Player player, IslandId islandId, String crate) {
        SkyblockIsland skyblockIsland = islandModule.findIsland(islandId)
                .orElseThrow(() -> ValidationException.of("skyblockIslandCrates.island_not_found"));
        getCrateManager(crate).open(player, skyblockIsland);
    }

    @Override
    public void reload() {
        crateManagers.values().forEach(CrateManager::reload);
    }

    private CrateManager getCrateManager(String crateName) {
        return Optional.ofNullable(crateManagers.get(crateName))
                .orElseThrow(() -> ValidationException.of("skyblockIslandCrates.crate_manager_not_found", crateName));
    }

}
