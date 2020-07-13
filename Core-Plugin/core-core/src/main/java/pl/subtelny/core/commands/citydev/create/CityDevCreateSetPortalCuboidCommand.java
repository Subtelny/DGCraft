package pl.subtelny.core.commands.citydev.create;

import org.bukkit.entity.Player;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.city.create.CityCreateService;
import pl.subtelny.core.city.create.CityCreateSession;
import pl.subtelny.core.configuration.CoreMessages;
import pl.subtelny.core.cuboidselector.CuboidSelectService;
import pl.subtelny.utilities.messages.Messages;

@PluginSubCommand(command = "setportalcuboid", mainCommand = CityDevCreateCommand.class)
public class CityDevCreateSetPortalCuboidCommand extends CityDevCreateSubCommand {

    private final CityCreateService cityCreateService;

    private final CuboidSelectService cuboidSelectService;

    @Autowired
    public CityDevCreateSetPortalCuboidCommand(CoreMessages messages,
                                               CityCreateService cityCreateService,
                                               CuboidSelectService cuboidSelectService) {
        super(messages, cityCreateService);
        this.cityCreateService = cityCreateService;
        this.cuboidSelectService = cuboidSelectService;
    }

    @Override
    public void handleCommand(Player player, String[] args) {
        final CityCreateSession session = cityCreateService.getSession(player);
        Messages messages = getMessages();
        messages.sendTo(player, "command.citydev.create.setportalcuboid.usage");
        cuboidSelectService.createSession(player, cuboid -> {
            session.setTeleportCubiod(cuboid);
            messages.sendTo(player, "command.citydev.create.setportalcuboid.success");
        });
    }
}
