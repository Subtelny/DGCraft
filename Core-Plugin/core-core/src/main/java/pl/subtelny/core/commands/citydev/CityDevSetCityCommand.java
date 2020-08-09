package pl.subtelny.core.commands.citydev;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.api.account.Accounts;
import pl.subtelny.core.api.city.CityId;
import pl.subtelny.core.configuration.CoreMessages;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.exception.ValidationException;

@PluginSubCommand(command = "setcity", mainCommand = CityDevCommand.class)
public class CityDevSetCityCommand extends BaseCommand {

    private final Accounts accounts;

    @Autowired
    public CityDevSetCityCommand(CoreMessages messages, Accounts accounts) {
        super(messages);
        this.accounts = accounts;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        validateArguments(args);
        String playerRaw = args[0];
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerRaw);
        CityId cityId = CityId.of(args[1].toUpperCase());

        AccountId accountId = AccountId.of(player.getUniqueId());
        changeAccountCity(accountId, cityId);

        getMessages().sendTo(sender, "command.citydev.setcity.success", player.getName());
        if (player.isOnline()) {
            getMessages().sendTo(player.getPlayer(), "command.citydev.setcity.success_player", sender.getName());
        }
    }

    private void changeAccountCity(AccountId accountId, CityId cityId) {
        Account account = accounts.findAccount(accountId)
                .orElseThrow(() -> ValidationException.of("account.not_found", accountId.getInternal()));
        account.setCityId(cityId);
        accounts.saveAccount(account);
    }

    private void validateArguments(String[] args) {
        Validation.isTrue(args.length >= 2, "command.citydev.setcity.usage");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return false;
    }
}
