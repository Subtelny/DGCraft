package pl.subtelny.crate.commands.cratedev;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.crate.Crate;
import pl.subtelny.crate.cqrs.CrateCommandService;
import pl.subtelny.crate.initializer.CrateInitializer;
import pl.subtelny.crate.repository.CrateRepository;
import pl.subtelny.utilities.messages.Messages;

@PluginSubCommand(mainCommand = CrateDevCommand.class, command = "reload", permission = "dgcraft.cratedev.reload")
public class CrateDevReloadCommand extends BaseCommand {

    private final CrateCommandService crateCommandService;

    private final CrateInitializer crateInitializer;

    private final CrateRepository crateRepository;

    @Autowired
    public CrateDevReloadCommand(Messages messages,
                                 CrateCommandService crateCommandService,
                                 CrateInitializer crateInitializer,
                                 CrateRepository crateRepository) {
        super(messages);
        this.crateCommandService = crateCommandService;
        this.crateInitializer = crateInitializer;
        this.crateRepository = crateRepository;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        crateCommandService.closeAll(Crate.plugin);
        crateRepository.removeCrates(Crate.plugin);
        crateInitializer.activate();
        getMessages().sendTo(sender, "command.cratedev.reloaded");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return false;
    }
}
