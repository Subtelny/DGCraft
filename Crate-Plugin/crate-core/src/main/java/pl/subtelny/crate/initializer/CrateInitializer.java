package pl.subtelny.crate.initializer;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.service.CrateRegistrationService;
import pl.subtelny.crate.service.RegisterCratePrototypeRequest;

import java.io.File;
import java.util.Arrays;

@Component
public class CrateInitializer {

    private final CrateRegistrationService crateRegistrationService;

    @Autowired
    public CrateInitializer(CrateRegistrationService crateRegistrationService) {
        this.crateRegistrationService = crateRegistrationService;
    }

    public void initializeFromDir(File dir) {
        validateFile(dir);
        File[] files = dir.listFiles();
        if (files != null) {
            Arrays.stream(files).forEach(this::initializeFromFile);
        }
    }

    private void initializeFromFile(File file) {
        crateRegistrationService.registerCratePrototype(RegisterCratePrototypeRequest.of(file));
    }

    private void validateFile(File file) {
        if (!file.isDirectory()) {
            throw new IllegalStateException("File is not directory");
        }
    }

}
