package pl.subtelny.gui.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.gui.GUI;
import pl.subtelny.gui.api.crate.model.CrateId;
import pl.subtelny.gui.api.crate.session.PlayerCrateSessionService;
import pl.subtelny.gui.messages.CrateMessages;

@PluginCommand(command = "crate")
public class CrateCommand extends BaseCommand {

    private final PlayerCrateSessionService sessionService;

    @Autowired
    public CrateCommand(CrateMessages messages, PlayerCrateSessionService sessionService) {
        super(messages);
        this.sessionService = sessionService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            getMessages().sendTo(sender, "command.crate.usage");
            return;
        }
        Player player = (Player) sender;
        sessionService.openSession(player, CrateId.of(GUI.plugin, args[0]));
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }

}
