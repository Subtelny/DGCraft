package pl.subtelny.islands.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.islands.islander.IslanderService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.message.IslandMessages;
import pl.subtelny.islands.skyblockisland.creator.SkyblockIslandCreator;
import pl.subtelny.islands.skyblockisland.schematic.SkyblockIslandSchematicOption;
import pl.subtelny.islands.skyblockisland.settings.SkyblockIslandSettings;
import pl.subtelny.utilities.Validation;

import java.util.Optional;

@PluginSubCommand(command = "create", aliases = "stworz", mainCommand = IslandCommand.class)
public class IslandCreateCommand extends BaseCommand {

    private final IslanderService islanderService;

    private final SkyblockIslandCreator creator;

    private final SkyblockIslandSettings settings;

    @Autowired
    public IslandCreateCommand(IslandMessages messages, IslanderService islanderService, SkyblockIslandCreator creator, SkyblockIslandSettings settings) {
        super(messages);
        this.islanderService = islanderService;
        this.creator = creator;
        this.settings = settings;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        validationArgsLength(args);
        Player player = (Player) sender;
        Islander islander = islanderService.getIslander(player);
        SkyblockIslandSchematicOption schematicOption = getSchematicOption(args[0]);
        creator.createIsland(islander, schematicOption);
    }

    private SkyblockIslandSchematicOption getSchematicOption(String schematicOptionRaw) {
        Optional<SkyblockIslandSchematicOption> schematicOption = settings.getSchematicOption(schematicOptionRaw);
        Validation.isTrue(schematicOption.isPresent(), "command.island.create.not_found_schematic", schematicOptionRaw);
        return schematicOption.get();
    }

    private void validationArgsLength(String[] args) {
        Validation.isTrue(args.length > 0, "command.island.create.usage");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
