package pl.subtelny.utilities;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public final class ColorUtil {

    public static List<String> color(List<String> list) {
        return list.stream()
                .map(ColorUtil::color)
                .collect(Collectors.toList());

    }

    public static String color(String value) {
        return ChatColor.translateAlternateColorCodes('&', value);
    }

}
