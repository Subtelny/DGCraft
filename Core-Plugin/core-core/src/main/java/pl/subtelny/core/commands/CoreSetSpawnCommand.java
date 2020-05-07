package pl.subtelny.core.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.configuration.Messages;
import pl.subtelny.core.configuration.Settings;
import pl.subtelny.utilities.MessageUtil;

@PluginSubCommand(command = "setspawn", mainCommand = CoreCommand.class)
public class CoreSetSpawnCommand extends BaseCommand {

    private final Messages messages;

    private final Settings settings;

    @Autowired
    public CoreSetSpawnCommand(Messages messages, Settings settings) {
        this.messages = messages;
        this.settings = settings;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Location location = player.getLocation();
        settings.set("global.spawn", Location.class, location);
        MessageUtil.message(sender, messages.get("global_spawn_set"));
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }

}
