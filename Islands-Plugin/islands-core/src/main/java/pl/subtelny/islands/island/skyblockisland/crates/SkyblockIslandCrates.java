package pl.subtelny.islands.island.skyblockisland.crates;

import pl.subtelny.gui.api.crate.CrateLoadRequest;
import pl.subtelny.gui.api.crate.CratesLoaderService;
import pl.subtelny.gui.api.crate.model.CrateId;
import pl.subtelny.islands.Islands;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.gui.IslandCrates;

import java.io.File;

public class SkyblockIslandCrates implements IslandCrates {

    private final IslandType islandType;

    private final CratesLoaderService cratesLoaderService;

    private final File cratesDir;

    public SkyblockIslandCrates(IslandType islandType, CratesLoaderService cratesLoaderService, File cratesDir) {
        this.islandType = islandType;
        this.cratesLoaderService = cratesLoaderService;
        this.cratesDir = cratesDir;
    }

    public void loadCrates() {
        CrateLoadRequest request = CrateLoadRequest.newBuilder(cratesDir)
                .setPlugin(Islands.plugin)
                .setPrefix(islandType.getInternal())
                .build();
        cratesLoaderService.loadCrates(request);
    }

    @Override
    public CrateId getMainCrate() {
        return CrateId.of(Islands.plugin, islandType.getInternal() + "-main");
    }

    @Override
    public void reloadCrates() {
        cratesLoaderService.unloadCrate(getMainCrate());
        loadCrates();
    }
}
