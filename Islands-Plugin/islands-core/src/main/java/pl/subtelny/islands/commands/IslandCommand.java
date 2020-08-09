package pl.subtelny.islands.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.api.schematic.SchematicLoader;
import pl.subtelny.islands.Islands;
import pl.subtelny.islands.message.IslandMessages;
import pl.subtelny.islands.skyblockisland.repository.SkyblockIslandRepository;

import java.io.File;

@PluginCommand(command = "island", aliases = {"wyspa", "w", "is"})
public class IslandCommand extends BaseCommand {

    private final SkyblockIslandRepository repository;

    private final SchematicLoader schematicLoader;

    @Autowired
    public IslandCommand(IslandMessages messages, SkyblockIslandRepository repository, SchematicLoader schematicLoader) {
        super(messages);
        this.repository = repository;
        this.schematicLoader = schematicLoader;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        File dataFolder = Islands.plugin.getDataFolder();
        File schematics = new File(dataFolder, "schematics");
        File basic = new File(schematics, "basic.schem");
        player.sendMessage("#1");
        schematicLoader.pasteSchematic(player.getLocation(), basic, aBoolean -> {
            player.sendMessage("#done " + aBoolean);
        });

        getMessages().sendTo(sender, "command.island.usage");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
