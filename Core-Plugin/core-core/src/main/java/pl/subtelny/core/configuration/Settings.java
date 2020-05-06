package pl.subtelny.core.configuration;

import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.utilities.FileUtil;

import java.io.File;

@Component
public class Settings {

    private static final String CONFIG_FILE_NAME = "config.yml";

    public Settings() {
    }

    public void initSettings(Plugin plugin) {
        File file = FileUtil.copyFile(plugin, CONFIG_FILE_NAME);
    }

}
