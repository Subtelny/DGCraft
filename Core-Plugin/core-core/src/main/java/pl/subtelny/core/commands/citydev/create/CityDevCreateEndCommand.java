package pl.subtelny.core.commands.citydev.create;

import org.bukkit.entity.Player;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.city.create.CityCreateService;
import pl.subtelny.core.configuration.CoreMessages;

@PluginSubCommand(command = "end", mainCommand = CityDevCreateCommand.class)
public class CityDevCreateEndCommand extends CityDevCreateSubCommand {

    private final CoreMessages messages;

    private final CityCreateService cityCreateService;

    @Autowired
    public CityDevCreateEndCommand(CoreMessages messages, CityCreateService cityCreateService) {
        super(cityCreateService);
        this.messages = messages;
        this.cityCreateService = cityCreateService;
    }

    @Override
    public void handleCommand(Player player, String[] args) {
        cityCreateService.completeSession(player);
        messages.sendTo(player, "citydev.create.end.success");
    }

}
