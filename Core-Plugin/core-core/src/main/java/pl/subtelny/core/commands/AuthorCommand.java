package pl.subtelny.core.commands;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.configuration.CoreMessages;
import pl.subtelny.utilities.MessageUtil;

@PluginCommand(
        command = "author",
        aliases = {
                "autor",
                "autorzy"
        }
)
public class AuthorCommand extends BaseCommand {

    @Autowired
    public AuthorCommand(CoreMessages messages) {
        super(messages);
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        System.out.println("cmd thread: " + Thread.currentThread());
        MessageUtil.message(sender, "&fSubtelny :2o " + Thread.activeCount());
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return false;
    }

}
