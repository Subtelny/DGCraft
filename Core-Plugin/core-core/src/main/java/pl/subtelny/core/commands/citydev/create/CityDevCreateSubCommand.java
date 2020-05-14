package pl.subtelny.core.commands.citydev.create;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.core.city.create.CityCreateService;
import pl.subtelny.utilities.exception.ValidationException;

public abstract class CityDevCreateSubCommand extends BaseCommand {

    private final CityCreateService cityCreateService;

    public CityDevCreateSubCommand(CityCreateService cityCreateService) {
        this.cityCreateService = cityCreateService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        validateSession(player);
        handleCommand(player, args);
    }

    abstract void handleCommand(Player player, String[] args);

    private void validateSession(Player player) {
        if (!cityCreateService.hasSession(player)) {
            throw ValidationException.of("citydev.create.session_not_created");
        }
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
