package pl.subtelny.crate.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.crate.Crate;
import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.api.query.CrateQueryService;
import pl.subtelny.crate.api.query.request.GetCrateRequest;
import pl.subtelny.crate.messages.CrateMessages;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.messages.Messages;

@PluginCommand(command = "crate")
public class CrateCommand extends BaseCommand {

    private final CrateQueryService crateQueryService;

    @Autowired
    public CrateCommand(CrateMessages messages, CrateQueryService crateQueryService) {
        super(messages);
        this.crateQueryService = crateQueryService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            throw ValidationException.of("command.crate.usage");
        }
        CrateId crateId = new CrateId(Crate.plugin, args[0]);
        GetCrateRequest request = GetCrateRequest.of(crateId);
        crateQueryService.getCrate(request)
                .open(player);
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
