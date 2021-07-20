package pl.subtelny.islands.module.skyblock.crates;

import org.bukkit.entity.Player;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateService;
import pl.subtelny.crate.api.creator.CrateCreateRequest;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.prototype.CratePrototypeLoadRequest;
import pl.subtelny.crate.api.prototype.CratePrototypeLoader;
import pl.subtelny.islands.Islands;
import pl.subtelny.islands.api.module.component.CratesComponent;
import pl.subtelny.islands.module.crates.reward.open.OpenIslandCrateRewardFileParserStrategy;
import pl.subtelny.utilities.file.FileUtil;

import java.io.File;

public abstract class ACrateManager implements CrateManager {

    private static final String FILE_PATH = "modules/%s/crates/%s";

    private final CrateService crateService;

    private final CratePrototypeLoader cratePrototypeLoader;

    private final String moduleName;

    private final CratesComponent islandCrates;

    protected ACrateManager(CrateService crateService,
                            CratePrototypeLoader cratePrototypeLoader,
                            String moduleName,
                            CratesComponent islandCrates) {
        this.crateService = crateService;
        this.cratePrototypeLoader = cratePrototypeLoader;
        this.moduleName = moduleName;
        this.islandCrates = islandCrates;
    }

    protected CratePrototype loadCratePrototype(String fileName) {
        CratePrototypeLoadRequest request = prepareCratePrototypeBuilder(fileName)
                .build();
        return cratePrototypeLoader.loadCratePrototype(request);
    }

    protected CratePrototypeLoadRequest.Builder prepareCratePrototypeBuilder(String fileName) {
        File file = FileUtil.copyFile(Islands.PLUGIN, String.format(FILE_PATH, moduleName, fileName));
        return CratePrototypeLoadRequest.builder(file)
                .addRewardParser(new OpenIslandCrateRewardFileParserStrategy(file, islandCrates));
    }

    protected void openCrate(Player player, CrateCreateRequest<CratePrototype> request) {
        Crate crate = crateService.createCrate(request);
        crate.open(player);
    }

}
