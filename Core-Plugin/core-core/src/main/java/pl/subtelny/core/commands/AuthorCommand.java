package pl.subtelny.core.commands;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BukkitCommandAdapter;
import pl.subtelny.commands.api.PluginCommand;
import pl.subtelny.utilities.MessageUtil;

@PluginCommand(
        command = "author",
        aliases = {
                "autor",
                "autorzy"
        }
)
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
