package pl.subtelny.gui.crate.reward;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import pl.subtelny.gui.GUI;
import pl.subtelny.gui.api.crate.model.CrateId;
import pl.subtelny.gui.api.crate.session.PlayerCrateSessionService;
import pl.subtelny.utilities.Saveable;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;

import java.io.File;

public class OpenGuiRewardFileParserStrategy extends PathAbstractFileParserStrategy<OpenGuiReward> {

    private final Plugin plugin;

    private final PlayerCrateSessionService playerCrateSessionService;

    public OpenGuiRewardFileParserStrategy(YamlConfiguration configuration, File file, Plugin plugin, PlayerCrateSessionService playerCrateSessionService) {
        super(configuration, file);
        this.plugin = plugin;
        this.playerCrateSessionService = playerCrateSessionService;
    }

    @Override
    public OpenGuiReward load(String path) {
        String rawCrateId = configuration.getString(path + "." + getPath());
        boolean internalPlugin = configuration.getBoolean(path + ".internal-plugin");
        Plugin plugin = internalPlugin ? this.plugin : GUI.plugin;
        CrateId crateId = CrateId.of(plugin, rawCrateId);
        return new OpenGuiReward(crateId, playerCrateSessionService);
    }

    @Override
    public Saveable set(String path, OpenGuiReward value) {
        throw new UnsupportedOperationException("Saving OpenGUI Reward is not implemented yet");
    }

    @Override
    public String getPath() {
        return "open-gui";
    }
}
