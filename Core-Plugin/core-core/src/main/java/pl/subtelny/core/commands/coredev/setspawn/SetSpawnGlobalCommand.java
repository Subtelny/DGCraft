package pl.subtelny.core.commands.coredev.setspawn;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.api.account.CityType;
import pl.subtelny.core.configuration.CoreMessages;
import pl.subtelny.core.configuration.Settings;

@PluginSubCommand(command = "city", mainCommand = CoreDevSetSpawnCommand.class)
public class SetSpawnGlobalCommand extends BaseCommand {

    private final CoreMessages messages;

    private final Settings settings;

    @Autowired
    public SetSpawnGlobalCommand(CoreMessages messages, Settings settings) {
        this.messages = messages;
        this.settings = settings;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            messages.sendTo(sender, "coredev.setspawn.city.usage");
            return;
        }
        String rawCityType = args[0].toUpperCase();
        if (!CityType.isCityType(rawCityType)) {
            messages.sendTo(sender, "coredev.setspawn.city.notcity", rawCityType);
            return;
        }
        Player player = (Player) sender;
        Location location = player.getLocation();
        settings.set("city." + rawCityType.toLowerCase() + ".spawn", Location.class, location);
        messages.sendTo(sender, "coredev.setspawn.city.set");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }

}
