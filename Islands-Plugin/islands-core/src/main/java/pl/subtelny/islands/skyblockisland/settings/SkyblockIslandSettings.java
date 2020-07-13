package pl.subtelny.islands.skyblockisland.settings;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.islander.repository.IslanderRepository;
import pl.subtelny.islands.skyblockisland.extendcuboid.settings.SkyblockIslandExtendCuboidOption;
import pl.subtelny.islands.skyblockisland.extendcuboid.settings.SkyblockIslandExtendCuboidOptionsLoader;
import pl.subtelny.islands.skyblockisland.schematic.SkyblockIslandSchematicOption;
import pl.subtelny.islands.skyblockisland.schematic.SkyblockIslandSchematicOptionLoader;
import pl.subtelny.utilities.FileUtil;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.file.ObjectFileParserStrategy;

import java.io.File;
import java.util.*;

@Component
public class SkyblockIslandSettings {

    private static final String CONFIG_FILE_NAME = "skyblockIsland.yml";

    private World BASIC_ISLAND_WORLD;

    private int BASIC_SPACE_BETWEEN_ISLANDS;

    private int BASIC_ISLAND_SIZE;

    private Map<Integer, SkyblockIslandExtendCuboidOption> EXTEND_CUBOID_LEVELS = new HashMap<>();

    private Map<String, SkyblockIslandSchematicOption> SCHEMATIC_OPTIONS = new HashMap<>();

    public void initConfig(Plugin plugin, Economy economy, IslanderRepository islanderRepository) {
        File configFile = FileUtil.copyFile(plugin, CONFIG_FILE_NAME);
        BASIC_ISLAND_WORLD = Bukkit.getWorld(new ObjectFileParserStrategy<String>(configFile).load("basic.island_world"));
        BASIC_SPACE_BETWEEN_ISLANDS = new ObjectFileParserStrategy<Integer>(configFile).load("basic.space_between_islands");
        BASIC_ISLAND_SIZE = new ObjectFileParserStrategy<Integer>(configFile).load("basic.island_size");
        EXTEND_CUBOID_LEVELS = new SkyblockIslandExtendCuboidOptionsLoader(configFile, economy, islanderRepository).loadExtendLevels();
        SCHEMATIC_OPTIONS = new SkyblockIslandSchematicOptionLoader(configFile, economy).loadOptions();
    }

    public World getWorld() {
        return BASIC_ISLAND_WORLD;
    }

    public int getSpaceBetweenIslands() {
        return BASIC_SPACE_BETWEEN_ISLANDS;
    }

    public int getIslandSize() {
        return BASIC_ISLAND_SIZE;
    }

    public int getMaxIslandSize() {
        return EXTEND_CUBOID_LEVELS.values().stream()
                .max(Comparator.comparingInt(SkyblockIslandExtendCuboidOption::getIslandSize))
                .map(SkyblockIslandExtendCuboidOption::getIslandSize)
                .orElse(BASIC_ISLAND_SIZE);
    }

    public int getMaxExtendCuboidLevel() {
        return EXTEND_CUBOID_LEVELS.size();
    }

    public Map<Integer, SkyblockIslandExtendCuboidOption> getExtendCuboidLevels() {
        return EXTEND_CUBOID_LEVELS;
    }

    public Optional<SkyblockIslandExtendCuboidOption> getExtendCuboidLevel(int level) {
        return Optional.ofNullable(EXTEND_CUBOID_LEVELS.get(level));
    }

    public Optional<SkyblockIslandSchematicOption> getSchematicOption(String schematicName) {
        return Optional.ofNullable(SCHEMATIC_OPTIONS.get(schematicName));
    }

    public Map<String, SkyblockIslandSchematicOption> getSchematicOptions() {
        return new HashMap<>(SCHEMATIC_OPTIONS);
    }

    public SkyblockIslandSchematicOption getDefaultSchematicOption() {
        return SCHEMATIC_OPTIONS.values().stream()
                .filter(SkyblockIslandSchematicOption::isDefaultSchematic)
                .findAny()
                .orElseThrow(() -> ValidationException.of("settings.skyblockIsland.not_found_default_schematic"));
    }

}
