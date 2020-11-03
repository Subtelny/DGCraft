package pl.subtelny.core.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.api.confirmation.ConfirmContextId;
import pl.subtelny.core.api.confirmation.ConfirmationService;
import pl.subtelny.core.configuration.CoreMessages;
import pl.subtelny.utilities.Validation;

@PluginCommand(
        command = "confirm",
        aliases = {
                "akceptuj"
        }
)
public class ConfirmCommand extends BaseCommand {

    private final ConfirmationService confirmationService;

    @Autowired
    public ConfirmCommand(CoreMessages messages, ConfirmationService confirmationService) {
        super(messages);
        this.confirmationService = confirmationService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Validation.isTrue(args.length == 1, "command.confirm.usage");
        confirm(player, args[0]);
    }

    private void confirm(Player player, String arg) {
        ConfirmContextId confirmContextId = ConfirmContextId.of(arg);
        confirmationService.confirm(player, confirmContextId);
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
