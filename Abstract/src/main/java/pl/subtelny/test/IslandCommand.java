package pl.subtelny.test;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BukkitCommandAdapter;
import pl.subtelny.commands.api.PluginCommand;
import pl.subtelny.utilities.MessageUtil;

//@PluginCommand(
//        command = "wyspa",
//        aliases = {
//                "island",
//                "w"
//        }
//)
public class IslandCommand extends BukkitCommandAdapter {

    public IslandCommand() {

    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        MessageUtil.message(sender, "hey hey");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return false;
    }
}
