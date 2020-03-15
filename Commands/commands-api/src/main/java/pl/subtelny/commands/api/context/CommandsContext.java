package pl.subtelny.commands.api.context;

import org.bukkit.plugin.Plugin;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.BeanContext;
import pl.subtelny.components.core.api.BeanContextId;
import pl.subtelny.components.core.api.Component;

import java.util.List;

@Component
public class CommandsContext {

    private static CommandsContext instance;

    private final CommandsService commandsService;

    @Autowired
    public CommandsContext(CommandsService commandsService) {
        instance = this;
        this.commandsService = commandsService;
    }

    public static void registerCommands(Plugin plugin) {
        List<BaseCommand> commands = BeanContext.getBeans(BeanContextId.of(plugin.getName()), BaseCommand.class);
        instance.commandsService.registerCommands(plugin, commands);
    }

}
