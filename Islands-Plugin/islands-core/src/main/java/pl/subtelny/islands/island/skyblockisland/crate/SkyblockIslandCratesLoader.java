package pl.subtelny.islands.island.skyblockisland.crate;

import pl.subtelny.crate.api.command.CrateCommandService;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.query.request.GetCratePrototypeRequest;
import pl.subtelny.islands.Islands;
import pl.subtelny.islands.island.crate.IslandCrateQueryService;
import pl.subtelny.islands.island.crate.parser.IslandRewardFileParserStrategyFactory;
import pl.subtelny.islands.island.crate.parser.IslandRewardsFileParserStrategy;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.crate.open.IslandOpenCrateRewardFileParserStrategy;
import pl.subtelny.islands.island.module.IslandModule;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;
import pl.subtelny.utilities.reward.Reward;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class SkyblockIslandCratesLoader {

    private final IslandRewardFileParserStrategyFactory strategyFactory;

    private final IslandCrateQueryService islandCrateQueryService;

    private final CrateCommandService crateCommandService;

    private final File cratesDir;

    public SkyblockIslandCratesLoader(IslandRewardFileParserStrategyFactory strategyFactory,
                                      IslandCrateQueryService islandCrateQueryService,
                                      CrateCommandService crateCommandService,
                                      File cratesDir) {
        this.strategyFactory = strategyFactory;
        this.islandCrateQueryService = islandCrateQueryService;
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
        List<PathAbstractFileParserStrategy<? extends Reward>> strategies = getStrategies(islandModule, file);
        GetCratePrototypeRequest request = GetCratePrototypeRequest.builder(Islands.plugin, file)
                .rewardParsers(strategies)
                .cratePrefix(islandModule.getType().getInternal())
                .build();
        CratePrototype cratePrototype = islandCrateQueryService.getCratePrototype(request);
        crateCommandService.register(cratePrototype);
    }

    private List<PathAbstractFileParserStrategy<? extends Reward>> getStrategies(IslandModule<? extends Island> islandModule,
                                                                                 File file) {
        IslandRewardsFileParserStrategy islandStrategy = strategyFactory.getStrategy(islandModule, file);
        IslandOpenCrateRewardFileParserStrategy openCrateStrategy = new IslandOpenCrateRewardFileParserStrategy(file, islandModule);
        return Arrays.asList(islandStrategy, openCrateStrategy);
    }

}
