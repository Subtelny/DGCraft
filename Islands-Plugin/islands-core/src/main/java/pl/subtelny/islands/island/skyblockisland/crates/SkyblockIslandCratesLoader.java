package pl.subtelny.islands.island.skyblockisland.crates;

import pl.subtelny.gui.api.crate.CrateLoadRequest;
import pl.subtelny.gui.api.crate.CratesLoaderService;
import pl.subtelny.gui.api.crate.model.CrateId;
import pl.subtelny.islands.Islands;
import pl.subtelny.islands.crates.IslandRewardFileParserStrategyFactory;
import pl.subtelny.islands.crates.IslandRewardsFileParserStrategy;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.module.IslandModule;

import java.io.File;
import java.util.List;

public class SkyblockIslandCratesLoader {

    private final IslandRewardFileParserStrategyFactory strategyFactory;

    private final CratesLoaderService cratesLoaderService;

    private final File cratesDir;

    public SkyblockIslandCratesLoader(IslandRewardFileParserStrategyFactory strategyFactory,
                                      CratesLoaderService cratesLoaderService,
                                      File cratesDir) {
        this.strategyFactory = strategyFactory;
        this.cratesLoaderService = cratesLoaderService;
        this.cratesDir = cratesDir;
    }

    public void loadCrates(IslandModule<? extends Island> islandModule) {
        IslandType islandType = islandModule.getType();
        File[] files = cratesDir.listFiles();
        if (files != null) {
            for (File file : files) {
                loadCrate(islandModule, islandType, file);
            }
        }
    }

    private void loadCrate(IslandModule<? extends Island> islandModule, IslandType islandType, File file) {
        IslandRewardsFileParserStrategy strategy = strategyFactory.getStrategy(islandModule, file);
        CrateLoadRequest request = CrateLoadRequest.newBuilder(file)
                .setPlugin(Islands.plugin)
                .setPrefix(islandType.getInternal())
                .addRewardParser(strategy)
                .build();
        cratesLoaderService.loadCrate(request);
    }

    public void unloadCrates(List<CrateId> crateIds) {
        crateIds.forEach(cratesLoaderService::unloadCrate);
    }

}
