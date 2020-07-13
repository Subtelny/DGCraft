package pl.subtelny.core.commands.citydev.create;

import org.bukkit.entity.Player;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.city.create.CityCreateService;
import pl.subtelny.core.city.create.CityCreateSession;
import pl.subtelny.core.configuration.CoreMessages;

@PluginSubCommand(command = "setportaltarget", mainCommand = CityDevCreateCommand.class)
public class CityDevCreateSetPortalTargetCommand extends CityDevCreateSubCommand {

    private final CityCreateService cityCreateService;

    @Autowired
    public CityDevCreateSetPortalTargetCommand(CoreMessages messages, CityCreateService cityCreateService) {
        super(messages, cityCreateService);
        this.cityCreateService = cityCreateService;
    }

    @Override
    public void handleCommand(Player player, String[] args) {
        CityCreateSession session = cityCreateService.getSession(player);
        session.setTeleportTarget(player.getLocation());
        getMessages().sendTo(player, "citydev.create.setportaltarget.success");
    }

}
