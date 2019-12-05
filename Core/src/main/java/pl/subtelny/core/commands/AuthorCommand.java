package pl.subtelny.core.commands;

import org.bukkit.command.CommandSender;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.command.HeadCommand;
import pl.subtelny.command.BukkitCommandAdapter;
import pl.subtelny.core.repository.AccountRepository;
import pl.subtelny.utils.MessageUtil;

@HeadCommand(
        command = "author",
        aliases = {
                "autor",
                "autorzy"
        }
)
public class AuthorCommand extends BukkitCommandAdapter {

    private final AccountRepository userRepository;

    @Autowired
    public AuthorCommand(AccountRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        MessageUtil.message(sender, "&fSubtelny :o " + Thread.activeCount());
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return false;
    }
}
