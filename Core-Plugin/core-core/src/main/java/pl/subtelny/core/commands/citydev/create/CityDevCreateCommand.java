package pl.subtelny.core.commands.citydev.create;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.api.account.CityType;
import pl.subtelny.core.city.create.CityCreateService;
import pl.subtelny.core.commands.citydev.CityDevCommand;
import pl.subtelny.core.configuration.CoreMessages;

@PluginSubCommand(command = "create", mainCommand = CityDevCommand.class, permission = "create")
public class CityDevCreateCommand extends BaseCommand {

    private final CoreMessages messages;

    private final CityCreateService cityCreateService;

    @Autowired
    public CityDevCreateCommand(CoreMessages messages, CityCreateService cityCreateService) {
        this.messages = messages;
        this.cityCreateService = cityCreateService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0 || cityCreateService.hasSession(player)) {
            messages.sendTo(sender, "citydev.create.usage");
            return;
        }
        String rawCityType = args[0].toUpperCase();
        if (!CityType.isCityType(rawCityType)) {
            messages.sendTo(sender, "not_valid_city_type", rawCityType);
            return;
        }
        cityCreateService.createSession(player, CityType.of(rawCityType));
        messages.sendTo(sender, "citydev.create.commands");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
