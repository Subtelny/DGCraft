package pl.subtelny.islands.commands;

import org.bukkit.command.CommandSender;
import pl.subtelny.command.BukkitCommandAdapter;
import pl.subtelny.utils.MessageUtil;

//@HeadCommand(
//        command = "wyspa",
//        aliases = {
//                "w",
//                "island"
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
