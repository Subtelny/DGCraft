package pl.subtelny.gui.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.gui.GUI;
import pl.subtelny.gui.api.crate.model.Crate;
import pl.subtelny.gui.api.crate.model.CrateId;
import pl.subtelny.gui.api.crate.repository.CrateRepository;
import pl.subtelny.gui.messages.CrateMessages;
import pl.subtelny.gui.api.crate.session.PlayerCrateSessionService;
import pl.subtelny.utilities.Validation;

import java.util.Optional;

@PluginCommand(command = "crate")
public class CrateCommand extends BaseCommand {

    private final CrateRepository crateRepository;

    private final PlayerCrateSessionService sessionService;

    @Autowired
    public CrateCommand(CrateMessages messages, CrateRepository crateRepository, PlayerCrateSessionService sessionService) {
        super(messages);
        this.crateRepository = crateRepository;
        this.sessionService = sessionService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            getMessages().sendTo(sender, "command.crate.usage");
            return;
        }
        Player player = (Player) sender;
        Optional<Crate> crateOpt = crateRepository.findCrate(CrateId.of(GUI.plugin, args[0]));
        crateOpt.ifPresentOrElse(
                crate -> {
                    boolean hasPermission = crate.getPermission().map(player::hasPermission).orElse(true);
                    Validation.isTrue(hasPermission, "command.crate.no_permissions");
                    sessionService.openSession(player, crate);
                },
                () -> getMessages().sendTo(player, "command.crate.crate_not_found", args[0]));
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }

}
