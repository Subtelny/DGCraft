package pl.subtelny.islands.skyblockisland.extendcuboid.settings;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.repository.islander.IslanderRepository;
import pl.subtelny.islands.skyblockisland.extendcuboid.SkyblockIslandExtendCuboidLevel;
import pl.subtelny.utilities.FileUtil;
import pl.subtelny.utilities.file.ObjectFileParserStrategy;

import java.io.File;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class SkyblockIslandSettings {

    private static final String CONFIG_FILE_NAME = "skyblockIsland.yml";

    private World BASIC_ISLAND_WORLD;

    private int BASIC_SPACE_BETWEEN_ISLANDS;

    private int BASIC_ISLAND_SIZE;

    private Map<Integer, SkyblockIslandExtendCuboidLevel> EXTEND_CUBOID_LEVELS = new HashMap<>();

    public SkyblockIslandSettings() {
    }

    public void initConfig(Plugin plugin, Economy economy, IslanderRepository islanderRepository) {
        File configFile = FileUtil.copyFile(plugin, CONFIG_FILE_NAME);
        BASIC_ISLAND_WORLD = Bukkit.getWorld(new ObjectFileParserStrategy<String>(configFile).load("basic.island_world"));
        BASIC_SPACE_BETWEEN_ISLANDS = new ObjectFileParserStrategy<Integer>(configFile).load("basic.space_between_islands");
        BASIC_ISLAND_SIZE = new ObjectFileParserStrategy<Integer>(configFile).load("basic.island_size");
        EXTEND_CUBOID_LEVELS = new SkyblockIslandExtendCuboidSettingsLoader(configFile, economy, islanderRepository).loadExtendLevels();
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
                .max(Comparator.comparingInt(SkyblockIslandExtendCuboidLevel::getIslandSize))
                .map(SkyblockIslandExtendCuboidLevel::getIslandSize)
                .orElse(BASIC_ISLAND_SIZE);
    }

    public int getMaxExtendCuboidLevel() {
        return EXTEND_CUBOID_LEVELS.size();
    }

    public Map<Integer, SkyblockIslandExtendCuboidLevel> getExtendCuboidLevels() {
        return EXTEND_CUBOID_LEVELS;
    }

    public Optional<SkyblockIslandExtendCuboidLevel> getExtendCuboidLevel(int level) {
        return Optional.ofNullable(EXTEND_CUBOID_LEVELS.get(level));
    }

}
