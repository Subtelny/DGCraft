package pl.subtelny.core.commands.citydev.create;

import org.bukkit.entity.Player;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.city.create.CityCreateService;
import pl.subtelny.core.city.create.CityCreateSession;
import pl.subtelny.core.configuration.Messages;
import pl.subtelny.utilities.MessageUtil;

@PluginSubCommand(command = "setspawn", mainCommand = CityDevCreateCommand.class)
public class CityDevCreateSetSpawnCommand extends CityDevCreateSubCommand {

    private final Messages messages;

    private final CityCreateService cityCreateService;

    @Autowired
    public CityDevCreateSetSpawnCommand(Messages messages, CityCreateService cityCreateService) {
        super(cityCreateService);
        this.messages = messages;
        this.cityCreateService = cityCreateService;
    }

    @Override
    public void handleCommand(Player player, String[] args) {
        CityCreateSession session = cityCreateService.getSession(player);
        session.setCitySpawn(player.getLocation());
        MessageUtil.message(player, messages.get("citydev.create.setspawn.success"));
    }

}
