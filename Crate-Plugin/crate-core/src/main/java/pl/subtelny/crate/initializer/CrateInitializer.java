package pl.subtelny.crate.initializer;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.crate.Crate;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.query.CrateQueryService;
import pl.subtelny.crate.api.query.request.GetCratePrototypeRequest;
import pl.subtelny.crate.repository.CrateRepository;
import pl.subtelny.utilities.file.FileUtil;

import java.io.File;

@Component
public class CrateInitializer implements DependencyActivator {

    private final CrateQueryService crateQueryService;

    private final CrateRepository crateRepository;

    @Autowired
    public CrateInitializer(CrateQueryService crateQueryService, CrateRepository crateRepository) {
        this.crateQueryService = crateQueryService;
        this.crateRepository = crateRepository;
    }

    @Override
    public void activate() {
        File cratesDir = FileUtil.getFile(Crate.plugin, "crates");
        if (!cratesDir.exists()) {
            FileUtil.copyFile(Crate.plugin, "crates/example.yml");
        }

        File[] files = cratesDir.listFiles();
        if (files != null) {
            for (File file : files) {
                initializeCrate(file);
            }
        }
    }

    private void initializeCrate(File file) {
        if (file.getName().contains(".yml")) {
            GetCratePrototypeRequest request = GetCratePrototypeRequest.builder(Crate.plugin, file).build();
            CratePrototype cratePrototype = crateQueryService.getCratePrototype(request);
            crateRepository.addCratePrototype(cratePrototype.getCrateId(), cratePrototype);
        }
    }
}
