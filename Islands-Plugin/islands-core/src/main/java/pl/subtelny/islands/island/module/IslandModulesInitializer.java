package pl.subtelny.islands.island.module;

import org.bukkit.plugin.Plugin;
import pl.subtelny.islands.island.Island;
import pl.subtelny.utilities.file.FileUtil;
import pl.subtelny.utilities.file.ObjectFileParserStrategy;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IslandModulesInitializer {

    private final List<IslandModuleCreator<Island>> islandModuleCreators;

    public IslandModulesInitializer(List<IslandModuleCreator<Island>> islandModuleCreators) {
        this.islandModuleCreators = islandModuleCreators;
    }

    public List<IslandModule<Island>> initializeModules(Plugin plugin) {
        File modulesDir = FileUtil.getFile(plugin, "modules");
        File[] moduleFiles = modulesDir.listFiles();
        if (moduleFiles == null) {
            throw new IllegalStateException("Not found any modules");
        }
        return Arrays.stream(moduleFiles)
                .filter(File::isDirectory)
                .map(this::initializeModule)
                .collect(Collectors.toList());
    }

    private IslandModule<Island> initializeModule(File moduleDirectory) {
        File configFile = new File(moduleDirectory, "configuration.yml");
        if (!configFile.exists()) {
            throw new IllegalStateException("Not found configuration file for module " + moduleDirectory.getName());
        }

        String moduleType = new ObjectFileParserStrategy<String>(configFile).load("module.type");
        IslandModuleInitable<Island> module = createModule(moduleDirectory, moduleType);
        module.initialize();
        return module;
    }

    private IslandModuleInitable<Island> createModule(File moduleDirectory, String moduleType) {
        try {
            return getProperIslandModuleCreatorFor(moduleType)
                    .createModule(moduleDirectory);
        } catch (IllegalStateException | IllegalArgumentException e) {
            throw new IllegalStateException("Cannot create module " + moduleType + " on path " + moduleDirectory.getPath(), e);
        }
    }


    private IslandModuleCreator<Island> getProperIslandModuleCreatorFor(String type) {
        return islandModuleCreators.stream()
                .filter(islandModuleCreator -> islandModuleCreator.getModuleType().equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Not found module creator for type " + type));
    }

}
