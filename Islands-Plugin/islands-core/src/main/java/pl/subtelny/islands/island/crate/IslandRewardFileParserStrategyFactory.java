package pl.subtelny.islands.island.crate;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.ComponentProvider;
import pl.subtelny.islands.island.crate.create.IslandCreateRewardFileParserStrategy;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.module.IslandModule;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Component
public class IslandRewardFileParserStrategyFactory {

    private final ComponentProvider componentProvider;

    @Autowired
    public IslandRewardFileParserStrategyFactory(ComponentProvider componentProvider) {
        this.componentProvider = componentProvider;
    }

    public IslandRewardsFileParserStrategy getStrategy(IslandModule<? extends Island> islandModule, File file) {
        List<IslandRewardFileParserStrategy> strategies = Arrays.asList(
                getCreateStrategy(islandModule, file)
        );
        return new IslandRewardsFileParserStrategy(file, strategies);
    }

    private IslandCreateRewardFileParserStrategy getCreateStrategy(IslandModule<? extends Island> islandModule, File file) {
        return new IslandCreateRewardFileParserStrategy(file,
                islandModule.getType(),
                componentProvider
        );
    }

}
