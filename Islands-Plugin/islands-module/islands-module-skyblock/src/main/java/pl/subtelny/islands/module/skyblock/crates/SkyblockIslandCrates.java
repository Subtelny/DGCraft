package pl.subtelny.islands.module.skyblock.crates;

import org.bukkit.entity.Player;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateKey;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.service.CrateService;
import pl.subtelny.crate.api.service.InitializeCrateRequest;
import pl.subtelny.islands.Islands;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.crates.IslandCrates;
import pl.subtelny.islands.island.module.IslandModule;
import pl.subtelny.islands.module.crates.IslandCrateCreatorStrategy;
import pl.subtelny.islands.module.crates.reward.open.OpenIslandCrateRewardFileParserStrategy;
import pl.subtelny.islands.module.skyblock.model.SkyblockIsland;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.file.FileUtil;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;
import pl.subtelny.utilities.reward.Reward;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class SkyblockIslandCrates implements IslandCrates {

    private final List<IslandCrateCreatorStrategy<CratePrototype>> crateCreatorStrategies;

    private final IslandModule<SkyblockIsland> islandModule;

    private final CrateService crateService;

    private final List<CrateKey> crateKeys = new ArrayList<>();

    public SkyblockIslandCrates(List<IslandCrateCreatorStrategy<CratePrototype>> crateCreatorStrategies,
                                IslandModule<SkyblockIsland> islandModule,
                                CrateService crateService) {
        this.crateCreatorStrategies = crateCreatorStrategies;
        this.islandModule = islandModule;
        this.crateService = crateService;
    }

    @Override
    public void openCrate(Player player, String crateName) {
        CrateKey crateKey = prepareCrateKey(crateName);
        Crate crate = crateService.getCrate(crateKey);
        crate.open(player);
    }

    @Override
    public void openCrate(Player player, IslandId islandId, String crateName) {
        CrateKey crateKey = prepareCrateKey(crateName);
        CratePrototype cratePrototype = crateService.getCratePrototype(crateKey);

        SkyblockIsland skyblockIsland = islandModule.findIsland(islandId)
                .orElseThrow(() -> ValidationException.of("skyblockIslandCrates.island_not_found"));

        Crate crate = getStrategy(cratePrototype.getCrateType())
                .map(strategy -> strategy.create(cratePrototype, skyblockIsland))
                .orElseGet(() -> crateService.getCrate(cratePrototype));
        crate.open(player);
    }

    @Override
    public void reload() {
        uninitializeCrates();
        initializeCrates();
    }

    public void copyResources() {
        String moduleName = islandModule.getIslandType().getInternal();
        FileUtil.copyFile(Islands.PLUGIN, "modules/" + moduleName + "/crates/config.yml");
        FileUtil.copyFile(Islands.PLUGIN, "modules/" + moduleName + "/crates/create.yml");
        FileUtil.copyFile(Islands.PLUGIN, "modules/" + moduleName + "/crates/search.yml");
    }

    public void initializeCrates() {
        File crates = new File(islandModule.getConfiguration().getModuleDir(), "crates");
        File[] files = crates.listFiles();
        List<CrateKey> crateKeys = Arrays.stream(files)
                .map(this::initializeCrate)
                .collect(Collectors.toList());
        this.crateKeys.addAll(crateKeys);
    }

    private CrateKey initializeCrate(File file) {
        List<PathAbstractFileParserStrategy<? extends Reward>> rewards = Collections.singletonList(new OpenIslandCrateRewardFileParserStrategy(file, this));
        String prefix = islandModule.getIslandType().getInternal();
        return crateService.initializeCrate(InitializeCrateRequest.of(file, Islands.PLUGIN, prefix, rewards));
    }

    private void uninitializeCrates() {
        crateService.unitializeCrates(crateKeys);
        crateKeys.clear();
    }

    private Optional<IslandCrateCreatorStrategy<CratePrototype>> getStrategy(CrateType crateType) {
        return crateCreatorStrategies.stream()
                .filter(islandCrateCreatorStrategy -> islandCrateCreatorStrategy.getType().equals(crateType))
                .findFirst();
    }

    private CrateKey prepareCrateKey(String name) {
        return CrateKey.of(islandModule.getIslandType().getInternal(), name);
    }

}
