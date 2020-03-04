package pl.subtelny.core.commands;

import org.bukkit.command.CommandSender;
import pl.subtelny.command.BukkitCommandAdapter;
import pl.subtelny.utils.MessageUtil;

//@HeadCommand(
//        command = "author",
//        aliases = {
//                "autor",
//                "autorzy"
//        }
//)
public class AuthorCommand extends BukkitCommandAdapter {

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        MessageUtil.message(sender, "&fSubtelny :o " + Thread.activeCount());
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return false;
    }
}
