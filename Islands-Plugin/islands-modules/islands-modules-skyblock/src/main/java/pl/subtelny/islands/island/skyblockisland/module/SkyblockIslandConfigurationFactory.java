package pl.subtelny.islands.island.skyblockisland.module;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.islands.island.configuration.IslandExtendConfiguration;
import pl.subtelny.islands.island.configuration.IslandExtendLevel;
import pl.subtelny.islands.island.configuration.IslandExtendLevelFileParserStrategy;
import pl.subtelny.islands.island.skyblockisland.configuration.SkyblockIslandConfiguration;
import pl.subtelny.utilities.ConfigUtil;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SkyblockIslandConfigurationFactory {

    private final Economy economy;

    public SkyblockIslandConfigurationFactory(Economy economy) {
        this.economy = economy;
    }

    public SkyblockIslandConfiguration createConfiguration(File moduleDir) {
        File file = new File(moduleDir.getPath(), "configuration.yml");
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        String worldName = configuration.getString("configuration.world");
        int defaultSize = configuration.getInt("configuration.default-size");
        int spaceBetweenIslands = configuration.getInt("configuration.space-between-islands");
        int schematicHeight = configuration.getInt("configuration.schematic-height");

        String schematicFileName = configuration.getString("configuration.schematic-file-name");
        String schematicFilePath = getSchematicFilePath(moduleDir, schematicFileName);
        boolean createEnabled = configuration.getBoolean("configuration.create-enabled");

        IslandExtendConfiguration extendConfiguration = createExtendConfiguration(configuration, file, economy);
        return new SkyblockIslandConfiguration(worldName, createEnabled, extendConfiguration, defaultSize, spaceBetweenIslands, schematicFilePath, schematicHeight, moduleDir);
    }

    private String getSchematicFilePath(File moduleDir, String schematicFileName) {
        File schematics = new File(moduleDir, "schematics");
        if (!schematics.exists()) {
            schematics.mkdir();
        }
        return new File(schematics, schematicFileName).getPath();
    }

    private IslandExtendConfiguration createExtendConfiguration(YamlConfiguration configuration, File file, Economy economy) {
        String path = "configuration.extends";
        Set<String> paths = ConfigUtil.getSectionKeys(configuration, path)
                .stream()
                .flatMap(Collection::stream)
                .map(s -> String.join(".", path, s))
                .collect(Collectors.toSet());

        List<IslandExtendLevel> extendLevels = loadIslandExtendLevels(configuration, file, economy, paths);
        return new IslandExtendConfiguration(extendLevels);
    }

    private List<IslandExtendLevel> loadIslandExtendLevels(YamlConfiguration configuration, File file, Economy economy, Set<String> paths) {
        IslandExtendLevelFileParserStrategy strategy = new IslandExtendLevelFileParserStrategy(configuration, file, economy);
        return paths.stream()
                .map(strategy::load)
                .collect(Collectors.toList());
    }

}
