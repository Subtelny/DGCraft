package pl.subtelny.islands.commands.dev;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.islands.api.message.IslandMessages;
import pl.subtelny.islands.api.module.IslandModule;
import pl.subtelny.islands.api.module.IslandModules;

@PluginSubCommand(command = "reload", mainCommand = IslandDevCommand.class)
public class IslandDevReloadCommand extends BaseCommand {

    private final IslandModules islandModules;

    @Autowired
    public IslandDevReloadCommand(IslandMessages messages, IslandModules islandModules) {
        super(messages);
        this.islandModules = islandModules;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        islandModules.getIslandModules()
                .forEach(IslandModule::reloadAll);
        getMessages().sendTo(sender, "command.islanddev.reloaded");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return false;
    }
}
