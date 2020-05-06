package pl.subtelny.core.commands;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.repository.account.AccountRepository;
import pl.subtelny.core.repository.loginhistory.LoginHistoryRepository;
import pl.subtelny.utilities.MessageUtil;

import java.time.LocalDateTime;
import java.util.UUID;

@PluginCommand(
        command = "author",
        aliases = {
                "autor",
                "autorzy"
        }
)
public class AuthorCommand extends BaseCommand {

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
