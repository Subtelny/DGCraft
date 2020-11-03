package pl.subtelny.islands.commands.dev;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.islands.gui.GUIService;
import pl.subtelny.islands.message.IslandMessages;
import pl.subtelny.islands.skyblockisland.settings.SkyblockIslandSettings;

@PluginSubCommand(command = "reload", aliases = "reload", mainCommand = IslandDevCommand.class)
public class IslandDevReloadCommand extends BaseCommand {

    private final SkyblockIslandSettings settings;

    private final GUIService guiService;

    @Autowired
    public IslandDevReloadCommand(IslandMessages messages, SkyblockIslandSettings settings, GUIService guiService) {
        super(messages);
        this.settings = settings;
        this.guiService = guiService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        settings.initConfig();
        guiService.reloadGuis();
        getMessages().sendTo(sender, "command.islanddev.reloaded");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return false;
    }
}
