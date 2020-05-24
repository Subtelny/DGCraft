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
import pl.subtelny.core.api.account.CityType;
import pl.subtelny.core.configuration.CoreMessages;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.Optional;

@PluginSubCommand(command = "setcity", mainCommand = CityDevCommand.class)
public class CityDevSetCityCommand extends BaseCommand {

    private final CoreMessages messages;

    private final Accounts accounts;

    @Autowired
    public CityDevSetCityCommand(CoreMessages messages, Accounts accounts) {
        this.messages = messages;
        this.accounts = accounts;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        validateArguments(args);
        String playerRaw = args[0];
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerRaw);
        CityType cityType = CityType.of(args[1].toUpperCase());
        accounts.findAccountAsync(AccountId.of(player.getUniqueId()))
                .whenComplete((account, throwable) -> changeAccountCity(sender, cityType, account));
    }

    public void changeAccountCity(CommandSender sender, CityType cityType, Optional<Account> accountOpt) {
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            if (account.getCityType() != cityType) {
                account.setCityType(cityType);
                accounts.saveAccount(account);
            }
            messages.sendTo(sender, "citydev.setcity.success");
        } else {
            messages.sendTo(sender, "not_found_account_for_player");
        }
    }

    private void validateArguments(String[] args) {
        if (args.length < 2) {
            throw ValidationException.of("citydev.setcity.usage");
        }
        String cityType = args[1].toUpperCase();
        if (!CityType.isCityType(cityType)) {
            throw ValidationException.of("not_valid_city_type", cityType);
        }
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return false;
    }
}
