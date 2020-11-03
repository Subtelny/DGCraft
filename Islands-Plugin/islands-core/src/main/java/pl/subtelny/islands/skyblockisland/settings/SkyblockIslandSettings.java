package pl.subtelny.islands.skyblockisland.settings;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.World;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.economy.EconomyProvider;
import pl.subtelny.islands.Islands;
import pl.subtelny.islands.islander.repository.IslanderRepository;
import pl.subtelny.islands.skyblockisland.extendcuboid.settings.SkyblockIslandExtendCuboidOption;
import pl.subtelny.islands.skyblockisland.extendcuboid.settings.SkyblockIslandExtendCuboidOptionsLoader;
import pl.subtelny.islands.skyblockisland.schematic.SkyblockIslandSchematicOption;
import pl.subtelny.islands.skyblockisland.schematic.SkyblockIslandSchematicOptionLoader;
import pl.subtelny.utilities.file.FileUtil;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.file.ObjectFileParserStrategy;

import java.io.File;
import java.util.*;

@Component
public class SkyblockIslandSettings {

    private static final String CONFIG_FILE_NAME = "skyblockIsland.yml";

    private final IslanderRepository islanderRepository;

    private final EconomyProvider economyProvider;

    private World BASIC_ISLAND_WORLD;

    private int BASIC_SPACE_BETWEEN_ISLANDS;

    private int BASIC_ISLAND_SIZE;

    private String CREATOR_GUI;

    private Map<Integer, SkyblockIslandExtendCuboidOption> EXTEND_CUBOID_LEVELS = new HashMap<>();

    private Map<String, SkyblockIslandSchematicOption> SCHEMATIC_OPTIONS = new HashMap<>();

    @Autowired
    public SkyblockIslandSettings(IslanderRepository islanderRepository, EconomyProvider economyProvider) {
        this.islanderRepository = islanderRepository;
        this.economyProvider = economyProvider;
    }

    public void initConfig() {
        File configFile = FileUtil.copyFile(Islands.plugin, CONFIG_FILE_NAME);
        FileUtil.getFile(Islands.plugin, "schematics").mkdirs();
        Economy economy = economyProvider.getEconomy();
        BASIC_ISLAND_WORLD = Bukkit.getWorld(new ObjectFileParserStrategy<String>(configFile).load("basic.island_world"));
        BASIC_SPACE_BETWEEN_ISLANDS = new ObjectFileParserStrategy<Integer>(configFile).load("basic.space_between_islands");
        BASIC_ISLAND_SIZE = new ObjectFileParserStrategy<Integer>(configFile).load("basic.island_size");
        EXTEND_CUBOID_LEVELS = new SkyblockIslandExtendCuboidOptionsLoader(configFile, economy, islanderRepository).loadExtendLevels();
        SCHEMATIC_OPTIONS = new SkyblockIslandSchematicOptionLoader(configFile, economy).loadOptions();
        CREATOR_GUI = new ObjectFileParserStrategy<String>(configFile).load("command.create.gui");
    }

    public World getWorld() {
        return BASIC_ISLAND_WORLD;
    }

    public int getSpaceBetweenIslands() {
        return BASIC_SPACE_BETWEEN_ISLANDS;
    }

    public int getBasicIslandSize() {
        return BASIC_ISLAND_SIZE;
    }

    public String getCreatorGui() {
        return CREATOR_GUI;
    }

    public int getMaxIslandSizeWithSpaceBetween() {
        return EXTEND_CUBOID_LEVELS.values().stream()
                .max(Comparator.comparingInt(SkyblockIslandExtendCuboidOption::getIslandSize))
                .map(SkyblockIslandExtendCuboidOption::getIslandSize)
                .orElse(BASIC_ISLAND_SIZE) + BASIC_SPACE_BETWEEN_ISLANDS;
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
