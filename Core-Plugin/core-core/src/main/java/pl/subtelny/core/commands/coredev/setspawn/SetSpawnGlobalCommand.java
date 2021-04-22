package pl.subtelny.core.commands.coredev.setspawn;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.commands.coredev.CoreDevCommand;
import pl.subtelny.core.configuration.CoreMessages;
import pl.subtelny.core.configuration.Locations;

@PluginSubCommand(command = "setspawn", mainCommand = CoreDevCommand.class)
public class SetSpawnGlobalCommand extends BaseCommand {

    private final Locations locations;

    @Autowired
    public SetSpawnGlobalCommand(CoreMessages messages, Locations locations) {
        super(messages);
        this.locations = locations;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        saveCityLocation((Player) sender);
        getMessages().sendTo(sender, "command.coredev.setspawn.global.changed");
    }

    public void saveCityLocation(Player sender) {
        Location location = sender.getLocation();
        locations.set("global.spawn", location);
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }

}
