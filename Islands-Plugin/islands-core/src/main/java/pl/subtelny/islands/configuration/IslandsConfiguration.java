package pl.subtelny.islands.configuration;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.islands.Islands;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.utilities.file.FileUtil;

import java.io.File;

public class IslandsConfiguration {

    public static IslandType ACTUAL_SEASON_ISLAND_TYPE;

    public static void init(Islands plugin) {
        File configFile = FileUtil.copyFile(plugin, "configuration.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        String rawActualSeasonModule = config.getString("configuration.current-season");
        ACTUAL_SEASON_ISLAND_TYPE = new IslandType(rawActualSeasonModule);
    }

}
