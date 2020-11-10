package pl.subtelny.islands.commands.dev;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.islands.Islands;
import pl.subtelny.islands.configuration.IslandsConfiguration;
import pl.subtelny.islands.crate.IslandCrateService;
import pl.subtelny.islands.message.IslandMessages;

@PluginSubCommand(command = "reload", aliases = "reload", mainCommand = IslandDevCommand.class)
public class IslandDevReloadCommand extends BaseCommand {

    private final IslandCrateService islandCrateService;

    @Autowired
    public IslandDevReloadCommand(IslandMessages messages, IslandCrateService islandCrateService) {
        super(messages);
        this.islandCrateService = islandCrateService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        islandCrateService.reloadGuis();
        IslandsConfiguration.init(Islands.plugin);
        getMessages().sendTo(sender, "command.islanddev.reloaded");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return false;
    }
}
