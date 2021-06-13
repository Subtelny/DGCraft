package pl.subtelny.crate.command;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.crate.initializer.CrateInitializer;
import pl.subtelny.crate.messages.CrateMessages;

@PluginSubCommand(command = "reload", permission = "dgcraft.crate.reload", mainCommand = CrateCommand.class)
public class CrateReloadCommand extends BaseCommand {

    private final CrateInitializer crateInitializer;

    @Autowired
    public CrateReloadCommand(CrateMessages crateMessages, CrateInitializer crateInitializer) {
        super(crateMessages);
        this.crateInitializer = crateInitializer;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        crateInitializer.reloadInitializedCratePrototypes();
        getMessages().sendTo(sender, "command.crate.reload.success");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return false;
    }
}


