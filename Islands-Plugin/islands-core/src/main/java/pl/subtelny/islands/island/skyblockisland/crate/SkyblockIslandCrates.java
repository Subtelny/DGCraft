package pl.subtelny.islands.island.skyblockisland.crate;

import org.bukkit.entity.Player;
import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.api.command.CrateCommandService;
import pl.subtelny.islands.Islands;
import pl.subtelny.islands.island.crate.IslandCrates;
import pl.subtelny.islands.island.skyblockisland.module.SkyblockIslandModule;

public class SkyblockIslandCrates implements IslandCrates {

    private final CrateCommandService crateCommandService;

    private final SkyblockIslandCrateQueryService crateQueryService;

    private final SkyblockIslandCratesLoader islandCrates;

    private final SkyblockIslandModule islandModule;

    public SkyblockIslandCrates(CrateCommandService crateCommandService,
                                SkyblockIslandCrateQueryService crateQueryService,
                                SkyblockIslandCratesLoader islandCrates,
                                SkyblockIslandModule islandModule) {
        this.crateCommandService = crateCommandService;
        this.crateQueryService = crateQueryService;
        this.islandCrates = islandCrates;
        this.islandModule = islandModule;
    }

    @Override
    public void openMainCrate(Player player) {
        CrateId main = buildCrateId("main");
        openCrate(player, main);
    }

    @Override
    public void openCreateCrate(Player player) {
        CrateId create = buildCrateId("create");
        openCrate(player, create);
    }

    @Override
    public void openSearchCrate(Player player) {
        CrateId search = buildCrateId("search");
        openCrate(player, search);
    }

    @Override
    public void openCrate(Player player, String rawCrateId) {
        CrateId crateId = buildCrateId(rawCrateId);
        openCrate(player, crateId);
    }

    @Override
    public void reloadCrates() {
        crateCommandService.unregister(buildCrateId("main"));
        crateCommandService.unregister(buildCrateId("create"));
        crateCommandService.unregister(buildCrateId("search"));
        islandCrates.loadCrates(islandModule);
    }

    private void openCrate(Player player, CrateId search) {
        crateQueryService.getCrate(GetSkyblockCrateRequest.of(search, islandModule)).open(player);
    }

    private CrateId buildCrateId(String value) {
        return CrateId.of(Islands.plugin, islandModule.getType().getInternal() + "-" + value);
    }
}
