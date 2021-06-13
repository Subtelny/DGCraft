package pl.subtelny.islands.module.skyblock.crates;

import org.bukkit.entity.Player;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateService;
import pl.subtelny.crate.api.creator.CrateCreateRequest;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.prototype.CratePrototypeLoadRequest;
import pl.subtelny.crate.api.prototype.CratePrototypeLoader;
import pl.subtelny.islands.Islands;
import pl.subtelny.utilities.file.FileUtil;

import java.io.File;

public abstract class ACrateManager implements CrateManager {

    private static final String FILE_PATH = "modules/%s/crates/%s";

    private final CrateService crateService;

    private final CratePrototypeLoader cratePrototypeLoader;

    private final String moduleName;

    protected ACrateManager(CrateService crateService, CratePrototypeLoader cratePrototypeLoader, String moduleName) {
        this.crateService = crateService;
        this.cratePrototypeLoader = cratePrototypeLoader;
        this.moduleName = moduleName;
    }

    protected CratePrototype loadCratePrototype(String fileName) {
        File file = FileUtil.copyFile(Islands.PLUGIN, String.format(FILE_PATH, moduleName, fileName));
        CratePrototypeLoadRequest request = CratePrototypeLoadRequest.builder(file)
                .build();
        return cratePrototypeLoader.loadCratePrototype(request);
    }

    protected void openCrate(Player player, CrateCreateRequest<CratePrototype> request) {
        Crate crate = crateService.createCrate(request);
        crate.open(player);
    }

}
