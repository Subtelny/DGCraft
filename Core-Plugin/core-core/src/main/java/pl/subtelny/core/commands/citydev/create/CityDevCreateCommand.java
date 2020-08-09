package pl.subtelny.core.commands.citydev.create;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.api.city.CityId;
import pl.subtelny.core.city.create.CityCreateService;
import pl.subtelny.core.commands.citydev.CityDevCommand;
import pl.subtelny.core.configuration.CoreMessages;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.messages.Messages;

@PluginSubCommand(command = "create", mainCommand = CityDevCommand.class, permission = "create")
public class CityDevCreateCommand extends BaseCommand {

    private final static int CITY_ID_MIN_LENGTH = 2;

    private final CityCreateService cityCreateService;

    @Autowired
    public CityDevCreateCommand(CoreMessages messages, CityCreateService cityCreateService) {
        super(messages);
        this.cityCreateService = cityCreateService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Messages messages = getMessages();
        if (args.length == 0 || cityCreateService.hasSession(player)) {
            messages.sendTo(sender, "command.citydev.create.usage");
            return;
        }
        String rawCityId = args[0].toUpperCase();
        validateRawCityId(rawCityId);

        cityCreateService.createSession(player, CityId.of(rawCityId));
        messages.sendTo(sender, "command.citydev.create.commands");
    }

    private void validateRawCityId(String rawCityId) {
        if (rawCityId.length() < CITY_ID_MIN_LENGTH) {
            throw ValidationException.of("command.citydev.create.id_too_short", rawCityId);
        }
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
