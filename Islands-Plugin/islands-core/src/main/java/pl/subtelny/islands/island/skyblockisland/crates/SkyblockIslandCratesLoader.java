package pl.subtelny.islands.island.skyblockisland.crates;

import pl.subtelny.crate.api.command.CrateCommandService;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.query.CrateQueryService;
import pl.subtelny.crate.api.query.request.GetCratePrototypeRequest;
import pl.subtelny.islands.crates.IslandRewardFileParserStrategyFactory;
import pl.subtelny.islands.crates.IslandRewardsFileParserStrategy;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.module.IslandModule;

import java.io.File;
import java.util.Collections;

public class SkyblockIslandCratesLoader {

    private final IslandRewardFileParserStrategyFactory strategyFactory;

    private final CrateQueryService crateQueryService;

    private final CrateCommandService crateCommandService;

    private final File cratesDir;

    public SkyblockIslandCratesLoader(IslandRewardFileParserStrategyFactory strategyFactory,
                                      CrateQueryService crateQueryService,
                                      CrateCommandService crateCommandService,
                                      File cratesDir) {
        this.strategyFactory = strategyFactory;
        this.crateQueryService = crateQueryService;
        this.crateCommandService = crateCommandService;
        this.cratesDir = cratesDir;
    }

    public void loadCrates(IslandModule<? extends Island> islandModule) {
        File[] files = cratesDir.listFiles();
        if (files != null) {
            for (File file : files) {
                loadCrate(islandModule, file);
            }
        }
    }

    private void loadCrate(IslandModule<? extends Island> islandModule, File file) {
        IslandRewardsFileParserStrategy strategy = strategyFactory.getStrategy(islandModule, file);
        GetCratePrototypeRequest request = GetCratePrototypeRequest.builder(file)
                .rewardParsers(Collections.singletonList(strategy))
                .build();

        CratePrototype cratePrototype = crateQueryService.getCratePrototype(request);
        crateCommandService.register(cratePrototype);
    }

}
