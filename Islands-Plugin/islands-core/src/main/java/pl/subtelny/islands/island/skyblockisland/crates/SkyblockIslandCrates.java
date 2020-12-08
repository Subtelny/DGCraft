package pl.subtelny.islands.island.skyblockisland.crates;

import org.bukkit.entity.Player;
import pl.subtelny.gui.api.crate.model.CrateId;
import pl.subtelny.gui.api.crate.session.PlayerCrateSessionService;
import pl.subtelny.islands.Islands;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.gui.IslandCrates;
import pl.subtelny.islands.island.module.IslandModule;

import java.util.Arrays;

public class SkyblockIslandCrates implements IslandCrates {

    private final PlayerCrateSessionService playerCrateSessionService;

    private final SkyblockIslandCratesLoader cratesLoaderService;

    private final IslandModule<? extends Island> islandModule;

    public SkyblockIslandCrates(PlayerCrateSessionService playerCrateSessionService,
                                SkyblockIslandCratesLoader cratesLoaderService,
                                IslandModule<? extends Island> islandModule) {
        this.playerCrateSessionService = playerCrateSessionService;
        this.cratesLoaderService = cratesLoaderService;
        this.islandModule = islandModule;
    }

    @Override
    public void openMainCrate(Player player) {
        CrateId main = buildCrateId("main");
        playerCrateSessionService.openSession(player, main);
    }

    @Override
    public void openCreateCrate(Player player) {
        CrateId create = buildCrateId("create");
        playerCrateSessionService.openSession(player, create);
    }

    @Override
    public void openSearchCrate(Player player) {
        CrateId search = buildCrateId("search");
        playerCrateSessionService.openSession(player, search);
    }

    private CrateId buildCrateId(String value) {
        return CrateId.of(Islands.plugin, islandModule.getType().getInternal() + "-" + value);
    }

    @Override
    public void reloadCrates() {
        cratesLoaderService.unloadCrates(Arrays.asList(
                buildCrateId("main"),
                buildCrateId("create"),
                buildCrateId("search")
        ));
        cratesLoaderService.loadCrates(islandModule);
    }
}
