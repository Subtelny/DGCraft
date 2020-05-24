package pl.subtelny.core.commands.coredev.setspawn;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.configuration.CoreMessages;
import pl.subtelny.core.configuration.Settings;

@PluginSubCommand(command = "global", mainCommand = CoreDevSetSpawnCommand.class)
public class SetSpawnCityCommand extends BaseCommand {

    private final CoreMessages messages;

    private final Settings settings;

    @Autowired
    public SetSpawnCityCommand(CoreMessages messages, Settings settings) {
        this.messages = messages;
        this.settings = settings;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Location location = player.getLocation();
        settings.set("global.spawn", Location.class, location);
        messages.sendTo(sender, "coredev.setspawn.global.set");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }

}
