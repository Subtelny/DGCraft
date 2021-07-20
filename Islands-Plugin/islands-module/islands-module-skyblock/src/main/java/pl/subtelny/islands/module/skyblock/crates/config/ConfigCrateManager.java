package pl.subtelny.islands.module.skyblock.crates.config;

import org.bukkit.entity.Player;
import pl.subtelny.crate.api.CrateData;
import pl.subtelny.crate.api.CrateService;
import pl.subtelny.crate.api.creator.CrateCreateRequest;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.prototype.CratePrototypeLoader;
import pl.subtelny.islands.api.module.component.CratesComponent;
import pl.subtelny.islands.api.repository.IslandConfigurationRepository;
import pl.subtelny.islands.module.skyblock.SkyblockIslandModule;
import pl.subtelny.islands.module.skyblock.crates.ACrateManager;
import pl.subtelny.islands.module.skyblock.model.SkyblockIsland;
import pl.subtelny.utilities.exception.ValidationException;

public class ConfigCrateManager extends ACrateManager {

    private static final String CONFIG_FILE_NAME = "config.yml";

    private final IslandConfigurationRepository islandConfigurationRepository;

    private CratePrototype cratePrototype;

    public ConfigCrateManager(SkyblockIslandModule islandModule,
                              CrateService crateService,
                              CratePrototypeLoader cratePrototypeLoader,
                              IslandConfigurationRepository islandConfigurationRepository,
                              CratesComponent islandCrates) {
        super(crateService, cratePrototypeLoader, islandModule.getIslandType().getInternal(), islandCrates);
        this.islandConfigurationRepository = islandConfigurationRepository;
    }

    @Override
    public void open(Player player) {
        throw new IllegalStateException("SkyblockIsland config crate is openable only with island parameter");
    }

    @Override
    public void open(Player player, SkyblockIsland island) {
        validateOpen();
        CrateData crateData = getCrateData(island);
        CrateCreateRequest<CratePrototype> request = getCrateCreateRequest(island, crateData);
        openCrate(player, request);
    }

    @Override
    public void reload() {
        this.cratePrototype = loadCratePrototype(CONFIG_FILE_NAME);
    }

    private CrateData getCrateData(SkyblockIsland island) {
        return CrateData.builder()
                .addData("config", island.getConfiguration())
                .build();
    }

    private CrateCreateRequest<CratePrototype> getCrateCreateRequest(SkyblockIsland island, CrateData crateData) {
        return CrateCreateRequest.builder(cratePrototype)
                .crateData(crateData)
                .closeCrateListener((player, crate) -> islandConfigurationRepository.saveConfiguration(island))
                .build();
    }

    private void validateOpen() {
        if (cratePrototype == null) {
            throw ValidationException.of("skyblockIslandModule.crateManager.config.not_initialized_yet");
        }
    }

}
