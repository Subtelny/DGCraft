package pl.subtelny.core.commands.citydev.create;

import org.bukkit.entity.Player;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.city.create.CityCreateService;
import pl.subtelny.core.city.create.CityCreateSession;
import pl.subtelny.core.configuration.CoreMessages;
import pl.subtelny.core.cuboidselector.CuboidSelectService;

@PluginSubCommand(command = "setcuboid", mainCommand = CityDevCreateCommand.class)
public class CityDevCreateSetCuboidCommand extends CityDevCreateSubCommand {

    private final CoreMessages messages;

    private final CityCreateService cityCreateService;

    private final CuboidSelectService cuboidSelectService;

    @Autowired
    public CityDevCreateSetCuboidCommand(CoreMessages messages,
                                         CityCreateService cityCreateService,
                                         CuboidSelectService cuboidSelectService) {
        super(cityCreateService);
        this.messages = messages;
        this.cityCreateService = cityCreateService;
        this.cuboidSelectService = cuboidSelectService;
    }

    @Override
    public void handleCommand(Player player, String[] args) {
        final CityCreateSession session = cityCreateService.getSession(player);
        messages.sendTo(player, "citydev.create.setcuboid.usage");
        cuboidSelectService.createSession(player, cuboid -> {
            session.setCityCuboid(cuboid);
            messages.sendTo(player, "citydev.create.setcuboid.success");
        });
    }
}
