package pl.subtelny.islands.island.skyblockisland.crates;

import org.bukkit.entity.Player;
import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.api.command.CrateCommandService;
import pl.subtelny.crate.api.query.CrateQueryService;
import pl.subtelny.crate.api.query.request.GetCrateRequest;
import pl.subtelny.islands.Islands;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.crate.IslandCrates;
import pl.subtelny.islands.island.module.IslandModule;

public class SkyblockIslandCrates implements IslandCrates {

    private final CrateCommandService crateCommandService;

    private final CrateQueryService crateQueryService;

    private final SkyblockIslandCratesLoader islandCrates;

    private final IslandModule<? extends Island> islandModule;

    public SkyblockIslandCrates(CrateCommandService crateCommandService,
                                CrateQueryService crateQueryService,
                                SkyblockIslandCratesLoader islandCrates,
                                IslandModule<? extends Island> islandModule) {
        this.crateCommandService = crateCommandService;
        this.crateQueryService = crateQueryService;
        this.islandCrates = islandCrates;
        this.islandModule = islandModule;
    }

    @Override
    public void openMainCrate(Player player) {
        CrateId main = buildCrateId("main");
        crateQueryService.getCrate(GetCrateRequest.of(main)).open(player);
    }

    @Override
    public void openCreateCrate(Player player) {
        CrateId create = buildCrateId("create");
        crateQueryService.getCrate(GetCrateRequest.of(create)).open(player);
    }

    @Override
    public void openSearchCrate(Player player) {
        CrateId search = buildCrateId("search");
        crateQueryService.getCrate(GetCrateRequest.of(search)).open(player);
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
