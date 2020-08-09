package pl.subtelny.core.commands.coredev.setspawn;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.api.city.CityId;
import pl.subtelny.core.city.City;
import pl.subtelny.core.city.repository.CityRepository;
import pl.subtelny.core.configuration.CoreMessages;
import pl.subtelny.core.configuration.Locations;
import pl.subtelny.utilities.exception.ValidationException;

@PluginSubCommand(command = "city", mainCommand = CoreDevSetSpawnCommand.class)
public class SetSpawnCityCommand extends BaseCommand {

    private final CoreMessages messages;

    private final Locations locations;

    private final CityRepository cityRepository;

    @Autowired
    public SetSpawnCityCommand(CoreMessages messages, Locations locations, CityRepository cityRepository) {
        super(messages);
        this.messages = messages;
        this.locations = locations;
        this.cityRepository = cityRepository;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            messages.sendTo(sender, "command.coredev.setspawn.city.usage");
            return;
        }
        String rawCityType = args[0].toUpperCase();
        CityId cityId = CityId.of(rawCityType);

        City city = cityRepository.find(cityId)
                .orElseThrow(() -> ValidationException.of("city.not_found", cityId.getInternal()));

        changeSpawn(city, ((Player) sender).getLocation());
        messages.sendTo(sender, "command.coredev.setspawn.city.changed");
    }

    public void changeSpawn(City city, Location location) {
        city.changeSpawn(location);
        String cityRawId = city.getCityId().getInternal();
        locations.set("city." + cityRawId + ".spawn", location);
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }

}
