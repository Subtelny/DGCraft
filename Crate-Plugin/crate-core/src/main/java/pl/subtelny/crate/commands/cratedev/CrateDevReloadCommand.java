package pl.subtelny.crate.commands.cratedev;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.crate.Crate;
import pl.subtelny.crate.api.command.CrateCommandService;
import pl.subtelny.crate.initializer.CrateInitializer;
import pl.subtelny.utilities.messages.Messages;

@PluginSubCommand(mainCommand = CrateDevCommand.class, command = "reload", permission = "dgcraft.cratedev.reload")
public class CrateDevReloadCommand extends BaseCommand {

    private final CrateCommandService crateCommandService;

    private final CrateInitializer crateInitializer;

    @Autowired
    public CrateDevReloadCommand(Messages messages,
                                 CrateCommandService crateCommandService,
                                 CrateInitializer crateInitializer) {
        super(messages);
        this.crateCommandService = crateCommandService;
        this.crateInitializer = crateInitializer;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        crateCommandService.unregisterAll(Crate.plugin);
        crateInitializer.activate();
        getMessages().sendTo(sender, "command.cratedev.reloaded");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return false;
    }
}
