package pl.subtelny.core.commands.citydev.create;

import org.bukkit.entity.Player;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.city.create.CityCreateService;
import pl.subtelny.core.city.create.CityCreateSession;
import pl.subtelny.core.configuration.Messages;
import pl.subtelny.core.cuboidselector.CuboidSelectService;
import pl.subtelny.utilities.MessageUtil;

@PluginSubCommand(command = "setcuboid", mainCommand = CityDevCreateCommand.class)
public class CityDevCreateSetCuboidCommand extends CityDevCreateSubCommand {

    private final Messages messages;

    private final CityCreateService cityCreateService;

    private final CuboidSelectService cuboidSelectService;

    @Autowired
    public CityDevCreateSetCuboidCommand(Messages messages,
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
        final String message = messages.get("citydev.create.setcuboid.success");
        MessageUtil.message(player, messages.get("citydev.create.setcuboid.usage"));
        cuboidSelectService.createSession(player, cuboid -> {
            session.setCityCuboid(cuboid);
            MessageUtil.message(player, message);
        });
    }
}
