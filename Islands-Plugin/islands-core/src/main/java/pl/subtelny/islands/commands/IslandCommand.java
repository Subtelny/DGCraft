package pl.subtelny.islands.commands;

import com.google.gson.Gson;
import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginCommand;

@PluginCommand(command = "island", aliases = {"wyspa", "w", "is"})
public class IslandCommand extends BaseCommand {

    @Override
    public void handleCommand(CommandSender sender, String[] args) {

    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
