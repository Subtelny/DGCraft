package pl.subtelny.core.commands;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.repository.account.AccountRepository;
import pl.subtelny.core.repository.account.entity.AccountEntity;
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

    private final AccountRepository accountRepository;

    @Autowired
    public AuthorCommand(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        System.out.println("cmd thread: " + Thread.currentThread());
        MessageUtil.message(sender, "&fSubtelny :o " + Thread.activeCount());

    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return false;
    }

}
