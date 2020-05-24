package pl.subtelny.core.commands.citydev;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.configuration.CoreMessages;

@PluginCommand(command = "citydev", permission = "lol")
public class CityDevCommand extends BaseCommand {

    private final CoreMessages messages;

    @Autowired
    public CityDevCommand(CoreMessages messages) {
        this.messages = messages;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        messages.sendTo(sender, "citydev.usage");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return false;
    }
}
