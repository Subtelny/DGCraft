package pl.subtelny.commands.api.context;

import org.bukkit.plugin.Plugin;
import pl.subtelny.commands.api.BaseCommand;

import java.util.List;

public interface CommandsService {

    void registerCommands(Plugin plugin, List<BaseCommand> commands);

}
