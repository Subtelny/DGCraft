package pl.subtelny.islands.gui;

import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.islands.Islands;
import pl.subtelny.utilities.FileUtil;

import java.util.Arrays;
import java.util.List;

@Component
public class GUIsInitializer implements DependencyActivator {

    private static final String GUI_DIR_NAME = "guis";

    private static final List<String> GUI_FILES = Arrays.asList("create.yml");

    private final GUIService guiService;

    @Autowired
    public GUIsInitializer(GUIService guiService) {
        this.guiService = guiService;
    }

    @Override
    public void activate(Plugin plugin) {
        copyCrates();
        guiService.reloadGuis();
    }

    private void copyCrates() {
        GUI_FILES.stream()
                .map(this::createFileName)
                .forEach(fileName -> FileUtil.copyFile(Islands.plugin, fileName));
    }

    private String createFileName(String file) {
        return String.join("/", GUI_DIR_NAME, file);
    }

}
