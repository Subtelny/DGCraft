package pl.subtelny.commands.core;

import org.bukkit.plugin.Plugin;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.context.CommandsService;
import pl.subtelny.components.core.api.Component;

import java.util.List;

@Component
public class CommandsServiceImpl implements CommandsService {

    @Override
    public void registerCommands(Plugin plugin, List<BaseCommand> commands) {
        System.out.println("REGISTER COMMANDS FOR " + plugin.getName());
        commands.forEach(baseCommand -> {
            System.out.println("Command: " + baseCommand.getClass().getPackageName());
        });
        CommandsInitializer commandsInitializer = new CommandsInitializer(plugin, commands);
        commandsInitializer.registerCommands();
    }

}
