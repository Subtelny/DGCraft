package pl.subtelny.gui.crate.repository;

import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.gui.api.crate.repository.CrateRepository;
import pl.subtelny.gui.crate.settings.CratesLoader;
import pl.subtelny.utilities.FileUtil;

import java.io.File;
import java.io.InputStream;

@Component
public class CratesRepositoryInitializer implements DependencyActivator {

    private final CratesLoader crateLoader;

    private final CrateRepository crateRepository;

    @Autowired
    public CratesRepositoryInitializer(CratesLoader crateLoader, CrateRepository crateRepository) {
        this.crateLoader = crateLoader;
        this.crateRepository = crateRepository;
    }

    @Override
    public void activate(Plugin plugin) {
        File dataFolder = new File(plugin.getDataFolder(), "guis");
        InputStream resource = plugin.getResource("example.yml");
        FileUtil.copyFile(dataFolder, resource, "example.yml");
        crateLoader.loadAllCratesFromDirectory(dataFolder).forEach(crateRepository::registerCrate);
    }

}
