package pl.subtelny.islands.module.skyblock.crates.main;

import org.bukkit.entity.Player;
import pl.subtelny.crate.api.CrateService;
import pl.subtelny.crate.api.creator.CrateCreateRequest;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.prototype.CratePrototypeLoader;
import pl.subtelny.islands.module.skyblock.SkyblockIslandModule;
import pl.subtelny.islands.module.skyblock.crates.ACrateManager;
import pl.subtelny.islands.module.skyblock.model.SkyblockIsland;
import pl.subtelny.utilities.Validation;

public class MainCrateManager extends ACrateManager {

    public static final String NON_ISLAND_FILE_NAME = "create.yml";

    public static final String ISLAND_FILE_NAME = "main.yml";

    private CratePrototype nonIslandCratePrototype;

    private CratePrototype islandCratePrototype;

    public MainCrateManager(SkyblockIslandModule islandModule,
                               CrateService crateService,
                               CratePrototypeLoader cratePrototypeLoader) {
        super(crateService, cratePrototypeLoader, islandModule.getIslandType().getInternal());
    }

    @Override
    public void open(Player player) {
        validateOpen();
        CrateCreateRequest<CratePrototype> request = CrateCreateRequest.builder(nonIslandCratePrototype).build();
        openCrate(player, request);
    }

    @Override
    public void open(Player player, SkyblockIsland island) {
        validateOpen();
        CrateCreateRequest<CratePrototype> request = CrateCreateRequest.builder(islandCratePrototype).build();
        openCrate(player, request);
    }

    @Override
    public void reload() {
        nonIslandCratePrototype = loadCratePrototype(NON_ISLAND_FILE_NAME);
        islandCratePrototype = loadCratePrototype(ISLAND_FILE_NAME);
    }

    private void validateOpen() {
        Validation.isTrue(nonIslandCratePrototype != null, "skyblockIslandModule.crateManager.main.not_initialized_yet");
        Validation.isTrue(islandCratePrototype != null, "skyblockIslandModule.crateManager.main.not_initialized_yet");
    }

}
