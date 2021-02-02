package pl.subtelny.islands.island.skyblockisland.crate;

import org.bukkit.entity.Player;
import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.api.command.CrateCommandService;
import pl.subtelny.islands.Islands;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.crate.IslandCrateQueryService;
import pl.subtelny.islands.island.crate.IslandCrates;
import pl.subtelny.islands.island.skyblockisland.module.SkyblockIslandModule;

public class SkyblockIslandCrates implements IslandCrates {

    private final CrateCommandService crateCommandService;

    private final IslandCrateQueryService islandCrateQueryService;

    private final SkyblockIslandCratesLoader islandCrates;

    private final SkyblockIslandModule islandModule;

    public SkyblockIslandCrates(CrateCommandService crateCommandService,
                                IslandCrateQueryService islandCrateQueryService,
                                SkyblockIslandCratesLoader islandCrates,
                                SkyblockIslandModule islandModule) {
        this.crateCommandService = crateCommandService;
        this.islandCrateQueryService = islandCrateQueryService;
        this.islandCrates = islandCrates;
        this.islandModule = islandModule;
    }

    @Override
    public void openMainCrate(Player player) {
        openCrate(player, "main");
    }

    @Override
    public void openCreateCrate(Player player) {
        openCrate(player, "create");
    }

    @Override
    public void openInvitesCrate(Player player, Island island) {
        CrateId crateId = buildCrateId("invites");
        islandCrateQueryService.getCrate(GetIslandCrateRequest.of(crateId, island)).open(player);
    }

    @Override
    public void openSearchCrate(Player player) {
        openCrate(player, "search");
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
        crateCommandService.unregister(buildCrateId("invites"));
        islandCrates.loadCrates(islandModule);
    }

    private void openCrate(Player player, CrateId crateId) {
        islandCrateQueryService.getCrate(GetIslandCrateRequest.of(crateId)).open(player);
    }

    private CrateId buildCrateId(String value) {
        return CrateId.of(Islands.plugin, islandModule.getType().getInternal() + "-" + value);
    }
}
