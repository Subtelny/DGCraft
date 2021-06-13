package pl.subtelny.crate.initializer;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.CrateCore;
import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.api.CrateRegistration;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.prototype.CratePrototypeLoadRequest;
import pl.subtelny.crate.api.prototype.CratePrototypeLoader;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.file.FileUtil;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CrateInitializer {

    private final CratePrototypeLoader cratePrototypeLoader;

    private final CrateRegistration crateRegistration;

    private final Set<CrateId> crateIds = new HashSet<>();

    @Autowired
    public CrateInitializer(CratePrototypeLoader cratePrototypeLoader, CrateRegistration crateRegistration) {
        this.cratePrototypeLoader = cratePrototypeLoader;
        this.crateRegistration = crateRegistration;
    }

    public void initializeCratePrototypes() {
        Validation.isTrue(crateIds.isEmpty(), "CratePrototypes already initialized");

        File cratesDir = copyResources();
        File[] files = cratesDir.listFiles();
        if (files != null) {
            List<CrateId> crateIds = Arrays.stream(files)
                    .map(this::loadCratePrototypeFromFile)
                    .collect(Collectors.toList());
            this.crateIds.addAll(crateIds);
        }
    }

    public void reloadInitializedCratePrototypes() {
        crateIds.forEach(crateRegistration::unregisterCratePrototype);
        crateIds.clear();
        initializeCratePrototypes();
    }

    private CrateId loadCratePrototypeFromFile(File file) {
        CratePrototypeLoadRequest request = CratePrototypeLoadRequest.builder(file).build();
        CratePrototype cratePrototype = cratePrototypeLoader.loadCratePrototype(request);
        crateRegistration.registerCratePrototype(cratePrototype);
        return cratePrototype.getCrateId();
    }

    private File copyResources() {
        File cratesDir = new File("crates");
        if (!cratesDir.exists()) {
            cratesDir.mkdirs();
            FileUtil.copyFile(CrateCore.PLUGIN, "crates/example.yml");
        }
        return cratesDir;
    }

}
