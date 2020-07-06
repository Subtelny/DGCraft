package pl.subtelny.core.commands.coredev.setspawn;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.configuration.CoreMessages;
import pl.subtelny.core.configuration.Locations;

@PluginSubCommand(command = "global", mainCommand = CoreDevSetSpawnCommand.class)
public class SetSpawnCityCommand extends BaseCommand {

    private final CoreMessages messages;

    private final Locations locations;

    @Autowired
    public SetSpawnCityCommand(CoreMessages messages, Locations locations) {
        this.messages = messages;
        this.locations = locations;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Location location = player.getLocation();
        locations.set("global.spawn", location);
        messages.sendTo(sender, "coredev.setspawn.global.set");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }

}
