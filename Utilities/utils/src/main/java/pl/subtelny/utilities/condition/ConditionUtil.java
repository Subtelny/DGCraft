package pl.subtelny.utilities.condition;

import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static pl.subtelny.utilities.ConfigUtil.getSectionKeys;

public final class ConditionUtil {

    public static List<String> findConditionPaths(YamlConfiguration configuration, String path) {
        return findTypeConditionPaths(configuration, "CONDITION", path);
    }

    public static List<String> findCostConditionPaths(YamlConfiguration configuration, String path) {
        return findTypeConditionPaths(configuration, "COST", path);
    }

    public static List<String> findTypeConditionPaths(YamlConfiguration configuration, String type, String path) {
        return getSectionKeys(configuration, path)
                .map(keys -> keys.stream()
                        .map(key -> path + "." + key)
                        .filter(conditionPath -> type.equals(configuration.getString(conditionPath + ".condition")))
                        .collect(Collectors.toList()))
                .orElse(new ArrayList<>());
    }

}
