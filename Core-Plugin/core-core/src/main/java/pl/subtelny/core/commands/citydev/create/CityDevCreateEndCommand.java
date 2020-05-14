package pl.subtelny.core.commands.citydev.create;

import org.bukkit.entity.Player;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.city.create.CityCreateService;
import pl.subtelny.core.configuration.Messages;
import pl.subtelny.utilities.MessageUtil;

@PluginSubCommand(command = "end", mainCommand = CityDevCreateCommand.class)
public class CityDevCreateEndCommand extends CityDevCreateSubCommand {

    private final Messages messages;

    private final CityCreateService cityCreateService;

    @Autowired
    public CityDevCreateEndCommand(Messages messages, CityCreateService cityCreateService) {
        super(cityCreateService);
        this.messages = messages;
        this.cityCreateService = cityCreateService;
    }

    @Override
    public void handleCommand(Player player, String[] args) {
        cityCreateService.completeSession(player);
        MessageUtil.message(player, messages.get("citydev.create.end.success"));
    }

}
