package pl.subtelny.core.commands.citydev.create;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.api.account.CityType;
import pl.subtelny.core.city.create.CityCreateService;
import pl.subtelny.core.commands.citydev.CityDevCommand;
import pl.subtelny.core.configuration.Messages;
import pl.subtelny.utilities.MessageUtil;

@PluginSubCommand(command = "create", mainCommand = CityDevCommand.class)
public class CityDevCreateCommand extends BaseCommand {

    private final Messages messages;

    private final CityCreateService cityCreateService;

    @Autowired
    public CityDevCreateCommand(Messages messages, CityCreateService cityCreateService) {
        this.messages = messages;
        this.cityCreateService = cityCreateService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        if (args.length == 0 || cityCreateService.hasSession((Player) sender)) {
            MessageUtil.message(sender, messages.get("citydev.create.usage"));
            return;
        }
        String rawCityType = args[0].toUpperCase();
        if (!CityType.isCityType(rawCityType)) {
            MessageUtil.message(sender, String.format(messages.get("not_valid_city_type"), rawCityType));
            return;
        }
        cityCreateService.createSession((Player) sender, CityType.of(rawCityType));
        MessageUtil.message(sender, messages.get("citydev.create.commands"));
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
