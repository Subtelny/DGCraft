package pl.subtelny.islands.island.skyblockisland.crates;

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
        crateQueryService.getCrate(GetSkyblockCrateRequest.of(main, islandModule)).open(player);
    }

    @Override
    public void openCreateCrate(Player player) {
        CrateId create = buildCrateId("create");
        crateQueryService.getCrate(GetSkyblockCrateRequest.of(create, islandModule)).open(player);
    }

    @Override
    public void openSearchCrate(Player player) {
        CrateId search = buildCrateId("search");
        crateQueryService.getCrate(GetSkyblockCrateRequest.of(search, islandModule)).open(player);
    }

    private CrateId buildCrateId(String value) {
        return CrateId.of(Islands.plugin, islandModule.getType().getInternal() + "-" + value);
    }

    @Override
    public void reloadCrates() {
        crateCommandService.unregister(buildCrateId("main"));
        crateCommandService.unregister(buildCrateId("create"));
        crateCommandService.unregister(buildCrateId("search"));
        islandCrates.loadCrates(islandModule);
    }
}
